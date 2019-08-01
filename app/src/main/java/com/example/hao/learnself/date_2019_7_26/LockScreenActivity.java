package com.example.hao.learnself.date_2019_7_26;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.hao.learnself.R;

import java.util.Arrays;

public class LockScreenActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        final TextView desc = findViewById(R.id.lock_desc);
        GestureLockView lockView = findViewById(R.id.lock_screen_view);
        lockView.setGestureLockListener(new GestureLockView.GestureLockListener() {
            @Override
            public void onStart() {
                desc.setText("开始设置手势密码");
            }

            @Override
            public void onCellAdd() {

            }

            @Override
            public boolean onFinish(int[] pas) {
                return true;
            }

            @Override
            public void onSuccess(int[] pas) {
                desc.setText("设置成功"+ Arrays.toString(pas));
            }

            @Override
            public void onError(int[] pas) {
                desc.setText("设置失败" + Arrays.toString(pas));
            }
        });
    }
}
