package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.adapter.ParentAdapter;
import com.android.mazhengyang.device_z.bean.SensorBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-19.
 */

public class ToolSensorsFragment extends BaseFragment {

    private static final String TAG = ToolSensorsFragment.class.getSimpleName();

    @BindView(R.id.parent_recyclerview)
    RecyclerView parentRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_sensors, null);
        ButterKnife.bind(this, view);

        Context context = getContext();

        List<List<SensorBean>> parent_list = new ArrayList<>();
        parent_list.add(getChildList(context));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);
        ParentAdapter parentAdapter = new ParentAdapter(context, parent_list, R.anim.layout_slide_right_out, null);
        parentRecyclerView.setAdapter(parentAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    private List<SensorBean> getChildList(Context context) {

        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> all = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.d(TAG, "getChildList: " + all.size());

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor origntation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        Sensor barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        Sensor proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Sensor temperature = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        Sensor gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        Sensor rotation_vector = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        Sensor linear_acceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        List<SensorBean> child_list = new ArrayList<>();
        child_list.add(new SensorBean(R.string.accelerometer_sensor, getRes(accelerometer)));
        child_list.add(new SensorBean(R.string.gyroscope_sensor, getRes(gyroscope)));
        child_list.add(new SensorBean(R.string.light_sensor, getRes(light)));
        child_list.add(new SensorBean(R.string.magnetometer_sensor, getRes(magnetic)));
        child_list.add(new SensorBean(R.string.origntation_sensor, getRes(origntation)));
        child_list.add(new SensorBean(R.string.barometer_sensor, getRes(barometer)));
        child_list.add(new SensorBean(R.string.proximity_sensor, getRes(proximity)));
        child_list.add(new SensorBean(R.string.temperature_sensor, getRes(temperature)));
        child_list.add(new SensorBean(R.string.gravity_sensor, getRes(gravity)));
        child_list.add(new SensorBean(R.string.humidity_sensor, getRes(humidity)));
        child_list.add(new SensorBean(R.string.rotation_vector_sensor, getRes(rotation_vector)));
        child_list.add(new SensorBean(R.string.linear_acceleration_sensor, getRes(linear_acceleration)));

        return child_list;
    }

    private int getRes(Sensor sensor) {
        if (sensor != null) {
            return R.drawable.sensor_yes;
        } else {
            return R.drawable.sensor_no;
        }
    }
}
