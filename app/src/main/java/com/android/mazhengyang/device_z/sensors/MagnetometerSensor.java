package com.android.mazhengyang.device_z.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

/**
 * Created by mazhengyang on 18-10-25.
 */

public class MagnetometerSensor extends DeviceSensor {

    private static final String TAG = MagnetometerSensor.class.getSimpleName();

    public MagnetometerSensor(Context context, Callback callback) {
        super(context, callback);
        SENSOR_TYPE = Sensor.TYPE_MAGNETIC_FIELD;
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
