package com.example.hao.learnself.date_2018_12_28;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.hao.learnself.InjectionRuntime;
import com.example.hao.learnself.date_2018_12_28.annotation.BindViewRuntime;
import com.example.hao.learnself.date_2018_12_28.annotation.OnClickRuntime;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        new InjectionRuntime().inject(this);
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
}
