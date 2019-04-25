package com.fwkj.fw_root_library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.fwkj.fw_root_library.R;

public class CountTimeUtils {

    public static CountDownTimer countDown(final Context context, final TextView textView, long waitTime, long interval, final int colorNormal) {
        textView.setEnabled(false);
        textView.setTextColor(colorNormal);
        CountDownTimer countDownTimer = new CountDownTimer(waitTime, interval) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(String.format("剩下 %d S", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                textView.setEnabled(true);
                textView.setText("再次获取");
                textView.setTextColor(colorNormal);
            }
        };
        countDownTimer.start();
        return countDownTimer;
    }
}
