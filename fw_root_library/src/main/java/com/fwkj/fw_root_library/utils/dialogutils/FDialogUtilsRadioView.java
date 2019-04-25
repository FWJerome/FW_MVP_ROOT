package com.fwkj.fw_root_library.utils.dialogutils;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ConvertUtils;

import java.util.List;

public class FDialogUtilsRadioView extends LinearLayout {

    private final List<DialogRadioEntity> dialogRadioEntities;

    public FDialogUtilsRadioView(Context context, final List<DialogRadioEntity> dialogRadioEntities) {
        super(context);
        this.dialogRadioEntities = dialogRadioEntities;
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        RadioGroup group = new RadioGroup(context);
        group.setOrientation(VERTICAL);
        group.setGravity(Gravity.CENTER);
        for (DialogRadioEntity entity : dialogRadioEntities) {
            RadioButton button = new RadioButton(context);
            button.setText(entity.getName());
            button.setId(entity.hashCode());
            button.setChecked(entity.isChecked());
            group.addView(button);
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (DialogRadioEntity entity : dialogRadioEntities) {
                    if (checkedId == entity.hashCode()) {
                        group.check(entity.hashCode());
                        entity.setChecked(true);
                    } else {
                        entity.setChecked(false);
                    }
                }
            }
        });
        group.setPadding(0, ConvertUtils.dp2px(20), 0, 0);
        addView(group);
    }

    public DialogRadioEntity getChecked() {
        DialogRadioEntity entity = null;
        for (DialogRadioEntity en : dialogRadioEntities) {
            if (en.isChecked()) {
                entity = en;
                break;
            }
        }
        return entity;
    }
}
