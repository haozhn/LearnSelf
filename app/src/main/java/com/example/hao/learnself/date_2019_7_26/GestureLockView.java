package com.example.hao.learnself.date_2019_7_26;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.hao.learnself.Util;

import java.util.LinkedList;

public class GestureLockView extends View {
    private Paint mPaint;
    private int mRadius;
    private Cell[][] mCells;
    private LinkedList<Cell> selectCells;

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
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(Util.dp2px(getContext(), 10));
        mRadius = Util.dp2px(getContext(), 30);
        mCells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCells[i][j] = new Cell(0, 0);
            }
        }
        selectCells = new LinkedList<>();
    }

    private void setPointPaint() {
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(Util.dp2px(getContext(), 10));
    }

    private void setCirclePaint() {
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Util.dp2px(getContext(), 1));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthSize = getMeasuredWidth();
        final int heightSize = getMeasuredHeight();
        final int size = Math.min(widthSize, heightSize);
        setMeasuredDimension(size, size);
        mRadius = Math.min(size / 6, mRadius);

        int[] n = {mRadius, size / 2, size - mRadius};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCells[i][j].x = n[j];
                mCells[i][j].y = n[i];
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        setPointPaint();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                canvas.drawPoint(mCells[i][j].x, mCells[i][j].y, mPaint);
            }
        }
        setCirclePaint();
        for (Cell cell : selectCells) {
            canvas.drawCircle(cell.x, cell.y, mRadius, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(event);
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            case MotionEvent.ACTION_CANCEL:
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void reset() {
        selectCells.clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCells[i][j].selected = false;
            }
        }
        invalidate();
    }

    private void handleActionDown(MotionEvent event) {
        reset();
        final float x = event.getX();
        final float y = event.getY();
        final Cell cell = getHitCell(x, y);
        if (cell != null) {
            cell.selected = true;
            selectCells.add(cell);
            invalidate();
        }
    }

    private void handleActionMove(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        final Cell cell = getHitCell(x, y);
        if (cell != null) {
            cell.selected = true;
            selectCells.add(cell);
            invalidate();
        }
    }

    private Cell getHitCell(float x, float y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Math.pow(x - mCells[i][j].x, 2) + Math.pow(y - mCells[i][j].y, 2) < mRadius * mRadius) {
                    return mCells[i][j];
                }
            }
        }
        return null;
    }

    private class Cell {
        int x;
        int y;
        boolean selected;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
            selected = false;
        }
    }
}
