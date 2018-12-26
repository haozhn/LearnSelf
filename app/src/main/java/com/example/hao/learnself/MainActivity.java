package com.example.hao.learnself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.hao.learnself.date_2018_12_28.BindViewRuntimeTestActivity;
import com.example.hao.learnself.date_2018_12_28.BindViewTestActivity;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.annotation_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.annotation_btn:
//                startActivity(new Intent(this, BindViewRuntimeTestActivity.class));
                startActivity(new Intent(this, BindViewTestActivity.class));
                break;
        }
    }
}
