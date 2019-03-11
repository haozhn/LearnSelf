package com.example.hao.learnself.date_2018_12_28.annotation;

import android.annotation.TargetApi;
import android.os.Build;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TargetApi(Build.VERSION_CODES.O)
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE_USE)
public @interface TestAnnotation {
}
