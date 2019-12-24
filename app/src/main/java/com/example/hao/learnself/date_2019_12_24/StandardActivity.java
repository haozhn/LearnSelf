package com.example.hao.learnself.date_2019_12_24;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.hao.learnself.R;

import static com.example.hao.learnself.MainActivity.TAG;

public class StandardActivity extends Activity {

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "StandardActivity->onAttachedToWindow");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "StandardActivity->onCreate");
        setContentView(R.layout.activity_standard);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "StandardActivity->onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "StandardActivity->onRestoreInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "StandardActivity->onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "StandardActivity->onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "StandardActivity->onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "StandardActivity->onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "StandardActivity->onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "StandardActivity->onDestroy");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG, "StandardActivity->onDetachedFromWindow");
    }
}
