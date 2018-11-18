package com.android.mazhengyang.device_z.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mazhengyang on 18-11-12.
 */

public class ScreenTestView extends View {

    private static final String TAG = ScreenTestView.class.getSimpleName();

    public enum MODE {
        COLOR, DRAWEDGE, MULTITOUCH
    }

    //COLOR mode
    public enum ColorItem {
        BLACK, WHITE, RED, GREEN, BLUE
    }

    private Paint colorPaint;
    private MODE currentMode;
    private ColorItem colorItem;
    private ColorItem colorItems[] = ColorItem.values();

    //DRAWEDGE mode
    private Paint edgePaint;
    private Path edgePath;

    //MULTITOUCH mode
    private Paint touchPaint;
    private String toastInfo;
    private String touchInfo;
    private float radius;
    private List<Point> list = new ArrayList<>();

    public ScreenTestView(Context context) {
        super(context);
    }

    public ScreenTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMode(MODE mode) {
        switch (mode) {
            case COLOR:
                colorItem = ColorItem.BLACK;
                colorPaint = new Paint();
                break;
            case DRAWEDGE:
                edgePaint = new Paint();
                edgePaint.setStyle(Paint.Style.STROKE);
                edgePaint.setStrokeWidth(20);
                edgePaint.setColor(Color.RED);
                edgePath = new Path();
                break;
            case MULTITOUCH:
                touchPaint = new Paint();
//                touchPaint.setStyle(Paint.Style.STROKE);
                touchPaint.setTextSize(30);
                touchPaint.setColor(Color.WHITE);
                toastInfo = getContext().getString(R.string.screen_multi_touch_toast);
                touchInfo = getContext().getString(R.string.screen_multi_touch_info);
                radius = Utils.dip2px(getContext(), 30);
                break;
        }
        currentMode = mode;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (currentMode) {
            case COLOR:
                drawColor(canvas, colorPaint);
                break;
            case DRAWEDGE:
                drawEdge(canvas, edgePaint);
                break;
            case MULTITOUCH:
                drawMultiTouch(canvas, touchPaint);
                break;
        }
    }

    //////////////////////////draw color//////////////////////////
    public boolean nextColor() {
        if (colorItem.ordinal() < colorItems.length - 1) {
            colorItem = colorItems[colorItem.ordinal() + 1];
            invalidate();
            return true;
        } else {
            return false;
        }
    }

    private void drawColor(Canvas c, Paint paint) {
        int color = Color.RED;
        colorPaint.reset();
        switch (colorItem) {
            case BLACK:
                color = Color.BLACK;
                break;
            case WHITE:
                color = Color.WHITE;
                break;
            case RED:
                color = Color.RED;
                break;
            case GREEN:
                color = Color.GREEN;
                break;
            case BLUE:
                color = Color.BLUE;
                break;
            default:
                break;
        }

        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        colorPaint.setColor(color);
        c.drawRect(rect, paint);
    }

    //////////////////////////draw edge//////////////////////////
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (currentMode == MODE.MULTITOUCH) {
            int action = event.getAction();

            int masked = event.getActionMasked();
            int count = event.getPointerCount();
            int index = event.getActionIndex();

            Log.d(TAG, "onTouchEvent: count=" + count);
//            Log.d(TAG, "onTouchEvent: index=" + index);

            if (action == MotionEvent.ACTION_DOWN) {
                Log.d(TAG, "onTouchEvent: ACTION_DOWN");
                toastInfo = null;
            }

            for (int i = 0; i < count; i++) {

                float x = event.getX(i);
                float y = event.getY(i);

                Point point = null;
                if (list.size() > i) {
                    point = list.get(i);
                }
                if (point == null) {
                    Point p = new Point();
                    p.setX(x);
                    p.setY(y);
                    list.add(p);
                } else {
                    point.setX(x);
                    point.setY(y);
                }
            }

            if (list.size() > count) {
                for (int i = count; i < list.size(); i++) {
                    list.remove(i);
                }
            }

            if (action == MotionEvent.ACTION_UP) {
                Log.d(TAG, "onTouchEvent: ACTION_UP");
                list.clear();
            }

            invalidate();

            return true;
        } else if (currentMode == MODE.DRAWEDGE) {

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    break;
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 1;

    public void touch_start(float x, float y) {
//        edgePath.reset();
        edgePath.moveTo(x, y);
        mX = x;
        mY = y;
        invalidate();
    }

    public void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            edgePath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
        invalidate();
    }

    public void touch_up() {
        edgePath.lineTo(mX, mY);
        invalidate();
    }

    private void drawEdge(Canvas c, Paint paint) {
        c.drawColor(Color.WHITE);
        c.drawPath(edgePath, paint);
    }

    //////////////////////////multi touch//////////////////////////
    private void drawMultiTouch(Canvas c, Paint paint) {
        c.drawColor(Color.BLACK);

        if (toastInfo != null) {
            Rect rect = new Rect();
            touchPaint.getTextBounds(toastInfo, 0, toastInfo.length(), rect);
            //文字宽度
            int mTextWidth = rect.width();
            //让文字水平居中显示
            c.drawText(toastInfo, getWidth() / 2 - mTextWidth / 2, getHeight() / 2, touchPaint);
        }

        for (int i = 0; i < list.size(); i++) {
            Point point = list.get(i);

            if (point != null) {
                float offset = 100 + paint.getTextSize() * i;

                int color = Color.RED;
                switch (i % 5) {
                    case 0:
                        color = Color.RED;
                        break;
                    case 1:
                        color = Color.GREEN;
                        break;
                    case 2:
                        color = Color.BLUE;
                        break;
                    case 3:
                        color = Color.YELLOW;
                        break;
                    case 4:
                        color = Color.WHITE;
                        break;
                    default:
                        break;
                }

                float x = point.getX();
                float y = point.getY();
                paint.setColor(color);
                c.drawLine(x, 0, x, getHeight(), paint);
                c.drawLine(0, y, getWidth(), y, paint);
                c.drawCircle((int) x, (int) y, radius, paint);
                c.drawText(String.format(touchInfo,
                        i + 1, (int) x, (int) y), 0, offset, paint);
            }
        }
    }

    class Point {
        float x;
        float y;

        public Point() {

        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
