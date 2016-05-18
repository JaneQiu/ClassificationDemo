package jane.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import jane.mall.app.DataDictionary;
import jane.mall.base.BaseFragment;
import jane.mall.base.EventBusTag;
import jane.mall.classification.AllCategoryEntity;
import jane.mall.classification.ClassificationContentFragment;
import jane.mall.classification.ClassificationMenuFragment;
import jane.mall.net.ApiConstant;
import jane.mall.net.JsonParserUtil;
import jane.mall.net.NetUtil;
import jane.mall.util.Checker;
import jane.mall.util.SpCache;
import jane.mall.util.TreatmentBase64;
import jane.mall.util.log.KLog;
import jane.mall.views.MultiStateView;
import okhttp3.Call;


public class ClassificationFragment extends BaseFragment {

    private static final String TAG = ClassificationFragment.class.getSimpleName();
    private int mCurrentCategoryId;

    private MultiStateView mMultiStateView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classification, container, false);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMultiStateView = findView(R.id.fragment_classification_ms);
        replaceFragment(R.id.frag_classification_menu_fl, ClassificationMenuFragment.class);
        replaceFragment(R.id.frag_classification_content_fl, ClassificationContentFragment.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    private void loadData() {
        getToken();
        getCategoryList();
    }

    private void getToken() {
        NetUtil.getInstance().post(ApiConstant.GET_TOKEN, null, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                KLog.d(e.toString());
            }

            @Override
            public void onResponse(String response) {
                String json = TreatmentBase64.decryptBASE64(response);
                KLog.json(json);

                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject jsonObj = jsonObject.getJSONObject("returnObject");
                    SpCache.putString(SpCache.TOKEN, jsonObj.getString("token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                DataDictionary dataDictionary = JsonParserUtil.getInstance(getActivity()).filterStringCode(json);
                if (dataDictionary.getCode() == ApiConstant.TOKEN_OK) {
                    KLog.d("成功获取到token");
                } else {
                    KLog.d("获取token失败");
                }

            }
        });
    }

    private void getCategoryList() {
        NetUtil.getInstance().post(ApiConstant.CATEGORY_LIST, null, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                KLog.d(e.toString());
            }

            @Override
            public void onResponse(String response) {
                String json = TreatmentBase64.decryptBASE64(response);
                KLog.json(json);
                DataDictionary dataDictionary = JsonParserUtil.getInstance(getActivity()).filterStringCode(json);
                if (dataDictionary.getCode() == ApiConstant.TOKEN_OK) {
                    KLog.d("getCategoryList success");
                    AllCategoryEntity result = new Gson().fromJson(json, AllCategoryEntity.class);
                    processAllCategoryEntity(result);
                } else {
                    KLog.d("getCategoryList failure");
                    mMultiStateView.showError();
                }

            }
        });
    }

    /**
     * 处理返回的数据
     *
     * @param result
     */
    private void processAllCategoryEntity(AllCategoryEntity result) {

        List<AllCategoryEntity.CategoryLevel1Entity> returnObject = result.getReturnObject();

        //所有父菜单
        final List<AllCategoryEntity.BaseCategoryEntity> baseCategoryEntities = new ArrayList<>();
        //所有子菜单
        final SparseArray<List<AllCategoryEntity.BaseSubCategoryEntity>> mSubCategoryList = new SparseArray<>();
        //子菜单
        List<AllCategoryEntity.BaseSubCategoryEntity> baseSubCategoryEntities;

        for (AllCategoryEntity.CategoryLevel1Entity categoryLevel1Entity : returnObject) {

            baseCategoryEntities.add(categoryLevel1Entity);

            baseSubCategoryEntities = new ArrayList<>();
            if (categoryLevel1Entity.getSubCategoryList() != null) {
                for (AllCategoryEntity.CategoryLevel2Entity categoryLevel2Entity : categoryLevel1Entity.getSubCategoryList()) {
                    StringBuilder builder2 = new StringBuilder();
                    builder2.append(categoryLevel1Entity.getCategoryId() + "_");
                    builder2.append(categoryLevel2Entity.getCategoryId());
                    categoryLevel2Entity.setCategoryLevel2Code(builder2.toString());
                    baseSubCategoryEntities.add(categoryLevel2Entity);

                    for (AllCategoryEntity.CategoryLevel3Entity categoryLevel3Entity : categoryLevel2Entity.getSubCategoryList()) {
                        StringBuilder builder = new StringBuilder();
                        builder.append(categoryLevel1Entity.getCategoryId() + "_");
                        builder.append(categoryLevel2Entity.getCategoryId() + "_");
                        builder.append(categoryLevel3Entity.getCategoryId());
                        categoryLevel3Entity.setCategoryLevel3Code(builder.toString());
                        baseSubCategoryEntities.add(categoryLevel3Entity);
                    }
                }
            }

            mSubCategoryList.put(categoryLevel1Entity.getCategoryId(), baseSubCategoryEntities);

        }//遍历完毕

        if (mCurrentCategoryId == 0) {
            mCurrentCategoryId = baseCategoryEntities.get(0).getCategoryId();
        } else {
            if (Checker.isEmpty(mSubCategoryList.get(mCurrentCategoryId))) {
                mCurrentCategoryId = baseCategoryEntities.get(0).getCategoryId();
            }
        }

        //通知菜单显示

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);//本地缓存时太快 EventBus还未注册完成 所以停留500毫秒
                } catch (Exception e) {

                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new Pair<>(mCurrentCategoryId, baseCategoryEntities), EventBusTag.CATEGORY_MENU_DATA_RECEIVE_TAG);
                        //通知内容显示
                        EventBus.getDefault().post(new Pair<>(mCurrentCategoryId, mSubCategoryList), EventBusTag.CATEGORY_SUB_MENU_DATA_RECEIVE_TAG);
                    }
                });

            }
        }.start();

        //显示内容
        KLog.d(TAG, "showContent");
        mMultiStateView.showContent();
    }

    public void replaceFragment(int resId, Class clazz) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(resId, Fragment.instantiate(getActivity(), clazz.getName()))
                .commit();
    }

    @Subscriber(tag = EventBusTag.CATEGORY_ON_CHECKED_CHANGED_TAG)
    public void onCategoryIdChecked(Integer id) {
        mCurrentCategoryId = id;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
