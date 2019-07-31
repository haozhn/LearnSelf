package com.example.hao.learnself.date_2019_7_26;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    private float currentX;
    private float currentY;
    private Path path;
    private int unselectedPointColor;
    private int selectedPointColor;
    private int pathColor;
    private int circleColor;

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
        mRadius = Util.dp2px(getContext(), 30);
        mCells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCells[i][j] = new Cell(0, 0);
            }
        }
        selectCells = new LinkedList<>();
        path = new Path();
        unselectedPointColor = Color.parseColor("#d4e0eb");
        selectedPointColor = Color.parseColor("#5a9ade");
        pathColor = Color.parseColor("#5a9ade");
        circleColor = Color.parseColor("#e9f1fa");
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

    private void setPointPaint(boolean selected) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(Util.dp2px(getContext(), 10));
        mPaint.setColor(selected ? selectedPointColor : unselectedPointColor);
    }

    private void setPathPaint() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Util.dp2px(getContext(), 2));
        mPaint.setColor(pathColor);
    }

    private void setCirclePaint() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(circleColor);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        setPointPaint(false);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                canvas.drawPoint(mCells[i][j].x, mCells[i][j].y, mPaint);
            }
        }

        for (Cell cell : selectCells) {
            setCirclePaint();
            canvas.drawCircle(cell.x, cell.y, mRadius, mPaint);
            setPointPaint(true);
            canvas.drawPoint(cell.x, cell.y, mPaint);
        }

        if (selectCells.size() > 1) {
            setPathPaint();
            canvas.drawPath(path, mPaint);
        }

        if (selectCells.size() > 0) {
            setPathPaint();
            canvas.drawLine(selectCells.get(selectCells.size() - 1).x, selectCells.get(selectCells.size() - 1).y, currentX, currentY, mPaint);
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
                handleActionUp(event);
                return true;
            case MotionEvent.ACTION_CANCEL:
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void clear() {
        selectCells.clear();
        path.rewind();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mCells[i][j].selected = false;
            }
        }
        invalidate();
    }

    private void handleActionDown(MotionEvent event) {
        clear();
        currentX = event.getX();
        currentY = event.getY();
        final Cell cell = getHitCell(currentX,  currentY);
        if (cell != null && !cell.selected) {
            if (selectCells.size() <= 0) {
                path.moveTo(cell.x, cell.y);
            } else {
                path.lineTo(cell.x, cell.y);
            }
            cell.selected = true;
            selectCells.add(cell);
            invalidate();
        }
    }

    private void handleActionMove(MotionEvent event) {
        currentX = event.getX();
        currentY = event.getY();
        final Cell cell = getHitCell(currentX, currentY);
        if (cell != null && !cell.selected) {
            if (selectCells.size() <= 0) {
                path.moveTo(cell.x, cell.y);
            } else {
                path.lineTo(cell.x, cell.y);
            }
            cell.selected = true;
            selectCells.add(cell);
        }
        invalidate();
    }

    private void handleActionUp(MotionEvent event) {

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
