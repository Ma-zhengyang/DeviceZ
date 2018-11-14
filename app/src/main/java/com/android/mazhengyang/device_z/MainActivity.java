package com.android.mazhengyang.device_z;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.mazhengyang.device_z.callback.ItemClickCallback;
import com.android.mazhengyang.device_z.fragments.AboutFragment;
import com.android.mazhengyang.device_z.fragments.BaseFragment;
import com.android.mazhengyang.device_z.fragments.BatteryFragment;
import com.android.mazhengyang.device_z.fragments.CPUFragment;
import com.android.mazhengyang.device_z.fragments.MainFragment;
import com.android.mazhengyang.device_z.fragments.MemoryFragment;
import com.android.mazhengyang.device_z.fragments.ScreenFragment;
import com.android.mazhengyang.device_z.fragments.SystemFragment;
import com.android.mazhengyang.device_z.fragments.ToolMainFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemClickCallback {

    private final String TAG = MainActivity.class.getSimpleName();

    private MainFragment mainFragment;
    private SystemFragment systemFragment;
    private CPUFragment cpuFragment;
    private ScreenFragment screenFragment;
    private BatteryFragment batteryFragment;
    private MemoryFragment memoryFragment;
    private ToolMainFragment toolMainFragment;
    private AboutFragment aboutFragment;
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainFragment fragment = new MainFragment();
        fragment.setListener(this);
        showFragment(fragment);
        mainFragment = fragment;
    }

    @Override
    public void start(int position, int titleRes) {
        BaseFragment fragment = null;
        switch (position) {
            case 0:
                if (systemFragment == null) {
                    systemFragment = new SystemFragment();
                }
                fragment = systemFragment;
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
                }
                fragment = toolMainFragment;
                break;
            case 6:
                if (aboutFragment == null) {
                    aboutFragment = new AboutFragment();
                }
                fragment = aboutFragment;
            default:
                break;
        }

        fragment.setTitleRes(titleRes);
        showFragment(fragment);
    }

    private void showFragment(BaseFragment fragment) {

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
            if (currentFragment.isRunning()) {
                return;
            }
            showFragment(mainFragment);
        } else {
            super.onBackPressed();
        }
    }
}
