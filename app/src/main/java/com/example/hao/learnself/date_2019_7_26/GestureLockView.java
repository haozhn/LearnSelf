package com.example.hao.learnself.date_2019_7_26;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.hao.learnself.Util;

public class GestureLockView extends View {
    private Paint mPaint;
    private int mSize;
    private int mRadius;
    private Cell[][] mCells;

    public GestureLockView(Context context) {
        this(context, null);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(Util.dp2px(getContext(), 10));
        mRadius = Util.dp2px(getContext(), 50);
        mCells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCells[i][j] = new Cell(0, 0);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSize == 0 && heightSize == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            final int minSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
            setMeasuredDimension(minSize, minSize);
            return;
        }
        if (widthSize == 0 || heightSize == 0) {
            mSize = Math.max(widthSize, heightSize);
        } else {
            mSize = Math.min(widthSize, heightSize);
        }
        final int newMeasureSpec = MeasureSpec.makeMeasureSpec(mSize, MeasureSpec.EXACTLY);
        super.onMeasure(newMeasureSpec, newMeasureSpec);

        int[] n = {mRadius, mSize / 2, mSize - mRadius};
        for(int i = 0;i< 3;i++) {
            for (int j = 0;j<3;j++) {
                mCells[i][j].x = n[j];
                mCells[i][j].y = n[i];
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                canvas.drawPoint(mCells[i][j].x, mCells[i][j].y, mPaint);
            }
        }
    }

    private class Cell {
        int x;
        int y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
