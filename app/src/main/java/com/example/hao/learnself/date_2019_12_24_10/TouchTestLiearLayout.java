package com.example.hao.learnself.date_2019_12_24_10;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class TouchTestLiearLayout extends LinearLayout {
    public TouchTestLiearLayout(Context context) {
        super(context);
    }

    public TouchTestLiearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchTestLiearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return false;
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
