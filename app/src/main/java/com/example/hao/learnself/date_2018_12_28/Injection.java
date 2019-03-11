package com.example.hao.learnself.date_2018_12_28;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Injection {
    private static final String SUFFIX = "$$view_binding";

    public static void inject(@NonNull Activity target) {
        inject(target, target.getWindow().getDecorView());
    }

    public static void inject(@NonNull Object target, @NonNull View view) {
        String className = target.getClass().getName();
        try {
            Class<?> clazz = target.getClass().getClassLoader().loadClass(className + SUFFIX);
            Constructor<Injectable> constructor = (Constructor<Injectable>) clazz.getConstructor(target.getClass(), View.class);
            constructor.newInstance(target, view);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
