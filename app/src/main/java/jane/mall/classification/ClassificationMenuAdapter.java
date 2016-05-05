package jane.mall.classification;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jane.mall.R;
import jane.mall.base.BaseRecycleViewAdapter;
import jane.mall.base.RecyclerViewHolder;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/4 11:22
 *         description
 *         vsersion
 */
public class ClassificationMenuAdapter<T> extends BaseRecycleViewAdapter<AllCategoryEntity.BaseCategoryEntity> implements View.OnClickListener {

    private int mCheckedId;
    private int mCheckedPosition;

    public ClassificationMenuAdapter(Context context) {
        this(context, null);
    }

    public ClassificationMenuAdapter(Context context, List<AllCategoryEntity.BaseCategoryEntity> data) {
        super(context, data);
    }

    @Override
    protected View getItemView(LayoutInflater layoutInflater, ViewGroup parent, int viewType) {
        return layoutInflater.inflate(R.layout.item_classification_menu, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        viewHolder.getHelper().setText(getItem(position).getCategoryName(), R.id.item_classification_menu_tv);
        viewHolder.itemView.setTag(getItem(position));
        viewHolder.itemView.setOnClickListener(this);
        if (position == mCheckedPosition) {
            viewHolder.getHelper().setTextColor(ContextCompat.getColor(mContext, R.color.app_green), R.id.item_classification_menu_tv);
            viewHolder.getHelper().setBackgroundColor(mContext.getResources().getColor(R.color.app_white), R.id.item_classification_menu_tv);
        } else {
            viewHolder.getHelper().setTextColor(ContextCompat.getColor(mContext, R.color.app_black), R.id.item_classification_menu_tv);
            viewHolder.getHelper().setBackgroundColor(mContext.getResources().getColor(R.color.app_gray_background), R.id.item_classification_menu_tv);
        }
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof AllCategoryEntity.BaseCategoryEntity) {
            int categoryId = ((AllCategoryEntity.BaseCategoryEntity) tag).getCategoryId();
            setCurrentCategoryId(categoryId);
            if (mOnMenuSelectedListener != null) {
                mOnMenuSelectedListener.OnMenuSelected(categoryId);
            }
        }
    }

    public void setCurrentCategoryId(int categoryId) {
        if (mCheckedId == categoryId) {
            return;
        }
        int size = getDataSize();
        for (int i = 0; i < size; i++) {
            if (categoryId == getData().get(i).getCategoryId()) {
                int oldCheckedPosition = mCheckedPosition;
                mCheckedPosition = i;
                notifyItemChanged(oldCheckedPosition);
                notifyItemChanged(mCheckedPosition);
                mCheckedId = categoryId;
                break;
            }
        }
    }

    @Override
    public void setData(List<AllCategoryEntity.BaseCategoryEntity> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        mCheckedPosition = 0;
        mCheckedId = data.get(0).getCategoryId();
        super.setData(data);
    }

    private OnMenuSelectedListener mOnMenuSelectedListener;

    public void setOnMenuSelectedListener(OnMenuSelectedListener onMenuSelectedListener) {
        mOnMenuSelectedListener = onMenuSelectedListener;
    }


    public interface OnMenuSelectedListener {
        void OnMenuSelected(int categoryId);
    }


}
