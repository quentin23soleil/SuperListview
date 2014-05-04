package com.quentindommerc.superlistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.quentindommerc.superlistview.superlistview.R;


/**
 * Created by kentin on 24/04/14.
 */
public class SuperGridview extends FrameLayout implements AbsListView.OnScrollListener {

    private int ITEM_LEFT_TO_LOAD_MORE = 10;


    private ProgressBar mProgress;
    private GridView mGrid;
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

    private int mColumns;

    private OnMoreListener mOnMoreListener;
    private boolean isLoadingMore;
    private int mSelector;
    private SwipeRefreshLayout mPtrLayout;
    private int mHorizontalSpacing;
    private int mVerticalSpacing;


    public SwipeRefreshLayout getSwipeToRefresh() {
        return mPtrLayout;
    }

    public GridView getGrid() {
        return mGrid;
    }

    public SuperGridview(Context context) {
        super(context);
        initView();
    }

    public SuperGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public SuperGridview(Context context, AttributeSet attrs, int defStyle) {
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

        TypedArray ag = getContext().obtainStyledAttributes(attrs, R.styleable.supergridview);
        try {
            mColumns = a.getInt(R.styleable.supergridview_supergv__columns, 1);
            mVerticalSpacing = (int) a.getDimension(R.styleable.supergridview_supergv__verticalSpacing, 1);
            mHorizontalSpacing = (int) a.getDimension(R.styleable.supergridview_supergv__horizontalSpacing, 1);
        } finally {
            ag.recycle();
        }
    }

    private void initView() {
        if (isInEditMode()) {
            return;
        }
        View v = LayoutInflater.from(getContext()).inflate(R.layout.view_progress_gridview, this);
        mPtrLayout = (SwipeRefreshLayout) v.findViewById(R.id.ptr_layout);
        mPtrLayout.setEnabled(false);

        mProgress = (ProgressBar) v.findViewById(android.R.id.progress);
        mGrid = (GridView)v.findViewById(android.R.id.list);
        mGrid.setNumColumns(mColumns);
        mGrid.setVerticalSpacing(mVerticalSpacing);
        mGrid.setHorizontalSpacing(mHorizontalSpacing);

        mEmpty = (ViewStub)v.findViewById(R.id.empty);
        mEmpty.setLayoutResource(mEmptyId);
        if (mEmptyId != 0)
            mEmpty.inflate();
        mEmpty.setVisibility(View.GONE);

        mGrid.setClipToPadding(mClipToPadding);
        mGrid.setHorizontalSpacing((int) mDividerHeight);
        mGrid.setVerticalSpacing((int) mDividerHeight);
        mGrid.setOnScrollListener(this);
        if (mSelector != 0)
            mGrid.setSelector(mSelector);

        if (mPadding != -1.0f) {
            mGrid.setPadding(mPadding,mPadding,mPadding,mPadding);
        } else {
            mGrid.setPadding(mPaddingLeft,mPaddingTop,mPaddingRight,mPaddingBottom);
        }

        mGrid.setScrollBarStyle(mScrollbarStyle);
    }

    /**
     * Set the adapter to the gridview
     * Automativally hide the progressbar
     * Set the refresh to false
     * If adapter is empty, then the emptyview is shown
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        mGrid.setAdapter(adapter);
        mProgress.setVisibility(View.GONE);
        if (mEmpty != null && mEmptyId != 0)
            mGrid.setEmptyView(mEmpty);
        mGrid.setVisibility(View.VISIBLE);
        mPtrLayout.setRefreshing(false);
        adapter.registerDataSetObserver( new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                isLoadingMore = false;
                mPtrLayout.setRefreshing(false);
                if (mGrid.getAdapter().getCount() == 0 && mEmptyId != 0) {
                    mEmpty.setVisibility(View.VISIBLE);
                } else if (mEmptyId != 0){
                    mEmpty.setVisibility(View.GONE);
                }
            }
        });
        if ((adapter == null || adapter.getCount() == 0) && mEmptyId != 0) {
            mEmpty.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Remove the adapter from the gridview
     */
    public void clear() {
        mGrid.setAdapter(null);
    }

    /**
     * Show the progressbar
     */
    public void showProgress() {
        hideList();
        mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the progressbar and show the gridview
     */
    public void showList() {
        hideProgress();
        mGrid.setVisibility(View.VISIBLE);
    }

    /**
     * Set the listener when refresh is triggered and enable the SwipeRefreshLayout
     * @param listener
     */
    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mPtrLayout.setEnabled(true);
        mPtrLayout.setOnRefreshListener(listener);
    }

    /**
     * Set the colors for the SwipeRefreshLayout states
     * @param col1
     * @param col2
     * @param col3
     * @param col4
     */
    public void setRefreshingColor(int col1, int col2, int col3, int col4) {
        mPtrLayout.setColorScheme(col1, col2, col3, col4);
    }

    /**
     * Hide the progressbar
     */
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    /**
     * Hide the gridview
     */
    public void hideList() {
        mGrid.setVisibility(View.GONE);
    }

    /**
     * Set the scroll listener for the gridview
     * @param listener
     */
    public void setOnScrollListener(AbsListView.OnScrollListener listener) {
        mGrid.setOnScrollListener(listener);
    }

    /**
     * Set the onItemClickListener for the gridview
     * @param listener
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mGrid.setOnItemClickListener(listener);
    }

    /**
     *
     * @return the gridview adapter
     */
    public android.widget.ListAdapter getAdapter() {
        return mGrid.getAdapter();
    }

    private int getFirstVisiblePosition() {
        return mGrid.getFirstVisiblePosition();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * Sets the More listener
     * @param onMoreListener
     * @param max Number of items before loading more
     */
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

    /**
     * Enable/Disable the More event
     * @param isLoadingMore
     */
    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        if (((totalItemCount - firstVisibleItem - visibleItemCount) == ITEM_LEFT_TO_LOAD_MORE || (totalItemCount - firstVisibleItem - visibleItemCount) == 0 && totalItemCount > visibleItemCount) && !isLoadingMore) {
            isLoadingMore = true;
            if (mOnMoreListener != null)
                mOnMoreListener.loadMore();
        }
    }

    /**
     * Remove the moreListener
     */
    public void removeMoreListener() {
        mOnMoreListener = null;
    }
}
