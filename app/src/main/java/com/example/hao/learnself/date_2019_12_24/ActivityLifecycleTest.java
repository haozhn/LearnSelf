package com.example.hao.learnself.date_2019_12_24;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.hao.learnself.R;

import static com.example.hao.learnself.MainActivity.TAG;

public class ActivityLifecycleTest extends Activity implements View.OnClickListener {

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "ActivityLifecycleTest->onAttachedToWindow");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "ActivityLifecycleTest->onCreate");
        setContentView(R.layout.activity_life_cycle);
        findViewById(R.id.standard_btn).setOnClickListener(this);
        findViewById(R.id.transparent_btn).setOnClickListener(this);
        findViewById(R.id.dialog_btn).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "ActivityLifecycleTest->onStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "ActivityLifecycleTest->onRestoreInstanceState");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "ActivityLifecycleTest->onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "ActivityLifecycleTest->onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "ActivityLifecycleTest->onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "ActivityLifecycleTest->onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "ActivityLifecycleTest->onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "ActivityLifecycleTest->onDestroy");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG, "ActivityLifecycleTest->onDetachedFromWindow");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "ActivityLifecycleTest->onConfigurationChanged");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.standard_btn:
                startActivity(new Intent(this, StandardActivity.class));
                break;
            case R.id.transparent_btn:
                startActivity(new Intent(this, TransparentActivity.class));
                break;
            case R.id.dialog_btn:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(this).setTitle("title").setMessage("message").show();
    }
}
