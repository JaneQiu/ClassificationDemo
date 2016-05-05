package jane.mall.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/4 14:30
 *         description
 *         vsersion
 */
public abstract class BaseRecycleViewAdapter<T> extends RecyclerAdapter<T, RecyclerViewHolder> {

    public BaseRecycleViewAdapter(Context context) {
        this(context, null);
    }

    public BaseRecycleViewAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(getItemView(mLayoutInflater, parent, viewType));
    }

    protected abstract View getItemView(LayoutInflater layoutInflater, ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerViewHolder viewHolder, int position) ;
}
