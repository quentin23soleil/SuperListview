package com.quentindommerc.superlistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.quentindommerc.superlistview.superlistview.R;


/**
 * Created by kentin on 24/04/14.
 */
public class SuperListview extends FrameLayout implements AbsListView.OnScrollListener {

    private int ITEM_LEFT_TO_LOAD_MORE = 10;


    private ProgressBar mProgress;
    private ListView mList;
    private ViewStub mEmpty;


    private float mDividerHeight;
    private int mDivider;
    private boolean mClipToPadding;
    private int mPadding;
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mScrollbarStyle;
    private int mEmptyId;

    private OnMoreListener mOnMoreListener;
    private boolean isLoadingMore;
    private int mSelector;
    private SwipeRefreshLayout mPtrLayout;


    public SwipeRefreshLayout getSwipeToRefresh() {
        return mPtrLayout;
    }

    public ListView getList() {
        return mList;
    }

    public SuperListview(Context context) {
        super(context);
        initView();
    }

    public SuperListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public SuperListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.superlistview);
        try {
            mClipToPadding = a.getBoolean(R.styleable.superlistview_superlv__clipToPadding, false);
            mDivider = a.getInt(R.styleable.superlistview_superlv__divider, 0);
            mDividerHeight = a.getDimension(R.styleable.superlistview_superlv__dividerHeight, 0.0f);
            mPadding = (int)a.getDimension(R.styleable.superlistview_superlv__Padding, -1.0f);
            mPaddingTop = (int)a.getDimension(R.styleable.superlistview_superlv__PaddingTop, 0.0f);
            mPaddingBottom = (int)a.getDimension(R.styleable.superlistview_superlv__PaddingBottom, 0.0f);
            mPaddingLeft = (int)a.getDimension(R.styleable.superlistview_superlv__PaddingLeft, 0.0f);
            mPaddingRight = (int)a.getDimension(R.styleable.superlistview_superlv__PaddingRight, 0.0f);
            mScrollbarStyle = a.getInt(R.styleable.superlistview_superlv__scrollbarStyle, -1);
            mEmptyId = a.getResourceId(R.styleable.superlistview_superlv__empty, 0);
            mSelector = a.getResourceId(R.styleable.superlistview_superlv__selector, 0);
        } finally {
            a.recycle();
        }
    }

    private void initView() {
        if (isInEditMode()) {
            return;
        }
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_progress_listview, this);
        mPtrLayout = (SwipeRefreshLayout) v.findViewById(R.id.ptr_layout);
        mPtrLayout.setEnabled(false);

        mProgress = (ProgressBar) v.findViewById(android.R.id.progress);
        mList = (ListView)v.findViewById(android.R.id.list);
        mEmpty = (ViewStub)v.findViewById(R.id.empty);
        mEmpty.setLayoutResource(mEmptyId);
        mEmpty.inflate();
        mEmpty.setVisibility(View.GONE);

        mList.setClipToPadding(mClipToPadding);
        mList.setDivider(new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent)));
        mList.setDividerHeight((int) mDividerHeight);
        mList.setOnScrollListener(this);
        if (mSelector != 0)
            mList.setSelector(mSelector);

        if (mPadding != -1.0f) {
            mList.setPadding(mPadding,mPadding,mPadding,mPadding);
        } else {
            mList.setPadding(mPaddingLeft,mPaddingTop,mPaddingRight,mPaddingBottom);
        }

        mList.setScrollBarStyle(mScrollbarStyle);
    }

    public void setAdapter(BaseAdapter adapter) {
        mList.setAdapter(adapter);
        mProgress.setVisibility(View.GONE);
        mList.setEmptyView(mEmpty);
        mList.setVisibility(View.VISIBLE);
        mPtrLayout.setRefreshing(false);
        adapter.registerDataSetObserver( new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                isLoadingMore = false;
                mPtrLayout.setRefreshing(false);
                if (mList.getAdapter().getCount() == 0) {
                    mEmpty.setVisibility(View.VISIBLE);
                } else {
                    mEmpty.setVisibility(View.GONE);
                }
            }
        });
        if (adapter == null || adapter.getCount() == 0) {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

    public void clear() {
        mList.setAdapter(null);
    }

    public void showProgress() {
        hideList();
        mProgress.setVisibility(View.VISIBLE);
    }

    public void showList() {
        hideProgress();
        mList.setVisibility(View.VISIBLE);
    }

    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mPtrLayout.setEnabled(true);
        mPtrLayout.setOnRefreshListener(listener);
    }

    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    public void hideList() {
        mList.setVisibility(View.GONE);
    }

    public void setOnScrollListener(AbsListView.OnScrollListener listener) {
        mList.setOnScrollListener(listener);
    }

//    public void setEmptyView(View emptyView) {
//        mEmpty = emptyView;
//    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mList.setOnItemClickListener(listener);
    }

    public android.widget.ListAdapter getAdapter() {
        return mList.getAdapter();
    }

    public int getFirstVisiblePosition() {
        return mList.getFirstVisiblePosition();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public void setupMoreListener(OnMoreListener onMoreListener, int max) {
        mOnMoreListener = onMoreListener;
        ITEM_LEFT_TO_LOAD_MORE = max;
    }

    public void setOnMoreListener(OnMoreListener onMoreListener) {
        mOnMoreListener = onMoreListener;
    }

    public void setNumberBeforeMoreIsCalled(int max) {
        ITEM_LEFT_TO_LOAD_MORE = max;
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        if ((totalItemCount - firstVisibleItem - visibleItemCount) == ITEM_LEFT_TO_LOAD_MORE && !isLoadingMore) {
            isLoadingMore = true;
            if (mOnMoreListener != null)
                mOnMoreListener.loadMore();
        }
    }

    public void removeMoreListener() {
        mOnMoreListener = null;
    }
}
