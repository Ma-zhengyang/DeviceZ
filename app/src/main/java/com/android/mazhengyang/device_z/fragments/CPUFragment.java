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
import com.android.mazhengyang.device_z.adapter.ParentAdapter;
import com.android.mazhengyang.device_z.bean.BaseBean;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-19.
 */

public class CPUFragment extends Fragment {

    private final String TAG = CPUFragment.class.getSimpleName();

    public static final String TIME_IN_STATE_SYSFS =
            "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state";

    @BindView(R.id.parent_recyclerview)
    RecyclerView parentRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cpu, null);
        ButterKnife.bind(this, view);

        Context context = getContext();

        List<List<BaseBean>> parent_list = new ArrayList<>();

        parent_list.add(getChildList());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);

        ParentAdapter parentAdapter = new ParentAdapter(context, parent_list);
        parentRecyclerView.setAdapter(parentAdapter);

        return view;
    }

    private List<BaseBean> getChildList() {

        List<BaseBean> child_list = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(TIME_IN_STATE_SYSFS);
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            String line;
            while ((line = br.readLine()) != null) {

                child_list.add(new BaseBean(line, ""));
            }
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "updateStates: " + e);
        }

        return child_list;

    }
}
