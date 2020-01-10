package com.example.hao.learnself.date_2019_7_26;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.widget.TextView;

import com.example.hao.learnself.R;

import java.util.Arrays;

public class LockScreenActivity extends Activity {
    private int[] currentGes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        final TextView desc = findViewById(R.id.lock_desc);
        final GestureLockView lockView = findViewById(R.id.lock_screen_view);
        final GestureLockView indicatorView = findViewById(R.id.gesture_indicator);
        lockView.setGestureLockListener(new GestureLockView.GestureLockListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCellAdd(int[] ges) {
            }

            @Override
            public boolean onFinish(int[] ges) {
                desc.setTextColor(ContextCompat.getColor(LockScreenActivity.this, R.color.color_666666));
                if (ges.length < 4) {
                    desc.setTextColor(Color.RED);
                    desc.setText("至少连接4个点，请重试");
                    return false;
                }
                if (currentGes != null && currentGes.length > 0 && !Arrays.equals(currentGes, ges)) {
                    desc.setTextColor(Color.RED);
                    desc.setText("输入有误，请重试");
                    return false;
                }
                return true;
            }

            @Override
            public void onSuccess(int[] ges) {
                desc.setTextColor(ContextCompat.getColor(LockScreenActivity.this, R.color.color_666666));
                if (currentGes == null || currentGes.length == 0) {
                    indicatorView.setGesture(currentGes = ges);
                    desc.setText("请确认图案");
                    new Handler().postDelayed(lockView::clear, 300);
                } else {
                    desc.setText("设置成功");
                }
            }

            @Override
            public void onError(int[] ges) {
                new Handler().postDelayed(lockView::clear, 300);
            }
        });
    }
}
