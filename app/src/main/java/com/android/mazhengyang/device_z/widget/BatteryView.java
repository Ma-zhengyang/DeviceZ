package com.android.mazhengyang.device_z.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by mazhengyang on 18-11-13.
 */

public class BatteryView extends View {

    private static final String TAG = BatteryView.class.getSimpleName();

    private Paint paint;
    private RectF backgroundRectF = new RectF();
    private RectF capacityRectF = new RectF();
    private RectF headRectF = new RectF();

    private int level = 0;

    public BatteryView(Context context) {
        super(context);
        init();
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setLevel(int level) {
        this.level = level;
        invalidate();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int batteryWidth = width / 2;
        int batteryHeight = height * 4 / 5;

        //background
        int backgroundLeft = (width - batteryWidth) / 2;
        int backgroundTop = (height - batteryHeight) / 2;
        int backgroundRight = backgroundLeft + batteryWidth;
        int backgroundBottom = backgroundTop + batteryHeight;

        backgroundRectF.set(backgroundLeft, backgroundTop, backgroundRight, backgroundBottom);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(backgroundRectF, 10, 10, paint);

        //head
        int headWidth = batteryWidth / 5;
        int headHeight = batteryHeight / 30;

        int headLeft = backgroundLeft + (batteryWidth - headWidth) / 2;
        int headTop = backgroundTop - headHeight;

        int headRight = headLeft + headWidth;
        int headBottom = headTop + headHeight;
        headRectF.set(headLeft, headTop, headRight, headBottom);
        canvas.drawRoundRect(headRectF, 5, 5, paint);

        //level
        int capacityLeft = backgroundLeft + 5;
        float percent = level * 1.0f / 100;
        float capacityTop = backgroundTop + (1.0f - percent) * (batteryHeight - 10) + 5; //max height: batteryHeight - 10
        int capacityRight = backgroundRight - 5;
        int capacityBottom = backgroundBottom - 5;
        capacityRectF.set(capacityLeft, capacityTop, capacityRight, capacityBottom);
        if (percent < 0.2) {
            paint.setColor(Color.RED);
        } else if (percent >= 0.2 && percent < 0.6) {
            paint.setColor(Color.YELLOW);
        } else {
            paint.setColor(Color.GREEN);
        }

        canvas.drawRoundRect(capacityRectF, 10, 10, paint);

    }

}
