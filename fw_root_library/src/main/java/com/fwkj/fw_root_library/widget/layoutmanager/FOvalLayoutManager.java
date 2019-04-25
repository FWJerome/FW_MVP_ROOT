package com.fwkj.fw_root_library.widget.layoutmanager;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 付俊杰
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description :  滚动效果核心在于mScrollX的使用
 */

public class FOvalLayoutManager extends RecyclerView.LayoutManager {
    private int mTotalX;
    private int mCenterX;
    private int mCanSeePart;
    private int mHeight;
    private int mWidth;
    private int mScrollX = 0;
    private int mSpace;
    private RecyclerView.Recycler recycler;

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 相对于父控件的高的一个比例
     * @Date 9:29 2019/3/28
     * @Param
     **/
    public FOvalLayoutManager() {

    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0 || state.isPreLayout()) {
            return;
        }
        //没有Item，界面空着吧
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        //state.isPreLayout()是支持动画的
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        //onLayoutChildren方法在RecyclerView 初始化时 会执行两遍
        detachAndScrapAttachedViews(recycler);
        //初始化x的总偏移量
        mTotalX = 0;
        //计算X的中心点
        mCenterX = getHorizontalSpace() / 2;
        //左右两边的可见部分宽度
        mCanSeePart = mCenterX / 4;
        mWidth = 0;
        mHeight = 0;
        this.recycler = recycler;
        // 放置视图
        for (int i = 0; i < getItemCount(); i++) {
            layoutTarget(i);
        }
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 放置目标View 通过setTranslationX(),setTranslationY()控制显示的位置
     * @Date 10:19 2019/3/28
     * @Param
     **/
    private void layoutTarget(int position) {
        View viewForPosition = recycler.getViewForPosition(position);
        addView(viewForPosition);
        //对子View进行测量
        measureChild(viewForPosition, 0, 0);
        if (mHeight == 0) {
            mHeight = getDecoratedMeasuredHeight(viewForPosition);
        }
        if (mWidth == 0) {
            mWidth = getDecoratedMeasuredWidth(viewForPosition);
        }
        layoutDecorated(viewForPosition, 0, 0, mWidth, mHeight);
        //位置矫正
        int x;
        //每个间距
        mSpace = mCenterX - mWidth / 2 - mCanSeePart;
        if (mTotalX == 0) {
            x = mCenterX - mWidth / 2;
        } else {
            x = mTotalX;
        }
        //在移动时加上移动距离
        int translationX = x + mScrollX;
        //判断这个View是否在中心那个视图的宽度范围内
        boolean isCenter = judgeIsCenter(translationX + mWidth / 2);
        viewForPosition.setTranslationX(translationX);
        int y = 0;
        if (!isCenter) {
            y = getVerticalSpace() / 4;
        }
        viewForPosition.setTranslationY(y);
        //当前坐标+item宽度+间隔   这样得到的是下一个坐标
        mTotalX = x + mWidth + mSpace;
        //如果右坐标小于0就回收
        if (translationX + mWidth <= 0) {
            removeAndRecycleView(viewForPosition, recycler);
        }
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 判断此点是否在中心视图范围
     * @Date 15:03 2019/3/28
     * @Param
     **/
    private boolean judgeIsCenter(int x) {
        return mCenterX - mWidth / 2 <= x && mCenterX + mWidth / 2 >= x;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //滚动
        if (getItemCount() == 3) {
            return 0;
        }
        mScrollX += -dx;
        if (!isRightLimit() && !isLeftLimit()) {
            // 放置视图
            for (int i = 0; i < getItemCount(); i++) {
                layoutTarget(i);
            }
        }
        return dx;
    }

    //右边界判断,以最后一个item的左上角为右边界
    private boolean isRightLimit() {
        boolean b = false;
        int rightSide = -(((getItemCount() - 1) * mWidth) + ((getItemCount() - 1) * mSpace)) + (mCenterX + mWidth / 2);
        if (mScrollX <= rightSide) {
            mScrollX = rightSide;
            b = true;
        }
        return b;
    }

    //左边界判断
    private boolean isLeftLimit() {
        boolean b = false;
        if (mScrollX >= 0) {
            mScrollX = 0;
            b = true;
        }
        return b;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 获取recycleview的高度
     * @Date 9:28 2019/3/28
     * @Param
     **/
    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 获取recycleview的宽度
     * @Date 9:28 2019/3/28
     * @Param
     **/
    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
