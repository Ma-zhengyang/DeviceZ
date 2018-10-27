package com.android.mazhengyang.device_z.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-19.
 */

public class CPUFragment extends Fragment {

    private final String TAG = CPUFragment.class.getSimpleName();

    public static final String TIME_IN_STATE_SYSFS =
            "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state";

    @BindView(R.id.tv_cpu_info)
    TextView cpuInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cpu, null);
        ButterKnife.bind(this, view);


        updateStates();

        return view;
    }

    private void updateStates() {

        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = new FileInputStream(TIME_IN_STATE_SYSFS);
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            String line;
            while ((line = br.readLine()) != null) {

                sb.append(line.concat("\n"));

                String[] nums = line.split(" ");
            }
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "updateStates: " + e);
        }

        cpuInfo.setText(sb.toString());
    }
}
