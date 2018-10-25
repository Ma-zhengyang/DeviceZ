package com.android.mazhengyang.device_z.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

import com.android.mazhengyang.device_z.R;

/**
 * Created by mazhengyang on 18-10-24.
 */

public class LightSensor extends DeviceSensor {

    private static final String TAG = LightSensor.class.getSimpleName();

    private static final float HIGH = 20.0f;
    private static final float LOW = 10.0f;

    public LightSensor(Context context, Callback callback) {
        super(context, callback);
        SENSOR_TYPE = Sensor.TYPE_LIGHT;
    }

    @Override
    public void sensorChanged(SensorEvent event) {
        if (callback != null) {
            float value = event.values[0];

            String status = "";
            if (value < LOW) {
                status = getString(R.string.light_sensor_dark);
            } else if (value > HIGH) {
                status = getString(R.string.light_sensor_bright);
            }

            sensorEvent = String.format("%f,%s", value, status);
            Log.d(TAG, "sensorChanged: " + sensorEvent);
            if (callback != null) {
                callback.update();
            }
        }
    }

}
