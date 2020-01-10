package com.example.hao.learnself.date_2019_12_24_10;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.hao.learnself.R;

import static com.example.hao.learnself.Util.TAG;

public class ScrollTouchTestActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_touch);

        findViewById(R.id.touch_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.touch_1:
                Log.e(TAG, "onClickListener");
                break;
        }
    }
}
