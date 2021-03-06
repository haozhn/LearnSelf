package com.example.hao.learnself.date_2019_12_24_10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hao.learnself.R;

public class TouchTestActivity extends Activity implements View.OnClickListener {
    private TextView drawBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_test);
        drawBtn = findViewById(R.id.redraw_btn);
        drawBtn.setOnClickListener(this);
        findViewById(R.id.touch_1).setOnClickListener(this);
        findViewById(R.id.touch_2).setOnClickListener(this);
        findViewById(R.id.scroll_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.touch_1:
                Log.e("haozhinan", "onClickListener");
                break;
            case R.id.scroll_btn:
                startActivity(new Intent(this, ScrollTouchTestActivity.class));
                break;
            case R.id.redraw_btn:
                drawBtn.requestLayout();
                break;
        }
    }
}
