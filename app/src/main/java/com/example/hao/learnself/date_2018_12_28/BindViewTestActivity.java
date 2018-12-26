package com.example.hao.learnself.date_2018_12_28;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.annotation.BindView;
import com.example.hao.learnself.R;

public class BindViewTestActivity extends FragmentActivity {
    @BindView(R.id.add_btn)
    Button addBtn;
    @BindView(R.id.sum_tv)
    TextView sumTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindview);
    }
}
