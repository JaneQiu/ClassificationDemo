package jane.mall.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import jane.mall.R;

/**
 * View that contains 4 different states: Content, Error, Empty, and Loading.<br>
 * Each state has their own separate layout which can be shown/hidden by setting
 * the {@link ViewState} accordingly
 * Every MultiStateView <b><i>MUST</i></b> contain a content view. The content view
 * is obtained from whatever is inside of the tags of the view via its XML declaration
 * <p/>
 * 修改者：Ztiany
 * <font color='red'>使用时，如果有多个View需要放在MultiStateView中，必须把ContentView放在第一位。</font>
 * 设置空布局或者错误布局时，提供一个 id = R.id.basic_retry_btn的控件如button,可以一次来触发点击重试
 */
public class MultiStateView extends FrameLayout {

    public static final int VIEW_STATE_CONTENT = 0;

    public static final int VIEW_STATE_ERROR = 1;

    public static final int VIEW_STATE_EMPTY = 2;

    public static final int VIEW_STATE_LOADING = 3;
    private View mErrorRetryView;
    private View mEmptyRetryView;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({VIEW_STATE_CONTENT, VIEW_STATE_ERROR, VIEW_STATE_EMPTY, VIEW_STATE_LOADING})
    public @interface ViewState {

    }


    private LayoutInflater mInflater;
    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    private OnRetryListener mOnErrorRetryListener;
    private OnRetryListener mOnEmptyRetryListener;

    private int mLoadingViewRes;
    private int mErrorViewRes;
    private int mEmptyViewRes;
    private OnClickListener mOnClickListener;

    private String mEmptyMsg;
    private String mErrorMsg;


    @ViewState
    private int mViewState = VIEW_STATE_LOADING;

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mInflater = LayoutInflater.from(getContext());
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateView);

        int loadingViewResId = a.getResourceId(R.styleable.MultiStateView_msv_loadingView, -1);
        if (loadingViewResId > 0) {
            mLoadingViewRes = loadingViewResId;
        }

        int emptyViewResId = a.getResourceId(R.styleable.MultiStateView_msv_emptyView, -1);
        if (emptyViewResId > 0) {
            mEmptyViewRes = emptyViewResId;
        }

        int errorViewResId = a.getResourceId(R.styleable.MultiStateView_msv_errorView, -1);
        if (errorViewResId > 0) {
            mErrorViewRes = errorViewResId;
        }

        int viewState = a.getInt(R.styleable.MultiStateView_msv_viewState, VIEW_STATE_CONTENT);


        switch (viewState) {
            case VIEW_STATE_CONTENT:
                mViewState = VIEW_STATE_CONTENT;
                break;

            case VIEW_STATE_ERROR:
                mViewState = VIEW_STATE_ERROR;
                break;

            case VIEW_STATE_EMPTY:
                mViewState = VIEW_STATE_EMPTY;
                break;

            case VIEW_STATE_LOADING:
                mViewState = VIEW_STATE_LOADING;
                break;
        }
        a.recycle();

        //重试监听
        mOnClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v == mEmptyRetryView) {
                    if (mOnEmptyRetryListener != null) {
                        mOnEmptyRetryListener.onRetry(MultiStateView.this);
                    }
                } else if (v == mErrorRetryView) {
                    if (mOnErrorRetryListener != null) {
                        mOnErrorRetryListener.onRetry(MultiStateView.this);
                    }
                }
            }
        };
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mContentView == null) throw new IllegalArgumentException("Content view is not defined");
        setView();
    }

    /* All of the addView methods have been overridden so that it can obtain the content view via XML
     It is NOT recommended to add views into MultiStateView via the addView methods, but rather use
     any of the setViewForState methods to set views for their given ViewState accordingly */
    @Override
    public void addView(View child) {
        if (isValidContentView(child))
            mContentView = child;
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) mContentView = child;
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (isValidContentView(child)) mContentView = child;
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    @Nullable
    public View getView(@ViewState int state) {
        switch (state) {
            case VIEW_STATE_LOADING:
                return mLoadingView;

            case VIEW_STATE_CONTENT:
                return mContentView;

            case VIEW_STATE_EMPTY:
                return mEmptyView;

            case VIEW_STATE_ERROR:
                return mErrorView;

            default:
                return null;
        }
    }


    @ViewState
    public int getViewState() {
        return mViewState;
    }

    private void setViewState(@ViewState int state) {
        if (state != mViewState) {
            mViewState = state;
            setView();
        }
    }

    private void setView() {
        switch (mViewState) {
            case VIEW_STATE_LOADING:
                if (mLoadingView == null) {
                    generateLoadingView();
                }


                if (mContentView != null) gone(mContentView);
                if (mErrorView != null) gone(mErrorView);
                if (mEmptyView != null) gone(mEmptyView);
                visible(mLoadingView);
                break;

            case VIEW_STATE_EMPTY:
                if (mEmptyView == null) {
                    generateEmptyView();
                }

                if (mLoadingView != null) gone(mLoadingView);
                if (mErrorView != null) gone(mErrorView);
                if (mContentView != null) gone(mContentView);
                visible(mEmptyView);
                break;

            case VIEW_STATE_ERROR:
                if (mErrorView == null) {
                    generateErrorView();
                }

                if (mLoadingView != null) gone(mLoadingView);
                if (mContentView != null) gone(mContentView);
                if (mEmptyView != null) gone(mEmptyView);
                visible(mErrorView);
                break;

            case VIEW_STATE_CONTENT:
            default:
                if (mContentView == null) {
                    throw new NullPointerException("Content View");
                }

                if (mLoadingView != null) gone(mLoadingView);
                if (mErrorView != null) gone(mErrorView);
                if (mEmptyView != null) gone(mEmptyView);
                visible(mContentView);
                break;
        }
    }

    private void generateLoadingView() {
        mLoadingView = mInflater.inflate(mLoadingViewRes, this, false);
        addView(mLoadingView, mLoadingView.getLayoutParams());
    }

    private void generateErrorView() {
        mErrorView = mInflater.inflate(mErrorViewRes, this, false);
        Log.d("MultiStateView", "mErrorViewRes:" + mErrorViewRes);
        addView(mErrorView, mErrorView.getLayoutParams());
        initErrorViewState(mErrorView);
    }

    private void generateEmptyView() {
        mEmptyView = mInflater.inflate(mEmptyViewRes, this, false);
        addView(mEmptyView, mEmptyView.getLayoutParams());
        initEmptyViewState(mEmptyView);
    }


    /**
     * Checks if the given {@link View} is valid for the Content View
     *
     * @param view The {@link View} to check
     * @return ContentView是否可用
     */
    private boolean isValidContentView(View view) {
        if ((mContentView != null) && (mContentView != view)) {
            return false;
        }
        return view != mLoadingView && view != mErrorView && view != mEmptyView;
    }

    /**
     * Sets the view for the given view state
     *
     * @param view The {@link View} to use
     */
    public void setViewForState(View view, @ViewState int state, boolean switchToState) {
        switch (state) {
            case VIEW_STATE_LOADING:
                if (mLoadingView != null) {
                    removeView(mLoadingView);
                }
                mLoadingView = view;
                addView(mLoadingView);
                break;

            case VIEW_STATE_EMPTY:
                if (mEmptyView != null) {
                    clearClickListener(mEmptyView);
                    removeView(mEmptyView);
                }
                mEmptyView = view;
                addView(mEmptyView);
                initEmptyViewState(mEmptyView);
                break;

            case VIEW_STATE_ERROR:
                if (mErrorView != null) {
                    clearClickListener(mErrorView);
                    removeView(mErrorView);
                }
                mErrorView = view;
                addView(mErrorView);
                initErrorViewState(mErrorView);
                break;

            case VIEW_STATE_CONTENT:
                if (mContentView != null) {
                    removeView(mContentView);
                }
                mContentView = view;
                addView(mContentView);
                break;
        }

        if (switchToState) setViewState(state);
    }

    public void setViewForState(View view, @ViewState int state) {
        setViewForState(view, state, false);
    }

    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state, boolean switchToState) {
        if (mInflater == null) mInflater = LayoutInflater.from(getContext());
        View view = mInflater.inflate(layoutRes, this, false);
        setViewForState(view, state, switchToState);
    }

    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state) {
        setViewForState(layoutRes, state, false);
    }


    public void setOnRetryListener(OnRetryListener onRetryListener) {
        setOnErrorRetryListener(onRetryListener);
    }

    public void setOnEmptyRetryListener(OnRetryListener onEmptyRetryListener) {
        mOnEmptyRetryListener = onEmptyRetryListener;
    }

    public void setOnErrorRetryListener(OnRetryListener onErrorRetryListener) {
        mOnErrorRetryListener = onErrorRetryListener;
    }

    public void showContent() {
        setViewState(VIEW_STATE_CONTENT);
    }

    public void showError() {
        setViewState(VIEW_STATE_ERROR);

    }

    public void showLoading() {
        setViewState(VIEW_STATE_LOADING);

    }

    public void showEmpty() {
        setViewState(VIEW_STATE_EMPTY);
    }


    private void initErrorViewState(View view) {
        mErrorRetryView = view.findViewById(R.id.basic_retry_view);
        if (mErrorRetryView != null) {
            mErrorRetryView.setOnClickListener(mOnClickListener);

            if (!TextUtils.isEmpty(mErrorMsg) && mErrorRetryView instanceof TextView) {
                ((TextView) mErrorRetryView).setText(mErrorMsg);
            }

        }
    }

    private void initEmptyViewState(View view) {
        mEmptyRetryView = view.findViewById(R.id.basic_retry_view);
        if (mEmptyRetryView != null) {
            mEmptyRetryView.setOnClickListener(mOnClickListener);
            if (!TextUtils.isEmpty(mEmptyMsg) && mEmptyRetryView instanceof TextView) {
                ((TextView) mEmptyRetryView).setText(mEmptyMsg);
            }
        }
    }


    private void visible(final View view) {
        if (view.getVisibility() == VISIBLE) {
            return;
        }
        view.setVisibility(VISIBLE);

    }

    private void gone(final View view) {
        if (view.getVisibility() == GONE) {
            return;
        }
        view.setVisibility(GONE);


    }


    public void setErrorMessage(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        mErrorMsg = msg;
        if (mErrorRetryView != null && mErrorRetryView instanceof TextView) {
            ((TextView) mErrorRetryView).setText(mErrorMsg);
        }
    }

    public void setEmptyMessage(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        mEmptyMsg = msg;
        if (mEmptyRetryView != null && mEmptyRetryView instanceof TextView) {
            ((TextView) mEmptyRetryView).setText(mEmptyMsg);
        }
    }


    private void clearClickListener(@NonNull View v) {
        v.setOnClickListener(null);
    }

    public interface OnRetryListener {
        void onRetry(MultiStateView multiStateView);
    }

}
