package com.example.hao.learnself.date_2019_3_27;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.example.hao.learnself.R;

public class GaussianBlurActivity extends FragmentActivity {
    private ScrollView scrollView;
    private View titleLayout;
    private RenderScript renderScript;
    private ScriptIntrinsicBlur blurScript;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gassian_blur);
        scrollView = findViewById(R.id.scroll_view);
        titleLayout = findViewById(R.id.title_layout);
        renderScript = RenderScript.create(this);
        blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // 创建跟TitleBar大小一致的bitmap
                Bitmap titleBmp = Bitmap.createBitmap(titleLayout.getMeasuredWidth(), titleLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                titleBmp.eraseColor(Color.WHITE);
                // 创建画布
                Canvas canvas = new Canvas(titleBmp);
                // 获取scrollView在Y轴滚动的距离
                int scrollY = scrollView.getScrollY();
                // 保存画布
                canvas.save();
                // 向上平移画布
                canvas.translate(0, -scrollY);
                // 绘制
                scrollView.draw(canvas);
                // 恢复画布
                canvas.restore();
                titleLayout.setBackground(new BitmapDrawable(getResources(), getBlurImage(titleBmp)));
            }
        });
    }

    private Bitmap getBlurImage(Bitmap bmp) {
        Allocation in = Allocation.createFromBitmap(renderScript, bmp);
        Allocation out = Allocation.createTyped(renderScript, in.getType());
        blurScript.setInput(in);
        blurScript.setRadius(25);
        blurScript.forEach(out);
        out.copyTo(bmp);

        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(Color.parseColor("#AAFFFFFF"));
        return bmp;
    }
}
