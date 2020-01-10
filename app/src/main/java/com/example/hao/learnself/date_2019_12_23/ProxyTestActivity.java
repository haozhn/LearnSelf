package com.example.hao.learnself.date_2019_12_23;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.example.hao.learnself.R;

public class ProxyTestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_test);

        ((TextView) findViewById(R.id.proxy_text)).setText(getIntent().getStringExtra("proxy_key"));
    }
}
