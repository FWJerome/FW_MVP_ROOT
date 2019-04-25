package com.fwkj.fw_root_library.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.fwkj.fw_root_library.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author 付俊杰
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description :
 * 控件一共两层
 * FTechnologicalProcess(RelativeLayout)
 * ---mDashView(虚线)
 * ---mHorizontalLLlForImageView(主要的)
 * ---图片层
 * ---文字层
 * ---指示器
 */

public class FTechnologicalProcess extends RelativeLayout {
    //外层圆 例如:IMGOUT0 IMGOUT1....
    private static final String TAG_IMG_OUT = "IMGOUT";
    //内层圆 例如:IMGIN0 IMGIN1....
    private static final String TAG_IMG_IN = "IMGIN";
    //两个圆的根布局
    private static final String TAG_RELATIVE = "RELATIVE";
    //文字
    private static final String TAG_TEXT = "TEXT";
    /**
     * 是否展示底部指示器
     */
    private final boolean showBottomIndicate;
    private Context context;
    /**
     * 个数
     */
    private int items = 0;
    /**
     * 指示器，就是那个三角
     */
    private ImageView mImgIndicate;
    /**
     * 流程图片的容器
     */
    private LinearLayout mHorizontalLLlForImageView;
    /**
     * 虚线
     */
    private View mDashView;
    //光标单位移动的距离
    private float mIndicateUnitDistance = 0;
    private int rootMargin = ConvertUtils.dp2px(15);
    /**
     * 图片资源
     */
    private int[] mImageResIds;
    private String[] mTitles;
    private List<ImageView> mImgOuts;
    private List<ImageView> mImgIns;
    private List<TextView> mTextViews;
    private List<RelativeLayout> mRelativeLayouts = new ArrayList<>();
    private List<View> mTagViews;
    private Listener listener;
    private int img_rzfinish;

    public FTechnologicalProcess(Context context) {
        this(context, null);
    }

    public FTechnologicalProcess(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FTechnologicalProcess(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FTechnologicalProcess);
        items = typedArray.getInt(R.styleable.FTechnologicalProcess_items, 0);
        showBottomIndicate = typedArray.getBoolean(R.styleable.FTechnologicalProcess_showbottomIndicate, true);
        typedArray.recycle();
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initLL();
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 初始化布局
     * @Date 9:01 2019/3/26
     * @Param
     **/
    private void initLL() {
        //虚线
        mDashView = new View(context);
        mDashView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(4)));
        mDashView.setBackground(context.getResources().getDrawable(R.drawable.bg_dash_line));
        addView(mDashView);
        //最外层的LL
        LinearLayout llRoot = new LinearLayout(context);
        LayoutParams rootParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        rootParams.leftMargin = rootMargin;
        rootParams.rightMargin = rootMargin;
        llRoot.setLayoutParams(rootParams);
        llRoot.setWeightSum(items);
        llRoot.setOrientation(LinearLayout.VERTICAL);
        addView(llRoot);
        //第一层的图片
        mHorizontalLLlForImageView = getLinearLayoutByWeightSum(items);
        for (int i = 0; i < mHorizontalLLlForImageView.getWeightSum(); i++) {
            RelativeLayout relativeLayout = new RelativeLayout(context);
            relativeLayout.setTag(TAG_RELATIVE + i);
            mRelativeLayouts.add(relativeLayout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            relativeLayout.setLayoutParams(params);
            mHorizontalLLlForImageView.addView(relativeLayout);

            ImageView imageOut = new ImageView(context);
            imageOut.setTag(TAG_IMG_OUT + i);
            imageOut.setImageResource(R.drawable.circle);
            relativeLayout.addView(imageOut);
            imageOut.setVisibility(GONE);
            LayoutParams pa = new LayoutParams(ConvertUtils.dp2px(60), ConvertUtils.dp2px(60));
            pa.addRule(RelativeLayout.CENTER_IN_PARENT);
            imageOut.setLayoutParams(pa);

            ImageView imageIn = new ImageView(context);
            imageIn.setTag(TAG_IMG_IN + i);
            imageIn.setScaleType(ImageView.ScaleType.FIT_XY);
            relativeLayout.addView(imageIn);
            LayoutParams paIn = new LayoutParams(ConvertUtils.dp2px(50), ConvertUtils.dp2px(50));
            paIn.addRule(RelativeLayout.CENTER_IN_PARENT);
            imageIn.setLayoutParams(paIn);
        }
        llRoot.addView(mHorizontalLLlForImageView);

        //第二层文字
        LinearLayout horizontalLlForTextView = getLinearLayoutByWeightSum(items);
        LayoutParams textParams = (LayoutParams) horizontalLlForTextView.getLayoutParams();
        textParams.topMargin = ConvertUtils.dp2px(5);
        horizontalLlForTextView.setLayoutParams(textParams);
        for (int i = 0; i < horizontalLlForTextView.getWeightSum(); i++) {
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#ffffff"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            textView.setTag(TAG_TEXT + i);
            textView.setLayoutParams(params);
            horizontalLlForTextView.addView(textView);
        }
        llRoot.addView(horizontalLlForTextView);
        //添加指示器
        mImgIndicate = new ImageView(context);
        mImgIndicate.setImageResource(R.mipmap.img_sanjiao);
        mImgIndicate.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ConvertUtils.dp2px(10), ConvertUtils.dp2px(10));
        params.topMargin = ConvertUtils.dp2px(5);
        mImgIndicate.setLayoutParams(params);
        llRoot.addView(mImgIndicate);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //指示器位置,注意执行动画后会再次刷新这里，所以需要知道当前指示器应该在哪个view上
        int focusableView = getFocusableView() == -1 ? 0 : getFocusableView();
        View childAt = mHorizontalLLlForImageView.getChildAt(focusableView);
        mIndicateUnitDistance = childAt.getWidth();
        mImgIndicate.setTranslationX(childAt.getWidth() / 2f - mImgIndicate.getWidth() / 2f + childAt.getX());
        //虚线位置
        mDashView.setTranslationY(mHorizontalLLlForImageView.getHeight() / 2);
        //线上指示器位置
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 获取一个LinearLayout
     * @Date 9:27 2019/3/26
     * @Param
     **/
    private LinearLayout getLinearLayoutByWeightSum(int items) {
        LinearLayout ll = new LinearLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.setWeightSum(items);
        ll.setLayoutParams(params);
        ll.setGravity(Gravity.CENTER);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        return ll;
    }

    public void setImageResIds(int[] imageResIds) {
        mImageResIds = imageResIds;
    }

    public int[] getImageResIds() {
        return mImageResIds;
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
    }

    public String[] getTitles() {
        return mTitles;
    }

    public void init() {
        //递归获取相关数据
        mImgOuts = new ArrayList<>();
        mImgIns = new ArrayList<>();
        mTextViews = new ArrayList<>();
        mTagViews = new ArrayList<>();
        mTagViews = getTagViews(mTagViews, this);
        for (View view : mTagViews) {
            String string = view.getTag().toString();
            switch (string.substring(0, string.length() - 1)) {
                case TAG_IMG_OUT:
                    mImgOuts.add((ImageView) view);
                    break;
                case TAG_IMG_IN:
                    mImgIns.add((ImageView) view);
                    break;
                case TAG_TEXT:
                    mTextViews.add((TextView) view);
                    break;
                default:
            }
        }
        //初始化数据
        mImgOuts.get(0).setVisibility(VISIBLE);
        for (ImageView imageView : mImgIns) {
            String string = imageView.getTag().toString();
//            imageView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String s = v.getTag().toString();
//                    int position = Integer.parseInt(s.substring(s.length() - 1));
//                    ToastUtils.showShort("点击了" + s);
//                    next(position);
//                }
//            });
            int index = Integer.parseInt(string.substring(string.length() - 1));
            imageView.setImageResource(mImageResIds[index]);
        }
        for (TextView textView : mTextViews) {
            String string = textView.getTag().toString();
            int index = Integer.parseInt(string.substring(string.length() - 1));
            textView.setText(mTitles[index]);
        }
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 切换动画通过TAG
     * @Date 17:03 2019/3/26
     * @Param
     */
    public void next() {
        //获取当前状态的view的位置
        int focusablePosition = getFocusableView();
        if (focusablePosition == mTitles.length - 1) {
            return;
        }
        //消失动画执行
        dimissAnima(focusablePosition, focusablePosition + 1);
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 消失动画
     * @Date 17:23 2019/3/26
     * @Param
     **/
    private void dimissAnima(int startPosition, int endPosition) {
        final RelativeLayout focusableRela = (RelativeLayout) getView(TAG_RELATIVE, startPosition);
        final ImageView startImgOut = (ImageView) getView(TAG_IMG_OUT, startPosition);
        final ImageView endImgOut = (ImageView) getView(TAG_IMG_OUT, endPosition);
        final ImageView startImageIn = (ImageView) getView(TAG_IMG_IN, startPosition);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(focusableRela, "scaleY", 1f, 1.5f, 0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(focusableRela, "scaleX", 1f, 1.5f, 0f);
        //指示器移动到下一个
        float indicateNewX = mImgIndicate.getTranslationX() + mIndicateUnitDistance;
        ObjectAnimator translationIndicateX = ObjectAnimator.ofFloat(mImgIndicate, "translationX", mImgIndicate.getTranslationX(), indicateNewX);
        animatorSet.playTogether(scaleY, scaleX, translationIndicateX);
        animatorSet.setDuration(500);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                focusableRela.setVisibility(GONE);
                startImgOut.setVisibility(INVISIBLE);
                startImageIn.setImageResource(img_rzfinish);
                endImgOut.setVisibility(VISIBLE);
                //执行完成动画
                finishAnimation(focusableRela);
                listener.animationFinish(getFocusableView());
            }
        });
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 完成动画
     * @Date 9:16 2019/3/27
     * @Param
     **/
    private void finishAnimation(final RelativeLayout focusableRela) {
        focusableRela.setVisibility(VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(focusableRela, "scaleY", 0f, 1.5f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(focusableRela, "scaleX", 0f, 1.5f, 1f);
        animatorSet.playTogether(scaleY, scaleX);
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 获取当前焦点的位置
     * @Date 17:13 2019/3/26
     * @Param
     **/
    private int getFocusableView() {
        for (ImageView view : mImgOuts) {
            if (view.getVisibility() == VISIBLE) {
                String s = view.getTag().toString();
                return Integer.parseInt(s.substring(s.length() - 1));
            }
        }
        return -1;
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 获取View通过TAG和位置
     * @Date 17:08 2019/3/26
     * @Param
     **/
    private View getView(String tagType, int position) {
        View view = null;
        switch (tagType) {
            case TAG_IMG_OUT:
                view = mImgOuts.get(position);
                break;
            case TAG_IMG_IN:
                view = mImgIns.get(position);
                break;
            case TAG_TEXT:
                view = mTextViews.get(position);
                break;
            case TAG_RELATIVE:
                view = mRelativeLayouts.get(position);
                break;
            default:
        }
        return view;
    }

    /**
     * @return
     * @Author 付俊杰
     * @Description //TODO 递归获取所有TAG的View
     * @Date 17:02 2019/3/26
     * @Param
     **/
    private List<View> getTagViews(List<View> tagViews, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                getTagViews(tagViews, (ViewGroup) child);
            }
            Object childTag = child.getTag();
            String tag = childTag == null ? "" : childTag.toString();
            if (tag.contains(TAG_IMG_OUT) || tag.contains(TAG_IMG_IN) || tag.contains(TAG_TEXT)) {
                tagViews.add(child);
            }
        }
        return tagViews;
    }

    public void addListener(Listener listener) {
        this.listener = listener;
    }

    public void setFinsihImg(int img_rzfinish) {
        this.img_rzfinish = img_rzfinish;
    }

    public void scrollToPosition(int position) {
        for (int i = 0; i <= position-1; i++) {
            ImageView imgIn = (ImageView) getView(TAG_IMG_IN, i);
            imgIn.setImageResource(img_rzfinish);
            imgIn.setVisibility(VISIBLE);
            ImageView imgOut = (ImageView) getView(TAG_IMG_OUT, i);
            imgOut.setVisibility(INVISIBLE);
        }
        ImageView imgOut = (ImageView) getView(TAG_IMG_OUT, position);
        imgOut.setVisibility(VISIBLE);
        float indicateNewX = mImgIndicate.getTranslationX() + mIndicateUnitDistance * position;
        mImgIndicate.setTranslationX(indicateNewX);
    }

    public interface Listener {
        void animationFinish(int position);
    }
}
