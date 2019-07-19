package com.jerome.ftablayout;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;

public class FCustomDecoration extends RecyclerView.ItemDecoration {

    private int paceColor;
    private int pace;

    public FCustomDecoration( int pace, int paceColor) {
        this.pace = pace;
        this.paceColor = paceColor;
    }

    //设置ItemView的内嵌偏移长度（inset）
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }

    // 在子视图上设置绘制范围，并绘制内容
    // 绘制图层在ItemView以下，所以如果绘制区域与ItemView区域相重叠，会被遮挡
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    //同样是绘制内容，但与onDraw（）的区别是：绘制在图层的最上层
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Paint paint = new Paint();
        paint.setColor(paceColor);
        // 获取RecyclerView的Child view的个数
        int childCount = parent.getChildCount();
        // 遍历每个Item，分别获取它们的位置信息，然后再绘制对应的分割线
        //mDivider本身为px
        int mDivider = ConvertUtils.dp2px(pace) / 2;
        //控制最后一个不画
        for (int i = 0; i < childCount - 1; i++) {
            // 获取每个Item的位置
            final View child = parent.getChildAt(i);
            // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
            //因为内边距已经把这个间隔加入，所以只用画空出来的间隔就好
            final int left = child.getRight() - mDivider/2;
            final int top = child.getTop();
            // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
            final int right = child.getRight() + mDivider/2;
            final int bottom = child.getBottom();
            // 通过Canvas绘制矩形（分割线）
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
