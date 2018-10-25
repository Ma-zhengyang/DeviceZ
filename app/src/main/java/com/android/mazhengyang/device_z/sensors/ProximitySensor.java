package com.android.mazhengyang.device_z.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

import com.android.mazhengyang.device_z.R;

/**
 * Created by mazhengyang on 18-10-24.
 */

public class ProximitySensor extends DeviceSensor {

    private static final String TAG = ProximitySensor.class.getSimpleName();

    public ProximitySensor(Context context, Callback callback) {
        super(context, callback);
        SENSOR_TYPE = Sensor.TYPE_PROXIMITY;
    }

    @Override
    public void sensorChanged(SensorEvent event) {
        if (callback != null) {
            float[] value = event.values;
            float result = value[SensorManager.DATA_X];

            String status = "";
            if (result == 0) {
                status = getString(R.string.proximity_sensor_near);
            } else if (result >= 1.0f) {
                status = getString(R.string.proximity_sensor_far);
            }

            sensorEvent = String.format("%f,%s", result, status);
            Log.d(TAG, "sensorChanged: " + sensorEvent);
            if (callback != null) {
                callback.update();
            }
        }
    }

}
