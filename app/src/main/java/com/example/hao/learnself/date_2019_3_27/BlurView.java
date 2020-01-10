package com.example.hao.learnself.date_2019_3_27;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.renderscript.RenderScript;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.example.hao.learnself.R;


public class BlurView extends FrameLayout {

    private Bitmap blurBitmap;

    private ViewGroup rootView;

    @ColorInt
    private int overlayColor;

    private float offset;

    private int radius;

    private int scale;

    private RenderScript renderScript;

    public BlurView(@NonNull Context context) {
        this(context, null);
    }

    public BlurView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        renderScript = RenderScript.create(context);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.BlurView, defStyleAttr, 0);
        overlayColor = ta.getColor(R.styleable.BlurView_overlayColor, Color.TRANSPARENT);
        offset = ta.getDimension(R.styleable.BlurView_offset, 0f);
        radius = ta.getInt(R.styleable.BlurView_radius, 0);
        scale = ta.getInt(R.styleable.BlurView_scale, 0);
        ta.recycle();
    }

    public void setOverlayColor(@ColorInt int overlayColor) {
        this.overlayColor = overlayColor;
    }

    public void setRootView(final ViewGroup rootView) {
        this.rootView = rootView;
        this.rootView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                updateBlur();
            }
        });
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (blurBitmap == null || blurBitmap.getWidth() != width || blurBitmap.getHeight() != height) {
            blurBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
    }

    private void updateBlur() {

    }
}
