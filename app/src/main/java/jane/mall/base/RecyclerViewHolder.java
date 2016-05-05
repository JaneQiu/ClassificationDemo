package jane.mall.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/4 11:25
 *         description
 *         vsersion
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements IViewHolder {

    private ViewHelper mItemHelper;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mItemHelper = new ViewHelper(itemView);
    }

    @Override
    public ViewHelper getHelper() {
        return mItemHelper;
    }

    @Override
    public void clear() {
        mItemHelper.clear();
    }

}
