package com.example.hao.learnself.date_2019_12_24;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.hao.learnself.R;

import static com.example.hao.learnself.MainActivity.TAG;

public class TransparentActivity extends Activity {

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "TransparentActivity->onAttachedToWindow");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "TransparentActivity->onCreate");
        setContentView(R.layout.activity_transparent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "TransparentActivity->onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "TransparentActivity->onRestoreInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "TransparentActivity->onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "TransparentActivity->onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "TransparentActivity->onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "TransparentActivity->onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "TransparentActivity->onDestroy");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG, "TransparentActivity->onDetachedFromWindow");
    }
}
