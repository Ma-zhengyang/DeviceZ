package com.android.mazhengyang.device_z;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.mazhengyang.device_z.adapter.MainAdapter;
import com.android.mazhengyang.device_z.fragments.BatteryFragment;
import com.android.mazhengyang.device_z.fragments.CPUFragment;
import com.android.mazhengyang.device_z.fragments.DeviceInfoFragment;
import com.android.mazhengyang.device_z.fragments.MainFragment;
import com.android.mazhengyang.device_z.fragments.SensorsFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private MainFragment mainFragment;
    private DeviceInfoFragment deviceInfoFragment;
    private CPUFragment cpuFragment;
    private SensorsFragment sensorsFragment;
    private BatteryFragment batteryFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainFragment fragment = new MainFragment();
        fragment.setListener(listener);
        showFragment(fragment);
        mainFragment = fragment;
    }

    private MainAdapter.OnItemClickListener listener = new MainAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int id) {

            Fragment fragment = null;
            switch (id) {
                case 0:
                    if (deviceInfoFragment == null) {
                        deviceInfoFragment = new DeviceInfoFragment();
                    }
                    fragment = deviceInfoFragment;
                    break;
                case 1:
                    if (cpuFragment == null) {
                        cpuFragment = new CPUFragment();
                    }
                    fragment = cpuFragment;
                    break;
                case 2:
                    if (sensorsFragment == null) {
                        sensorsFragment = new SensorsFragment();
                    }
                    fragment = sensorsFragment;
                    break;
                case 3:
                    if (batteryFragment == null) {
                        batteryFragment = new BatteryFragment();
                    }
                    fragment = batteryFragment;
                    break;
                default:
                    break;
            }

            showFragment(fragment);
        }
    };

    private void showFragment(Fragment fragment) {

        if (fragment != null && fragment != currentFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_content, fragment)
                    .show(fragment)
                    .commit();

            currentFragment = fragment;
        }
    }


    @Override
    public void onBackPressed() {
        if (currentFragment != mainFragment) {
            showFragment(mainFragment);
        } else {
            super.onBackPressed();
        }
    }

}
