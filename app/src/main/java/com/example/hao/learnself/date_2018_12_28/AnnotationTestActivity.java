package com.example.hao.learnself.date_2018_12_28;

import android.view.View;
import android.widget.TextView;

import com.example.annotation.BindView;
import com.example.hao.learnself.R;
import com.example.hao.learnself.date_2018_12_28.annotation.BindViewRuntime;
import com.example.hao.learnself.date_2018_12_28.annotation.OnClickRuntime;

public class AnnotationTestActivity extends BaseActivity {
    @BindViewRuntime(R.id.runtime_add_btn)
    private TextView runtimeAddBtn;
    @BindViewRuntime(R.id.runtime_sum_tv)
    private TextView runtimeSumTv;
    @BindView(R.id.compile_add_btn)
    TextView compileAddBtn;
    @BindView(R.id.compile_sum_tv)
    TextView compileSumTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annotation_test;
    }

    @Override
    protected void initView() {
        Injection.inject(this);
//        runtimeAddBtn.setOnClickListener(v -> {
//            String text = runtimeSumTv.getText().toString();
//            runtimeSumTv.setText(String.valueOf(Integer.parseInt(text) + 1));
//        });
        compileAddBtn.setOnClickListener(v -> {
            String text = compileSumTv.getText().toString();
            compileSumTv.setText(String.valueOf(Integer.parseInt(text) + 1));
        });
    }

    @OnClickRuntime(R.id.runtime_add_btn)
    void runTimeAdd() {
        String text = runtimeSumTv.getText().toString();
        runtimeSumTv.setText(String.valueOf(Integer.parseInt(text) + 1));
    }
}
