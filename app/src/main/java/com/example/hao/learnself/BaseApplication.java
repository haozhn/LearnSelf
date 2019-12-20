package com.example.hao.learnself;

import android.app.Application;
import android.content.Context;

import com.example.hao.learnself.date_2019_12_20.ClassPatchUtil;

public class BaseApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ClassPatchUtil.patchClass(base);
    }
}
