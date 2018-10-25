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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

        showFragment(0, getString(R.string.device_info));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sensorsFragment != null){
            sensorsFragment.disableAll();
        }
    }

    private void initView() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
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
            case R.id.nav_device_info:
            default:
                if (deviceInfoFragment == null) {
                    deviceInfoFragment = new DeviceInfoFragment();
                }
                fragment = deviceInfoFragment;
                break;
        }

        if(sensorsFragment != null){
            sensorsFragment.disableAll();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_content, fragment)
                .show(fragment)
                .commit();
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String title = (String) item.getTitle();
        showFragment(id, title);
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
