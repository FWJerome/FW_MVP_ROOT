package com.fwkj.fw_root_library.utils.dialogutils;

import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fwkj.fw_root_library.R;

import razerdp.basepopup.BasePopupWindow;

public class DialogPopup extends BasePopupWindow {

    private FDialogUtils.Builder builder;
    private TextView mTitleView;
    private LinearLayout mCustomView;
    private TextView mTvPosition;
    private TextView mTvNegitive;
    private FDialogUtils.Builder.OnClickListener positiveListener;
    private FDialogUtils.Builder.OnClickListener negativeListener;
    private EditText editTextView;

    public DialogPopup(FDialogUtils.Builder builder) {
        super(builder.context);
        this.builder = builder;
    }

    @Override
    public View onCreateContentView() {
        View view = createPopupById(R.layout.popup_fdialog);
        mTitleView = view.findViewById(R.id.tvTitle);
        mCustomView = view.findViewById(R.id.viewCustom);
        mTvPosition = view.findViewById(R.id.tvPosition);
        mTvNegitive = view.findViewById(R.id.tvNegative);
        mTvPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (builder.type) {
                    case FDialogType.TYPE_INPUT:
                        if (ObjectUtils.isEmpty(editTextView.getText().toString()) && !builder.isEmpty) {
                            ToastUtils.showShort("请输入信息");
                            return;
                        }
                        break;
                    default:
                }
                dismiss();
                if (positiveListener == null) {
                    return;
                }
                positiveListener.listener(DialogPopup.this);
            }
        });
        mTvNegitive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (negativeListener == null) {
                    return;
                }
                negativeListener.listener(DialogPopup.this);
            }
        });
        return view;
    }

    // 以下为可选代码（非必须实现）
    // 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }

    public void setEditTextView(EditText view) {
        this.editTextView = view == null ? new EditText(getContext()) : view;
        mCustomView.addView(view);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }

    public void setPositiveButton(String positiveText, FDialogUtils.Builder.OnClickListener positiveListener) {
        if (ObjectUtils.isEmpty(positiveText)) {
            mTvPosition.setVisibility(View.INVISIBLE);
            return;
        }
        mTvPosition.setText(positiveText);
        this.positiveListener = positiveListener;
    }

    public void setNegativeButton(String negitiveText, FDialogUtils.Builder.OnClickListener negativeListener) {
        if (ObjectUtils.isEmpty(negitiveText)) {
            mTvNegitive.setVisibility(View.INVISIBLE);
            return;
        }
        mTvNegitive.setText(negitiveText);
        this.negativeListener = negativeListener;
    }

    public String getInputText() {
        return editTextView.getText().toString();
    }

    public void setView(View view) {
        mCustomView.addView(view);
    }
}
