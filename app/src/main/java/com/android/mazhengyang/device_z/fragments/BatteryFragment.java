package com.android.mazhengyang.device_z.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
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
import com.android.mazhengyang.device_z.bean.TitleInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-22.
 */

public class BatteryFragment extends Fragment {

    private static final String TAG = BatteryFragment.class.getSimpleName();

    private IntentFilter intentFilter;
    private BatteryReceiver batteryReceiver;

    List<List<TitleInfoBean>> parent_list = new ArrayList<>();

    private ParentAdapter parentAdapter;

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

        View view = inflater.inflate(R.layout.fragment_battery, null);
        ButterKnife.bind(this, view);

        Context context = getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);

        //执行过一遍，onDestroyView后，parent_list等属性其实已经不是null，会导致adapter中短时间
        //多次onBindViewHolder，导致动画效果失效，主要还是在onReceive中传入，这里直接传空
        parentAdapter = new ParentAdapter(context, null/*parent_list*/);
        parentRecyclerView.setAdapter(parentAdapter);

        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryReceiver = new BatteryReceiver();

        context.registerReceiver(batteryReceiver, intentFilter);

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
        getContext().unregisterReceiver(batteryReceiver);
    }

    public class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");
            List<TitleInfoBean> child_list1 = new ArrayList<>();
            child_list1.add(new TitleInfoBean(context.getString(R.string.battery_status), getStatus(context, intent)));
            child_list1.add(new TitleInfoBean(context.getString(R.string.battery_charge), getCharge(context, intent)));
            child_list1.add(new TitleInfoBean(context.getString(R.string.battery_health), getHealth(context, intent)));

            List<TitleInfoBean> child_list2 = new ArrayList<>();
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0); //当前电量
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10;//温度
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);//电压
            String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);//电池技术

            child_list2.add(new TitleInfoBean(context.getString(R.string.battery_capacity), String.valueOf(level).concat("%")));
            child_list2.add(new TitleInfoBean(context.getString(R.string.battery_voltage), String.valueOf(voltage).concat("mv")));
            child_list2.add(new TitleInfoBean(context.getString(R.string.battery_temperature), String.valueOf(temperature).concat("℃")));
            child_list2.add(new TitleInfoBean(context.getString(R.string.battery_echnology), String.valueOf(technology)));

            parent_list.clear();
            parent_list.add(child_list1);
            parent_list.add(child_list2);
            parentAdapter.update(parent_list);
        }
    }

    //充电状态
    private String getStatus(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                return context.getString(R.string.battery_status_charging);
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return context.getString(R.string.battery_status_discharging);
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return context.getString(R.string.battery_status_not_charging);
            case BatteryManager.BATTERY_STATUS_FULL:
                return context.getString(R.string.battery_status_full);
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
            default:
                return context.getString(R.string.battery_status_unknown);
        }
    }

    //充电方式
    private String getCharge(Context context, Intent intent) {
        int charge = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        switch (charge) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                return context.getString(R.string.battery_charging_ac);
            case BatteryManager.BATTERY_PLUGGED_USB:
                return context.getString(R.string.battery_charging_usb);
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                return context.getString(R.string.battery_charging_wireless);
            default:
                return context.getString(R.string.battery_charging_unknow);
        }
    }

    //健康状态
    private String getHealth(Context context, Intent intent) {
        int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return context.getString(R.string.battery_health_good);
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return context.getString(R.string.battery_health_over_heat);
            case BatteryManager.BATTERY_HEALTH_DEAD:
                return context.getString(R.string.battery_health_dead);
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return context.getString(R.string.battery_health_over_voltage);
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return context.getString(R.string.battery_health_unspecified_failure);
            case BatteryManager.BATTERY_HEALTH_COLD:
                return context.getString(R.string.battery_health_cold);
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
            default:
                return context.getString(R.string.battery_health_unknow);
        }
    }
}
