package com.android.mazhengyang.device_z;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by mazhengyang on 18-11-16.
 */

public class RotateAnim extends Animation {
    private int centerX;
    private int centerY;
    private Camera camera;

    /**
     * 获取坐标，定义动画时间
     *
     * @param width
     * @param height
     * @param parentWidth
     * @param parentHeight
     */
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        camera = new Camera();
        //获得旋转点坐标
        centerX = 0;
        centerY = width / 2;
        //动画执行时间 自行定义
        setDuration(500);
        setInterpolator(new AccelerateInterpolator());
    }

    /**
     * 旋转的角度设置
     *
     * @param interpolatedTime
     * @param t
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();
        camera.save();
        //中心是Y轴旋转，这里可以自行设置X轴 Y轴 Z轴
        camera.rotateY(30 * interpolatedTime);
        //把摄像头加在变换矩阵上
        camera.getMatrix(matrix);
        //设置翻转点
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        camera.restore();
    }
}
