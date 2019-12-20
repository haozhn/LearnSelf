package com.example.hao.learnself.date_2019_12_19;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.hao.learnself.R;

public class ASMTestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asm_test);
        costTime();
    }

    private void costTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
