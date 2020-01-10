package com.example.hao.learnself;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.view.View;

import com.example.annotation.TestCompiler;
import com.example.hao.learnself.date_19_12_25_22.AnimationTestActivity;
import com.example.hao.learnself.date_19_12_27.HandlerActivity;
import com.example.hao.learnself.date_2018_12_28.AnnotationTestActivity;
import com.example.hao.learnself.date_2019_12_20.HotfixTestActivity;
import com.example.hao.learnself.date_2019_12_23.RouterHandler;
import com.example.hao.learnself.date_2019_12_23.RouterService;
import com.example.hao.learnself.date_2019_12_24.ActivityLifecycleTest;
import com.example.hao.learnself.date_2019_12_24_10.TouchTestActivity;
import com.example.hao.learnself.date_2019_12_25.ListViewReuseActivity;
import com.example.hao.learnself.date_2019_3_27.GaussianBlurActivity;
import com.example.hao.learnself.date_2019_7_26.LockScreenActivity;
import com.haozhn.learnself.jetpack.JetpackActivity;

import java.lang.reflect.Proxy;

@TestCompiler
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    public static final String TAG = "haozhinan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.annotation_btn).setOnClickListener(this);
        findViewById(R.id.blur_btn).setOnClickListener(this);
        findViewById(R.id.lock_screen_btn).setOnClickListener(this);
        findViewById(R.id.hotfix_btn).setOnClickListener(this);
        findViewById(R.id.touch_test_btn).setOnClickListener(this);
        findViewById(R.id.proxy_btn).setOnClickListener(this);
        findViewById(R.id.activity_lifecycle_btn).setOnClickListener(this);
        findViewById(R.id.animation_btn).setOnClickListener(this);
        findViewById(R.id.list_recycle_btn).setOnClickListener(this);
        findViewById(R.id.handler_btn).setOnClickListener(this);
        findViewById(R.id.jetpack_btn).setOnClickListener(this);
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
            case R.id.touch_test_btn:
                startActivity(new Intent(this, TouchTestActivity.class));
            case R.id.proxy_btn:
                RouterService service = (RouterService) Proxy.newProxyInstance(RouterService.class.getClassLoader(),
                        new Class[]{RouterService.class}, new RouterHandler(this));
                service.gotoMain("I am from MainActivity");
                break;
            case R.id.activity_lifecycle_btn:
                startActivity(new Intent(this, ActivityLifecycleTest.class));
                break;
            case R.id.animation_btn:
                startActivity(new Intent(this, AnimationTestActivity.class));
                break;
            case R.id.list_recycle_btn:
                startActivity(new Intent(this, ListViewReuseActivity.class));
                break;
            case R.id.handler_btn:
                startActivity(new Intent(this, HandlerActivity.class));
                break;
            case R.id.jetpack_btn:
                startActivity(new Intent(this, JetpackActivity.class));
                break;
        }
    }
}
