package com.android.mazhengyang.device_z;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.mazhengyang.device_z.fragments.BatteryFragment;
import com.android.mazhengyang.device_z.fragments.CPUFragment;
import com.android.mazhengyang.device_z.fragments.DeviceInfoFragment;
import com.android.mazhengyang.device_z.fragments.SensorsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

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

        initView();

        showFragment(0, getString(R.string.device_info));
        Menu menu = navigationView.getMenu();
        menu.getItem(0).setChecked(true);
        getSupportActionBar().setTitle(menu.getItem(0).getTitle());
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private void initView() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                Log.d(TAG, "onDrawerOpened: ");
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d(TAG, "onDrawerClosed: ");
                super.onDrawerClosed(drawerView);
                Menu menu = navigationView.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    if (menu.getItem(i).isChecked()) {
                        getSupportActionBar().setTitle(menu.getItem(i).getTitle());
                        break;
                    }
                }
            }
        };
        toggle.syncState();//不加上左边指示器图标出不来
        drawerLayout.setDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showFragment(int id, String title) {

        Fragment fragment;
        switch (id) {
            case R.id.nav_cpu:
                if (cpuFragment == null) {
                    cpuFragment = new CPUFragment();
                }
                fragment = cpuFragment;
                break;
            case R.id.nav_sensors:
                if (sensorsFragment == null) {
                    sensorsFragment = new SensorsFragment();
                }
                fragment = sensorsFragment;
                break;
            case R.id.nav_battery:
                if (batteryFragment == null) {
                    batteryFragment = new BatteryFragment();
                }
                fragment = batteryFragment;
                break;
            case R.id.nav_device_info:
            default:
                if (deviceInfoFragment == null) {
                    deviceInfoFragment = new DeviceInfoFragment();
                }
                fragment = deviceInfoFragment;
                break;
        }

        if (fragment != currentFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_content, fragment)
                    .show(fragment)
                    .commit();
            getSupportActionBar().setTitle(title);

            currentFragment = fragment;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String title = (String) item.getTitle();
        showFragment(id, title);

        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setChecked(false);
        }
        item.setChecked(true);

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
