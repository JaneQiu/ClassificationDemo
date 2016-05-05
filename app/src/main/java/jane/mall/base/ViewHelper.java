package jane.mall.base;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/4 11:27
 *         description
 *         vsersion
 */
public class ViewHelper {

    private View mItemView;
    protected SparseArray<View> views;
    private static final String TAG = ViewHelper.class.getSimpleName();

    public ViewHelper(View itemView) {
        mItemView = itemView;
        views = new SparseArray<>();
    }


    public <T> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


    public void clear() {
        if (views != null) {
            views.clear();
        }
        views = null;
        mItemView = null;
    }

    public ViewHelper setText(CharSequence str, @IdRes int viewId) {
        ((TextView) getView(viewId)).setText(str == null ? "" : str);
        return this;
    }

    public ViewHelper setText(@StringRes int strId, @IdRes int viewId) {
        ((TextView) getView(viewId)).setText(strId);
        return this;
    }


    public ViewHelper setBitmapResourece(@DrawableRes int resId, @IdRes int viewId) {
        ((ImageView) getView(viewId)).setImageResource(resId);
        return this;
    }


    public ViewHelper setOnClickLister(View.OnClickListener clickLister, @IdRes int viewID) {
        View view = getView(viewID);
        view.setOnClickListener(clickLister);
        return this;
    }

    public ViewHelper gone(@IdRes int... viewIDs) {
        View view;
        for (int viewID : viewIDs) {
            view = getView(viewID);
            view.setVisibility(View.GONE);
        }
        return this;
    }

    public ViewHelper visible(@IdRes int... viewIDs) {
        View view;
        for (int viewID : viewIDs) {
            view = getView(viewID);
            view.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public ViewHelper invisible(@IdRes int... viewIDs) {
        View view;
        for (int viewID : viewIDs) {
            view = getView(viewID);
            view.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public ViewHelper setTextColor(@ColorInt int color, @IdRes int viewID) {
        TextView view = getView(viewID);
        view.setTextColor(color);
        return this;
    }


    public ViewHelper setChecked(boolean checked, @IdRes int viewID) {
        View view = getView(viewID);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }


    public ViewHelper setSelected(boolean selected, @IdRes int viewID) {
        View view = getView(viewID);
        view.setSelected(selected);
        return this;
    }


    public ViewHelper setBackGroundColor(@ColorInt int color, @IdRes int viewID) {
        View view = getView(viewID);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHelper setBackgroundDrawable(Drawable drawable, @IdRes int viewID) {
        View view = getView(viewID);
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        return this;
    }

    public ViewHelper setEnable(boolean enable, @IdRes int viewID) {
        View view = getView(viewID);
        view.setEnabled(enable);
        return this;
    }

    public ViewHelper setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener, @IdRes int viewID) {
        CheckBox checkBox = getView(viewID);
        checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        return this;
    }

    public ViewHelper setTag(@NonNull Object object, @IdRes int tagId, @IdRes int viewID) {
        View view = getView(viewID);
        view.setTag(tagId, object);
        return this;
    }

    public ViewHelper setTag(@NonNull Object object, @IdRes int viewID) {
        View view = getView(viewID);
        view.setTag(object);
        return this;
    }


    public ViewHelper setBackgroundColor(@ColorInt int backgroundColor, @IdRes int viewId) {
        View view = getView(viewId);
        view.setBackgroundColor(backgroundColor);
        return this;
    }


    public ViewHelper setMarginLeft(int marginLeft, @IdRes int viewId) {
        View view = getView(viewId);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.leftMargin = marginLeft;
        return this;
    }
}