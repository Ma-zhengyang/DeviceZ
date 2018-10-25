package com.android.mazhengyang.device_z.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.android.mazhengyang.device_z.R;

/**
 * Created by mazhengyang on 18-10-24.
 */

public abstract class DeviceSensor implements SensorEventListener {

    private static final String TAG = DeviceSensor.class.getSimpleName();

    protected int SENSOR_TYPE;
    protected String sensorEvent;
    private Context context;
    private boolean isEnabled = false;
    private SensorManager sensorManager;

    public interface Callback {
        void update();
    }

    protected Callback callback;

    public abstract void sensorChanged(SensorEvent event);

    public DeviceSensor(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public String getName() {
        switch (SENSOR_TYPE) {
            case Sensor.TYPE_LIGHT:
                return context.getString(R.string.light_sensor);
            case Sensor.TYPE_PROXIMITY:
                return context.getString(R.string.proximity_sensor);
            case Sensor.TYPE_GRAVITY:
                return context.getString(R.string.gravity_sensor);
            case Sensor.TYPE_ACCELEROMETER:
                return context.getString(R.string.accelerometer_sensor);
            default:
                return context.getString(R.string.sensor_unknow);
        }
    }

    public String getEvent() {
        return sensorEvent;
    }

    protected String getString(int resId) {
        return context.getString(resId);
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void enable() {
        if (!isEnabled && sensorManager != null) {
            Log.d(TAG, "enable: " + getName());
            boolean enable = sensorManager.registerListener(this, sensorManager.getDefaultSensor(SENSOR_TYPE),
                    SensorManager.SENSOR_DELAY_NORMAL);
            isEnabled = enable;
        }
    }

    public void disable() {
        if (isEnabled && sensorManager != null) {
            Log.d(TAG, "disable: " + getName());
            sensorManager.unregisterListener(this);
            isEnabled = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorChanged(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
