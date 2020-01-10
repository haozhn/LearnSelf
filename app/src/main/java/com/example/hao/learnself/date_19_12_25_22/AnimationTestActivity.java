package com.example.hao.learnself.date_19_12_25_22;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.hao.learnself.R;

public class AnimationTestActivity extends Activity implements View.OnClickListener {
    private TextView translateBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);

        translateBtn = findViewById(R.id.translate_btn);
        translateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translate_btn:
                translateBtn.startAnimation(getTranslateAnimation("RELATIVE_TO_PARENT"));
                break;
        }
    }

    private Animation getTranslateAnimation(String type) {
        Animation animation = null;
        if ("ABSOLUTE".equals(type)) {
            animation = new TranslateAnimation(Animation.ABSOLUTE, 0,
                    Animation.ABSOLUTE, 400, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 100);
        } else if ("RELATIVE_TO_SELF".equals(type)) {
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 2, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
        } else if ("RELATIVE_TO_PARENT".equals(type)) {
            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 0.6f, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.1f);
        }
        if (animation != null) {
            animation.setDuration(500);
            animation.setFillAfter(true);
        }
        return animation;
    }
}
