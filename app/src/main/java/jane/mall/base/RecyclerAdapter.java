package jane.mall.base;

import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/4 11:24
 *         description
 *         vsersion
 */
public abstract class RecyclerAdapter<T, VH extends RecyclerViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> mData;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public RecyclerAdapter(Context context, List<T> data) {
        this.mData = data;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public RecyclerAdapter(Context context) {
        this(context, null);
    }

    public List<T> getData() {
        return mData;
    }


    /**
     * 添加一条数据，这里需要注意的是，如果使用type来实现多个条目。在notify的时候，可能需要写此方法
     *
     * @param t
     */
    @UiThread
    public boolean addItem(T t) {
        if (t == null) {
            return false;
        }
        if (mData != null) {
            int lastIndex = mData.size();
            mData.add(t);
            notifyItemInserted(lastIndex);
        } else {
            mData = new ArrayList<>();
            mData.add(t);
            notifyDataSetChanged();
        }
        return true;
    }

    /**
     * 添加数据集合，这里需要注意的是，如果使用type来实现多个条目。在notify的时候，可能需要写此方法
     *
     * @param data
     */
    @UiThread
    public boolean addAll(List<T> data) {
        if (data == null || data.isEmpty()) {
            return false;
        }
        if (this.mData != null) {
            int lastIndex = getDataSize();
            this.mData.addAll(data);
            notifyItemRangeInserted(lastIndex, data.size());
        } else {
            this.mData = data;
            notifyDataSetChanged();
        }
        return true;
    }


    @UiThread
    public void setData(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }


    @UiThread
    public boolean removeItem(int position) {
        if (isEmpty()) {
            return false;
        }
        if (position < getDataSize()) {
            mData.remove(position);
            notifyItemRemoved(position);
            return true;
        }
        return false;
    }

    public boolean removeItem(T t) {
        int dataPosition = getDataPosition(t);
        if (dataPosition >= 0 && dataPosition < getDataSize()) {
            boolean remove = mData.remove(t);
            notifyItemRemoved(dataPosition);
            return remove;
        }
        return false;
    }


    public int getDataPosition(T t) {
        if (isEmpty()) {
            return -1;
        }
        int position = 0;
        for (T t1 : getData()) {
            if (t1 == t) {
                break;
            }
            position++;
        }
        return position;
    }


    @UiThread
    public boolean clear() {
        if (mData != null) {
            mData.clear();
            mData = null;
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public T getItem(int position) {
        if (position >= 0 && position < getDataSize()) {
            return mData.get(position);
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return getDataSize();
    }

    public boolean isEmpty() {
        return getDataSize() == 0;
    }

    public int getDataSize() {
        return mData == null ? 0 : mData.size();
    }


    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(VH viewHolder, int position);


}
