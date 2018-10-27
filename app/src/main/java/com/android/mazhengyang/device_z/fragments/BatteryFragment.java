package com.android.mazhengyang.device_z.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-22.
 */

public class BatteryFragment extends Fragment {

    private final String TAG = BatteryFragment.class.getSimpleName();

    private IntentFilter filter;
    private BatteryReceiver receiver;
    private boolean isEnabled = false;

    @BindView(R.id.tv_battery_info)
    TextView batteryInfo;

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

        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        receiver = new BatteryReceiver();

        getContext().registerReceiver(receiver, filter);
        isEnabled = true;

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
        if (isEnabled) {
            getContext().unregisterReceiver(receiver);
            isEnabled = false;
        }
    }

    public class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "onReceive: ");

            //充电状态
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String statusStr;
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusStr = context.getString(R.string.battery_status_charging);
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusStr = context.getString(R.string.battery_status_discharging);
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    statusStr = context.getString(R.string.battery_status_not_charging);
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    statusStr = context.getString(R.string.battery_status_full);
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                default:
                    statusStr = context.getString(R.string.battery_status_unknown);
                    break;
            }

            int charge = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            String chargeStr;
            switch (charge) {
                case BatteryManager.BATTERY_PLUGGED_AC:
                    chargeStr = context.getString(R.string.battery_charging_ac);
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    chargeStr = context.getString(R.string.battery_charging_usb);
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    chargeStr = context.getString(R.string.battery_charging_wireless);
                    break;
                default:
                    chargeStr = context.getString(R.string.battery_charging_unknow);
                    break;
            }

            float temperature = (float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10;//温度
            float voltage = (float) intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);//电压
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0); //当前电量

            //电池的健康状态
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
            String healthStr;
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthStr = context.getString(R.string.battery_health_good);
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthStr = context.getString(R.string.battery_health_over_heat);
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    healthStr = context.getString(R.string.battery_health_dead);
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthStr = context.getString(R.string.battery_health_over_voltage);
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthStr = context.getString(R.string.battery_health_unspecified_failure);
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD:
                    healthStr = context.getString(R.string.battery_health_cold);
                    break;
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                default:
                    healthStr = context.getString(R.string.battery_health_unknow);
                    break;
            }

            String result = String.format(context.getString(R.string.battery_message),
                    statusStr, chargeStr, temperature, voltage, level, healthStr);

            batteryInfo.setText(result);
        }
    }

}
