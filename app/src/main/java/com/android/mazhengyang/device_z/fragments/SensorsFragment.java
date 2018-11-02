package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.adapter.ParentAdapter;
import com.android.mazhengyang.device_z.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-19.
 */

public class SensorsFragment extends Fragment {

    private final String TAG = SensorsFragment.class.getSimpleName();

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

        List<List<BaseBean>> parent_list = new ArrayList<>();

        parent_list.add(getChildList(context));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);

        ParentAdapter parentAdapter = new ParentAdapter(context, parent_list);
        parentRecyclerView.setAdapter(parentAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

    private List<BaseBean> getChildList(Context context) {

        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> all = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.d(TAG, "recycler: " + all.size());

        Sensor light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Sensor gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        List<BaseBean> child_list = new ArrayList<>();
        child_list.add(new BaseBean(context.getString(R.string.light_sensor), getInfo(context, light)));
        child_list.add(new BaseBean(context.getString(R.string.proximity_sensor), getInfo(context, proximity)));
        child_list.add(new BaseBean(context.getString(R.string.gravity_sensor), getInfo(context, gravity)));
        child_list.add(new BaseBean(context.getString(R.string.accelerometer_sensor), getInfo(context, accelerometer)));
        child_list.add(new BaseBean(context.getString(R.string.gyroscope_sensor), getInfo(context, gyroscope)));
        child_list.add(new BaseBean(context.getString(R.string.magnetometer_sensor), getInfo(context, magnetic)));

        return child_list;
    }

    private String getInfo(Context context, Sensor sensor) {
        if (sensor != null) {
            return context.getString(R.string.has_sensor);
        } else {
            return context.getString(R.string.no_sensor);
        }
    }
}
