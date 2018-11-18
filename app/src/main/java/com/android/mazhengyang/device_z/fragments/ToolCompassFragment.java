package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.widget.CompassView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-6.
 */

public class ToolCompassFragment extends BaseFragment implements SensorEventListener {

    private static final String TAG = ToolCompassFragment.class.getSimpleName();

    private SensorManager mSensorManager;

    @BindView(R.id.compass_view)
    CompassView compassView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_compass, null);
        ButterKnife.bind(this, view);

        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            mSensorManager.registerListener(this,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                    SensorManager.SENSOR_DELAY_GAME);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float value = event.values[0];
        compassView.setVal(value);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

}
