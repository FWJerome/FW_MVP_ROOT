package com.fwkj.fw_root_library.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.fwkj.fw_root_library.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;


/**
 * @author 付俊杰
 * @email 1025461682@qq.com
 * @phone 17684220995
 * @file description : 类似于6个密码输入框
 */

public class CodePasEditText extends LinearLayout {

    private Context context;
    private View mItemView;
    //单个输入框的宽度
    private int mItemWidth;
    private InputMethodManager mInputMethodManager;
    private List<EditText> mEditTexts;

    public CodePasEditText(Context context) {
        this(context, null);
    }

    public CodePasEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodePasEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setGravity(Gravity.CENTER_HORIZONTAL);
        mItemWidth = ScreenUtils.getScreenWidth() / 10;
        mEditTexts = new ArrayList<>();
        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        for (int i = 0; i < 6; i++) {
            addView(initItem(i));
        }
        setFocusable(true);//设置输入框可聚集
        setFocusableInTouchMode(true);//设置触摸聚焦
        requestFocus();//请求焦点
        findFocus();//获取焦
    }

    /**
     * 遍历焦点位置，谁为空就给谁
     */
    private void initFocus() {
        for (EditText editText : mEditTexts) {
            if (ObjectUtils.isEmpty(editText.getText().toString())) {
                editText.setFocusable(true);//设置输入框可聚集
                editText.setFocusableInTouchMode(true);//设置触摸聚焦
                editText.requestFocus();//请求焦点
                editText.findFocus();//获取焦
                mInputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);// 显示输入法
                return;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private View initItem(int tag) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, mItemWidth / 3, 0);
        linearLayout.setLayoutParams(params);

        EditText editText = new EditText(context);
        editText.setBackground(null);
        editText.setGravity(Gravity.CENTER);
        editText.setTag(tag);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        editText.setLayoutParams(new LinearLayoutCompat.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    delete((int) v.getTag());
                }
                return false;
            }
        });

        View view = new View(context);
        view.setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(mItemWidth, 5));

        linearLayout.addView(editText);
        linearLayout.addView(view);
        mEditTexts.add(editText);
        return linearLayout;
    }

    private void delete(int tag) {
        for (EditText editText : mEditTexts) {
            if ((int) editText.getTag() == tag - 1) {
                editText.setFocusable(true);//设置输入框可聚集
                editText.setFocusableInTouchMode(true);//设置触摸聚焦
                editText.requestFocus();//请求焦点
                editText.findFocus();//获取焦
                mInputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);// 显示输入法
            }
        }
    }

    public String getText() {
        StringBuilder s = new StringBuilder();
        for (EditText editText : mEditTexts) {
            Editable text = editText.getText();
            if (text == null) {
                continue;
            }
            s.append(text.toString());
        }
        return s.toString();
    }
}
