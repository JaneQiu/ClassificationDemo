package jane.mall.classification;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import jane.mall.R;
import jane.mall.base.BaseFragment;
import jane.mall.base.EventBusTag;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassificationContentFragment extends BaseFragment {

    private RecyclerView mContentRecyclerView;
    private ClassificationContentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_classification_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mContentRecyclerView = findView(R.id.frag_classification_rv);
        adapter = new ClassificationContentAdapter(getContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                switch (viewType) {
                    case ClassificationContentAdapter.ITEM_TYPE_PARENT_MENU:
                        return 3;
                    case ClassificationContentAdapter.ITEM_TYPE_SUB_MENU:
                        return 1;
                }
                return 1;
            }
        });
        mContentRecyclerView.setLayoutManager(gridLayoutManager);

        adapter.setOnContentSelectedAdapter(new ClassificationContentAdapter.OnContentSelectedAdapter() {
            @Override
            public void onContentSelected(String categoryId, String categoryId2, String categoryName) {
                Toast.makeText(getContext(), categoryId + "-----" + categoryId2 + "--------" + categoryName, Toast.LENGTH_SHORT).show();
            }
        });
        mContentRecyclerView.setAdapter(adapter);
    }

    private SparseArray<List<AllCategoryEntity.BaseSubCategoryEntity>> mBaseSubCategoryEntities;

    @Subscriber(tag = EventBusTag.CATEGORY_SUB_MENU_DATA_RECEIVE_TAG)
    public void onReceiveData(Pair<Integer, SparseArray<List<AllCategoryEntity.BaseSubCategoryEntity>>> pair) {
        mBaseSubCategoryEntities = pair.second;
        adapter.setData(mBaseSubCategoryEntities.get(pair.first));
    }

    @Subscriber(tag = EventBusTag.CATEGORY_ON_CHECKED_CHANGED_TAG)
    public void onCategoryIdChecked(Integer id) {
        List<AllCategoryEntity.BaseSubCategoryEntity> baseSubCategoryEntities = mBaseSubCategoryEntities.get(id);
        adapter.setData(baseSubCategoryEntities);
        mContentRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}