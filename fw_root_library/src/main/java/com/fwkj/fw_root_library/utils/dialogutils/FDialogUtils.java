package com.fwkj.fw_root_library.utils.dialogutils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

public class FDialogUtils {
    private Builder builder;
    private DialogPopup mDialogPopup;

    private FDialogUtils(Builder builder) {
        this.builder = builder;
        initDialog(chooseView());
    }

    private void initDialog(final View view) {
        mDialogPopup = new DialogPopup(builder);
        if (builder.type == FDialogType.TYPE_INPUT) {
            mDialogPopup.setEditTextView((EditText) view);
        }else {
            mDialogPopup.setView(view);
        }
        mDialogPopup.setTitle(builder.title);
        if (builder.onPositiveListener != null) {
            mDialogPopup.setPositiveButton(builder.positiveText, builder.onPositiveListener);
        }
        if (builder.onNegitiveListener != null) {
            mDialogPopup.setNegativeButton(builder.negitiveText, builder.onPositiveListener);
        }
    }

    private View chooseView() {
        View view = null;
        switch (builder.type) {
            case FDialogType.TYPE_RADIO:

                break;
            case FDialogType.TYPE_INPUT:
                view = new EditText(builder.context);
                ((EditText) view).setHint(builder.hint);
                break;
            case FDialogType.TYPE_CUSTOM:
                view = builder.view;
                break;
            default:
        }
        return view;
    }

    public void show() {
        mDialogPopup.setPopupGravity(Gravity.CENTER);
        mDialogPopup.showPopupWindow();
    }

    public static class Builder {
        Context context;
        int type;
        String positiveText;
        String negitiveText;
        OnClickListener onPositiveListener;
        OnClickListener onNegitiveListener;
        String title;
        String hint;
        boolean isEmpty;
        View view;

        public Builder(Context context, int type) {
            this.context = context;
            this.type = type;
        }

        public Builder positiveText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public Builder negitiveText(String negitiveText) {
            this.negitiveText = negitiveText;
            return this;
        }

        public Builder onPositiveListener(OnClickListener onPositiveListener) {
            this.onPositiveListener = onPositiveListener;
            return this;
        }

        public Builder onNegitiveListener(OnClickListener onNegitiveListener) {
            this.onNegitiveListener = onNegitiveListener;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public FDialogUtils build() {
            return new FDialogUtils(this);
        }

        public Builder hint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder inputEnableNull(boolean isEmpty) {
            this.isEmpty = isEmpty;
            return this;
        }

        public Builder addCustomView(View view) {
            this.view = view;
            return this;
        }

        public interface OnClickListener {
            void listener(DialogPopup dialog);
        }
    }
}

