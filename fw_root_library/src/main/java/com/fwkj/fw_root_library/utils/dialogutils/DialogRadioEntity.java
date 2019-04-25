package com.fwkj.fw_root_library.utils.dialogutils;

public class DialogRadioEntity {
    private String name;
    private boolean isChecked;

    public DialogRadioEntity(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public DialogRadioEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "DialogRadioEntity{" +
                "name='" + name + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
