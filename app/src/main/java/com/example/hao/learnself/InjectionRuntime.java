package com.example.hao.learnself;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.hao.learnself.date_2018_12_28.annotation.BindViewRuntime;
import com.example.hao.learnself.date_2018_12_28.annotation.OnClickRuntime;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InjectionRuntime {
    public void inject(Activity activity) {
        inject(activity, activity.getWindow().getDecorView());
    }

    public void inject(Object target, View view) {
        injectView(target, view);
        injectListener(target, view);
    }

    private void injectView(Object target, View rootView) {
        // 获取类中的所有成员变量
        Field[] fields = target.getClass().getDeclaredFields();
        if (fields.length == 0) {
            return;
        }
        // 遍历属性找到被BindViewRuntime注解的字段，然后通过反射赋值
        for (Field field : fields) {
            BindViewRuntime annotation = field.getAnnotation(BindViewRuntime.class);
            if (annotation != null) {
                int viewId = annotation.value();
                if (viewId == -1) {
                    throw new IllegalArgumentException("-1 is not a validate viewId");
                }
                try {
                    field.setAccessible(true);
                    field.set(target, rootView.findViewById(viewId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void injectListener(Object target, View rootView) {
        // 获取类中的所有方法
        Method[] methods = target.getClass().getDeclaredMethods();
        if (methods.length == 0) {
            return;
        }
        // 遍历找出被OnClickRuntime注解的方法
        for (Method method : methods) {
            OnClickRuntime annotation = method.getAnnotation(OnClickRuntime.class);
            if (annotation != null) {
                int viewId = annotation.value();
                if (viewId == -1) {
                    throw new IllegalArgumentException("-1 is not a validate viewId");
                }
                View view = rootView.findViewById(viewId);
                try {
                    // 反射获取View中的setOnClickListener方法，并赋值一个匿名的OnClickListener
                    Method viewMethod = view.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
                    viewMethod.setAccessible(true);
                    viewMethod.invoke(view, (View.OnClickListener) v -> {
                        try {
                            // 执行被OnClickRuntime注解的方法
                            method.setAccessible(true);
                            method.invoke(target);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
