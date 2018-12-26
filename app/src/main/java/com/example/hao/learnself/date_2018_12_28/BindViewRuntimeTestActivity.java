package com.example.hao.learnself.date_2018_12_28;

import android.widget.Button;
import android.widget.TextView;

import com.example.hao.learnself.R;
import com.example.hao.learnself.date_2018_12_28.annotation.BindViewRuntime;

public class BindViewRuntimeTestActivity extends BaseActivity {
    @BindViewRuntime(R.id.add_btn)
    private Button addBtn;
    @BindViewRuntime(R.id.sum_tv)
    private TextView sumTv;

    private int sum = 0;

    @Override
    protected void initView() {
        addBtn.setOnClickListener(v -> sumTv.setText(String.valueOf(++sum)));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bindview;
    }
}
