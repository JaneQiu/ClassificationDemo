package jane.mall.classification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import jane.mall.R;
import jane.mall.base.BaseFragment;
import jane.mall.base.EventBusTag;
import jane.mall.util.log.KLog;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/3 19:29
 *         description
 *         vsersion
 */
public class ClassificationMenuFragment extends BaseFragment {

    private static final String TAG = ClassificationMenuFragment.class.getSimpleName();
    private ClassificationMenuAdapter mClassificationMenuAdapter;
    private RecyclerView mMenuRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_classification_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        KLog.d();
        mMenuRecyclerView = findView(R.id.frag_classification_rv);
        mClassificationMenuAdapter = new ClassificationMenuAdapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mMenuRecyclerView.setLayoutManager(linearLayoutManager);

        mClassificationMenuAdapter.setOnMenuSelectedListener(new ClassificationMenuAdapter.OnMenuSelectedListener() {
            @Override
            public void OnMenuSelected(int categoryId) {
                EventBus.getDefault().post(categoryId, EventBusTag.CATEGORY_ON_CHECKED_CHANGED_TAG);
            }
        });
        mMenuRecyclerView.setAdapter(mClassificationMenuAdapter);
    }

    @Subscriber(tag = EventBusTag.CATEGORY_MENU_DATA_RECEIVE_TAG)
    public void onReceiveData(Pair<Integer, List<AllCategoryEntity.BaseCategoryEntity>> pair) {
        Log.d("ClassificationMenuFragm", "pair.second.size():" + pair.second.size());
        Log.d(TAG, "onReceiveData() called with: " + "pair = [" + pair + "]-----" + "[" + pair.second.size() + "]");
        mClassificationMenuAdapter.setData(pair.second);
        mClassificationMenuAdapter.setCurrentCategoryId(pair.first);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
