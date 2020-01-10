package com.example.hao.learnself.date_19_12_27;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.hao.learnself.R;

import static com.example.hao.learnself.Util.TAG;

public class HandlerActivity extends Activity implements View.OnClickListener {
    private HandlerThread thread;
    private Handler workHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        findViewById(R.id.handler_thread).setOnClickListener(this);

        thread = new HandlerThread("handler_thread");
        thread.start();
        workHandler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e(TAG, "what=" + msg.what + " isMain=" + (getLooper() == Looper.getMainLooper()));
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.handler_thread:
                workHandler.sendEmptyMessage(10);
                break;
        }
    }
}
