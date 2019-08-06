package com.example.hao.learnself.date_2019_7_26;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.hao.learnself.R;
import com.example.hao.learnself.Util;

import java.util.LinkedList;

public class GestureLockView extends View {
    private Paint paint;
    private float radius;
    private Cell[][] cells;
    private LinkedList<Cell> selectCells;
    private float currentX;
    private float currentY;
    private Path path;
    private int pointColor;
    private int pathColor;
    private int errorPathColor;
    private int errorCircleColor;
    private int circleColor;
    private boolean error;
    private boolean done;
    private GestureLockListener listener;

    public GestureLockView(Context context) {
        this(context, null);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GestureLockView);
        pointColor = a.getColor(R.styleable.GestureLockView_pointColor, ContextCompat.getColor(context, R.color.pointColor));
        pathColor = a.getColor(R.styleable.GestureLockView_pathColor, ContextCompat.getColor(context, R.color.pathColor));
        circleColor = a.getColor(R.styleable.GestureLockView_circleColor, ContextCompat.getColor(context, R.color.circleColor));
        errorPathColor = a.getColor(R.styleable.GestureLockView_errorPathColor, ContextCompat.getColor(context, R.color.errorPathColor));
        errorCircleColor = a.getColor(R.styleable.GestureLockView_errorCircleColor, ContextCompat.getColor(context, R.color.errorCircleColor));
        radius = a.getDimension(R.styleable.GestureLockView_radius, Util.dp2px(context, 30));
        a.recycle();
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cells = new Cell[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell(0, 0);
                cells[i][j].pos = 3 * i + j + 1;
            }
        }
        selectCells = new LinkedList<>();
        path = new Path();
        done = false;
        error = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthSize = getMeasuredWidth();
        final int heightSize = getMeasuredHeight();
        final int size = Math.min(widthSize, heightSize);
        setMeasuredDimension(size, size);
        radius = Math.min(size / 6f, radius);

        float[] n = {radius, size / 2f, size - radius};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].x = n[j];
                cells[i][j].y = n[i];
            }
        }
    }

    private void setPointPaint(boolean selected) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(Util.dp2px(getContext(), 10));
        paint.setColor(selected ? (error ? errorPathColor : pathColor) : pointColor);
    }

    private void setPathPaint() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Util.dp2px(getContext(), 2));
        paint.setColor(error ? errorPathColor : pathColor);
    }

    private void setCirclePaint() {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(error ? errorCircleColor : circleColor);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        setPointPaint(false);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                canvas.drawPoint(cells[i][j].x, cells[i][j].y, paint);
            }
        }

        for (Cell cell : selectCells) {
            setCirclePaint();
            canvas.drawCircle(cell.x, cell.y, radius, paint);
            setPointPaint(true);
            canvas.drawPoint(cell.x, cell.y, paint);
        }

        if (selectCells.size() > 1) {
            setPathPaint();
            canvas.drawPath(path, paint);
        }

        if (selectCells.size() > 0 && !done) {
            setPathPaint();
            canvas.drawLine(selectCells.get(selectCells.size() - 1).x, selectCells.get(selectCells.size() - 1).y, currentX, currentY, paint);
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
                clear();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void clear() {
        done = false;
        error = false;
        selectCells.clear();
        path.rewind();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j].selected = false;
            }
        }
        invalidate();
    }

    private void checkAndAdd(float x, float y) {
        Cell cell = null;
        boolean find = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Math.pow(x - cells[i][j].x, 2) + Math.pow(y - cells[i][j].y, 2) < radius * radius) {
                    cell = cells[i][j];
                    find = true;
                    break;
                }
            }
            if (find) {
                break;
            }
        }
        if (find && !cell.selected) {
            cell.selected = true;
            if (selectCells.size() <= 0) {
                path.moveTo(cell.x, cell.y);
            } else {
                path.lineTo(cell.x, cell.y);
            }
            selectCells.add(cell);
            if (listener != null) {
                listener.onCellAdd(getPas());
            }
        }
    }

    private void handleActionDown(MotionEvent event) {
        clear();
        currentX = event.getX();
        currentY = event.getY();
        if (listener != null) {
            listener.onStart();
        }
        checkAndAdd(currentX, currentY);
    }

    private void handleActionMove(MotionEvent event) {
        currentX = event.getX();
        currentY = event.getY();
        checkAndAdd(currentX, currentY);
        invalidate();
    }

    private void handleActionUp(MotionEvent event) {
        done = true;
        if (listener != null && selectCells.size() > 0) {
            int[] pas = getPas();
            boolean success = listener.onFinish(pas);
            if (success) {
                listener.onSuccess(pas);
            } else {
                error = true;
                listener.onError(pas);
            }
        }
        invalidate();
    }

    private int[] getPas() {
        if (selectCells != null && selectCells.size() > 0) {
            int[] result = new int[selectCells.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = selectCells.get(i).pos;
            }
            return result;
        }
        return null;
    }

    private class Cell {
        float x;
        float y;
        int pos;
        boolean selected;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
            selected = false;
        }
    }

    public void setGestureLockListener(GestureLockListener l) {
        this.listener = l;
    }

    public interface GestureLockListener {
        void onStart();

        void onCellAdd(int[] pas);

        boolean onFinish(int[] pas);

        void onSuccess(int[] pas);

        void onError(int[] pas);
    }
}
