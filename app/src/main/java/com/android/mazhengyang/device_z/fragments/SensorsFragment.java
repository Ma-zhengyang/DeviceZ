package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
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
import com.android.mazhengyang.device_z.adapter.SensorAdapter;
import com.android.mazhengyang.device_z.sensors.AccelerometerSensor;
import com.android.mazhengyang.device_z.sensors.DeviceSensor;
import com.android.mazhengyang.device_z.sensors.GravitySensor;
import com.android.mazhengyang.device_z.sensors.LightSensor;
import com.android.mazhengyang.device_z.sensors.ProximitySensor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-19.
 */

public class SensorsFragment extends Fragment implements DeviceSensor.Callback {

    private final String TAG = SensorsFragment.class.getSimpleName();

    private SensorAdapter sensorAdapter;

    @BindView(R.id.sensors_recyclerview)
    RecyclerView recyclerView;

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

        //必须设置layout manager，否则adapter的onCreateViewHolder和onBindViewHolder不会调用
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<DeviceSensor> list = new ArrayList<>();
        list.add(new AccelerometerSensor(context, this));
        list.add(new GravitySensor(context, this));
        list.add(new LightSensor(context, this));
        list.add(new ProximitySensor(context, this));

        sensorAdapter = new SensorAdapter(context, list);
        recyclerView.setAdapter(sensorAdapter);

        //放在SensorAdapter动画结束后执行，update()中多次notifyDataSetChanged动画会无效 ？
        //   sensorAdapter.enableAll();

//        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(context,
//                R.anim.layout_scale_out);
//        recyclerView.setLayoutAnimation(animationController);
//        sensorAdapter.notifyDataSetChanged();
//        recyclerView.scheduleLayoutAnimation();

        return view;
    }

    @Override
    public void update() {
        if (sensorAdapter != null) {
            sensorAdapter.notifyDataSetChanged();
        }
    }

    public void disableAll() {
        if (sensorAdapter != null) {
            sensorAdapter.disableAll();
        }
    }
}
