package com.example.hao.learnself.date_2019_12_23;

import android.content.Context;
import android.content.Intent;

import com.example.annotation.Page;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RouterHandler implements InvocationHandler {
    private Context context;
    public RouterHandler(Context context) {
        this.context = context;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Page page = method.getAnnotation(Page.class);
        if (page != null) {
            Class clazz = page.value();
            Intent intent = new Intent(context, clazz);
            context.startActivity(intent);
        }
        return null;
    }
}
