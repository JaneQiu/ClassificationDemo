package jane.mall.classification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import jane.mall.R;
import jane.mall.base.BaseRecycleViewAdapter;
import jane.mall.base.RecyclerViewHolder;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/4 11:24
 *         description
 *         vsersion
 */
public class ClassificationContentAdapter extends BaseRecycleViewAdapter<AllCategoryEntity.BaseSubCategoryEntity> implements View.OnClickListener {

    public static final int ITEM_TYPE_PARENT_MENU = 0;
    public static final int ITEM_TYPE_SUB_MENU = 1;

    public ClassificationContentAdapter(Context context) {
        this(context, null);
    }

    public ClassificationContentAdapter(Context context, List<AllCategoryEntity.BaseSubCategoryEntity> data) {
        super(context, data);
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = getItem(position).getType();
//        KLog.d("viewType--------" + viewType);
        switch (viewType) {
            case AllCategoryEntity.BaseSubCategoryEntity.TYPE_LEVEL_2:
                return ITEM_TYPE_PARENT_MENU;
            case AllCategoryEntity.BaseCategoryEntity.TYPE_LEVEL_3:
                return ITEM_TYPE_SUB_MENU;
        }
        throw new IllegalStateException("getItemViewType 不支持此种类型的Item-------" + viewType);
    }

    @Override
    protected View getItemView(LayoutInflater layoutInflater, ViewGroup parent, int viewType) {
//        KLog.d("viewType-------" + viewType);
        switch (viewType) {
            case ITEM_TYPE_PARENT_MENU:
                View view = layoutInflater.inflate(R.layout.item_classification_content_parent, parent, false);
                view.setOnClickListener(this);
                return view;
            case ITEM_TYPE_SUB_MENU:
                View view1 = layoutInflater.inflate(R.layout.item_classification_content_sub, parent, false);
                view1.setOnClickListener(this);
                return view1;
        }
        throw new IllegalStateException("getItemView 不支持此种类型的Item-------" + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        int viewType = viewHolder.getItemViewType();
        AllCategoryEntity.BaseSubCategoryEntity entity = getItem(position);
        if (entity == null) {
            return;
        }
        viewHolder.getHelper().setText(entity.getCategoryName(), R.id.item_classification_content_tv);

        switch (viewType) {
            case ITEM_TYPE_PARENT_MENU:
                break;
            case ITEM_TYPE_SUB_MENU:
                if (getItem(position) instanceof AllCategoryEntity.CategoryLevel3Entity) {
                    ImageView imageView = viewHolder.getHelper().getView(R.id.item_classification_content_iv);
                    Glide.with(mContext).load(((AllCategoryEntity.CategoryLevel3Entity) getItem(position)).getCategoryImg())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.mipmap.def_km_icon)
                            .placeholder(R.mipmap.def_km_icon)
                            .into(imageView);
                }
                break;
        }
        viewHolder.itemView.setTag(getItem(position));
    }

    @Override
    public void onClick(View v) {
        Object o = v.getTag();
        if (o instanceof AllCategoryEntity.BaseSubCategoryEntity) {
            String categoryId = ((AllCategoryEntity.BaseSubCategoryEntity) o).getCategoryLevel3Code();
            String categoryId2 = ((AllCategoryEntity.BaseSubCategoryEntity) o).getCategoryLevel2Code();
            String categoryName = ((AllCategoryEntity.BaseSubCategoryEntity) o).getCategoryName();

            if (onContentSelectedAdapter != null) {
                onContentSelectedAdapter.onContentSelected(categoryId, categoryId2, categoryName);
            }
        }
    }

    private OnContentSelectedAdapter onContentSelectedAdapter;

    public void setOnContentSelectedAdapter(OnContentSelectedAdapter onContentSelectedAdapter) {
        this.onContentSelectedAdapter = onContentSelectedAdapter;
    }

    public interface OnContentSelectedAdapter {
        void onContentSelected(String categoryId, String categoryId2, String categoryName);
    }


}
