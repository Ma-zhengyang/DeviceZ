package com.android.mazhengyang.device_z.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

/**
 * Created by mazhengyang on 18-10-25.
 */

public class GravitySensor extends DeviceSensor {

    private static final String TAG = GravitySensor.class.getSimpleName();

    public GravitySensor(Context context, Callback callback) {
        super(context, callback);
        SENSOR_TYPE = Sensor.TYPE_GRAVITY;
    }

    @Override
    public void sensorChanged(SensorEvent event) {
        if (callback != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            sensorEvent = String.format("x: %f\ny: %f\nz: %f,", x, y, z);
            Log.d(TAG, "sensorChanged: " + sensorEvent);
            if (callback != null) {
                callback.update();
            }
        }
    }

}
