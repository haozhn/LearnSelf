package com.example.hao.learnself.date_2018_12_28;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.hao.learnself.date_2018_12_28.annotation.BindViewRuntime;

import java.lang.reflect.Field;

public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        injectView(this);
        initView();
    }

    private void injectView(FragmentActivity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        if (fields.length == 0) {
            return;
        }
        for (Field field : fields) {
            BindViewRuntime annotation = field.getAnnotation(BindViewRuntime.class);
            if (annotation != null) {
                int viewId = annotation.value();
                if (viewId == -1) {
                    throw new IllegalArgumentException("-1 is not a validate viewId");
                }
                try {
                    field.setAccessible(true);
                    field.set(activity, activity.findViewById(viewId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
}
