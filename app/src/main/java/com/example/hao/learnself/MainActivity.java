package com.example.hao.learnself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.example.annotation.TestCompiler;
import com.example.hao.learnself.date_2018_12_28.AnnotationTestActivity;
import com.example.hao.learnself.date_2019_12_20.HotfixTestActivity;
import com.example.hao.learnself.date_2019_3_27.GaussianBlurActivity;
import com.example.hao.learnself.date_2019_7_26.LockScreenActivity;

@TestCompiler
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.annotation_btn).setOnClickListener(this);
        findViewById(R.id.blur_btn).setOnClickListener(this);
        findViewById(R.id.lock_screen_btn).setOnClickListener(this);
        findViewById(R.id.hotfix_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.annotation_btn:
                startActivity(new Intent(this, AnnotationTestActivity.class));
                break;
            case R.id.blur_btn:
                startActivity(new Intent(this, GaussianBlurActivity.class));
                break;
            case R.id.lock_screen_btn:
                startActivity(new Intent(this, LockScreenActivity.class));
                break;
            case R.id.hotfix_btn:
                startActivity(new Intent(this, HotfixTestActivity.class));
                break;
        }
    }
}
