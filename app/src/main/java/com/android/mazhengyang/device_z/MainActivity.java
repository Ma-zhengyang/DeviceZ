package com.android.mazhengyang.device_z;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.mazhengyang.device_z.adapter.MainAdapter;
import com.android.mazhengyang.device_z.fragments.BatteryFragment;
import com.android.mazhengyang.device_z.fragments.CPUFragment;
import com.android.mazhengyang.device_z.fragments.MainFragment;
import com.android.mazhengyang.device_z.fragments.MemoryFragment;
import com.android.mazhengyang.device_z.fragments.ScreenFragment;
import com.android.mazhengyang.device_z.fragments.SystemFragment;
import com.android.mazhengyang.device_z.fragments.ToolMainFragment;
import com.android.mazhengyang.device_z.fragments.tools.CompassFragment;
import com.android.mazhengyang.device_z.fragments.tools.SensorsFragment;
import com.android.mazhengyang.device_z.fragments.tools.TorchFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private MainFragment mainFragment;
    private SystemFragment deviceInfoFragment;
    private CPUFragment cpuFragment;
    private ScreenFragment screenFragment;
    private BatteryFragment batteryFragment;
    private MemoryFragment memoryFragment;
    private ToolMainFragment toolMainFragment;
    private TorchFragment torchFragment;
    private CompassFragment compassFragment;
    private SensorsFragment sensorsFragment;
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
                        deviceInfoFragment = new SystemFragment();
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
                    if (screenFragment == null) {
                        screenFragment = new ScreenFragment();
                    }
                    fragment = screenFragment;
                    break;
                case 3:
                    if (batteryFragment == null) {
                        batteryFragment = new BatteryFragment();
                    }
                    fragment = batteryFragment;
                    break;
                case 4:
                    if (memoryFragment == null) {
                        memoryFragment = new MemoryFragment();
                    }
                    fragment = memoryFragment;
                    break;
                case 5:
                    if (toolMainFragment == null) {
                        toolMainFragment = new ToolMainFragment();
                        toolMainFragment.setListener(tool_listener);
                    }
                    fragment = toolMainFragment;
                    break;
                default:
                    break;
            }

            showFragment(fragment);
        }
    };

    private MainAdapter.OnItemClickListener tool_listener = new MainAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int id) {

            Fragment fragment = null;
            switch (id) {
                case 0:
                    if (torchFragment == null) {
                        torchFragment = new TorchFragment();
                    }
                    fragment = torchFragment;
                    break;
                case 1:
                    if (compassFragment == null) {
                        compassFragment = new CompassFragment();
                    }
                    fragment = compassFragment;
                    break;
                case 2:
                    if (sensorsFragment == null) {
                        sensorsFragment = new SensorsFragment();
                    }
                    fragment = sensorsFragment;
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
            if (isTool()) {
                showFragment(toolMainFragment);
            } else {
                showFragment(mainFragment);
            }
        } else {
            super.onBackPressed();
        }
    }

    private boolean isTool() {
        return currentFragment == torchFragment
                || currentFragment == compassFragment
                || currentFragment == sensorsFragment;
    }

}
