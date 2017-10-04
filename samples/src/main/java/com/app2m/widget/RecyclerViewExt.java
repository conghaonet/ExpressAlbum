package com.app2m.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by CongHao on 2017/10/4.
 * E-mail: hao.cong@app2m.com
 */

public class RecyclerViewExt extends RecyclerView {
    private OnScrollListener mOnScrollListener;
    private boolean mIsScrollUp;
    private boolean mIsControlledOnScrollStateChanged;
    private float mActionDownY;
    private OnLastItemVisibleListener mOnLastItemVisibleListener;
    private OnTouchListener mOnTouchListener;

    public RecyclerViewExt(Context context) {
        this(context, (AttributeSet)null);
    }

    public RecyclerViewExt(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewExt(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mOnScrollListener = new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(RecyclerView.SCROLL_STATE_IDLE == newState && mIsScrollUp && mIsControlledOnScrollStateChanged) {
                    findLastItem();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) {
                    mIsControlledOnScrollStateChanged = true;
                    mIsScrollUp = true;
                } else if( dy == 0) {
                    mIsControlledOnScrollStateChanged = false;
                } else {
                    mIsControlledOnScrollStateChanged = true;
                    mIsScrollUp = false;
                }
            }
        };
        //当RecyclerView不能滚动时，需要动过onTouch判断是否需要执行上拉动作。
        mOnTouchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!mIsControlledOnScrollStateChanged) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mActionDownY = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (mActionDownY <= 0) {
                                mActionDownY = event.getY();
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if (mActionDownY - event.getY() > 0) { //向上滑动
                                mIsScrollUp = true;
                                findLastItem();
                            } else {
                                mIsScrollUp = false;
                            }
                            mActionDownY = -1;
                            break;
                        default:
                            break;
                    }
                }
                return false;
            }
        };
        this.addOnScrollListener(this.mOnScrollListener);
        this.setOnTouchListener(mOnTouchListener);
    }
    private void findLastItem() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        int lastPosition = -1;
        if (layoutManager instanceof LinearLayoutManager) {
            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if(getLayoutManager() instanceof StaggeredGridLayoutManager) {
            //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
            //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
            lastPosition = findMax(lastPositions);
        }
        //不满一屏时，自动加载更多。
        if(mIsScrollUp && lastPosition + 1 == this.getAdapter().getItemCount()) {
            if(mOnLastItemVisibleListener != null) {
                mOnLastItemVisibleListener.onLastItemVisible(lastPosition);
            }
//            mAdapter.setDefaultFooterView();
//            loadData(mData.size());
        }
    }
    //当LayoutManager为StaggeredGridLayoutManager时，找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void setOnLastItemVisibleListener(OnLastItemVisibleListener onLastItemVisibleListener) {
        this.mOnLastItemVisibleListener = onLastItemVisibleListener;
    }
    public interface OnLastItemVisibleListener {
        void onLastItemVisible(int lastItemPosition);
    }

}
