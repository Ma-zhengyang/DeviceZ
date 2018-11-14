package com.android.mazhengyang.device_z.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.adapter.ParentAdapter;
import com.android.mazhengyang.device_z.bean.TitleInfoBean;

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

public class CPUFragment extends BaseFragment {

    private static final String TAG = CPUFragment.class.getSimpleName();

    private static final String CAT = "/system/bin/cat";
    private static final String CPUINFO = "/proc/cpuinfo";
    private static final String CPUINFO_MAX_FREQ =
            "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
    private static final String CPUINFO_MIN_FREQ =
            "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq";
    private static final String CPUINFO_CUR_FREQ =
            "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_cur_freq";

    @BindView(R.id.bar_title)
    TextView title;
    @BindView(R.id.tv_cpuinfo_cur_freq)
    TextView tvCpuCurFreq;
    @BindView(R.id.tv_cpuinfo_max_freq)
    TextView tvCpuMaxFreq;
    @BindView(R.id.tv_cpuinfo_min_freq)
    TextView tvCpuMinFreq;
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

        View view = inflater.inflate(R.layout.fragment_cpu, null);
        ButterKnife.bind(this, view);

        title.setText(getTitleRes());

        Context context = getContext();

        String curCpuFreq = getCpuFreq(CPUINFO_CUR_FREQ);
        Log.d(TAG, "onCreateView: curCpuFreq=" + curCpuFreq);
        if (curCpuFreq != null && !"".equals(curCpuFreq)) {
            tvCpuCurFreq.setText(String.format("%d MHz", Integer.valueOf(curCpuFreq) / 1000));
        }
        String maxCpuFreq = getCpuFreq(CPUINFO_MAX_FREQ);
        Log.d(TAG, "onCreateView: maxCpuFreq=" + maxCpuFreq);
        if (maxCpuFreq != null && !"".equals(maxCpuFreq)) {
            tvCpuMaxFreq.setText(String.format("%d MHz", Integer.valueOf(maxCpuFreq) / 1000));
        }
        String minCpuFreq = getCpuFreq(CPUINFO_MIN_FREQ);
        Log.d(TAG, "onCreateView: minCpuFreq=" + minCpuFreq);
        if (minCpuFreq != null && !"".equals(minCpuFreq)) {
            tvCpuMinFreq.setText(String.format("%d MHz", Integer.valueOf(minCpuFreq) / 1000));
        }

        List<List<TitleInfoBean>> parent_list = new ArrayList<>();
        parent_list.add(getCpuInfo(context, CPUINFO));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);
        ParentAdapter parentAdapter = new ParentAdapter(context, parent_list, null);
        parentRecyclerView.setAdapter(parentAdapter);

        return view;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    private String getCpuFreq(String path) {

        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = new FileInputStream(path);
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "getCpuFreq: " + e);
        }
        return sb.toString();
    }

    private List<TitleInfoBean> getCpuInfo(Context context, String path) {
        List<TitleInfoBean> child_list = new ArrayList<>();

        int count = 0;

        TitleInfoBean cpu_name = new TitleInfoBean(R.string.cpu_name, "");
        TitleInfoBean cpu_core_count = new TitleInfoBean(R.string.cpu_core_count, "");
        TitleInfoBean cpu_implementer = new TitleInfoBean(R.string.cpu_implementer, "");
        TitleInfoBean cpu_BogoMIPS = new TitleInfoBean(R.string.cpu_BogoMIPS, "");
        TitleInfoBean cpu_Features = new TitleInfoBean(R.string.cpu_Features, "");
        TitleInfoBean cpu_architecture = new TitleInfoBean(R.string.cpu_architecture, "");
        TitleInfoBean cpu_variant = new TitleInfoBean(R.string.cpu_variant, "");
        TitleInfoBean cpu_part = new TitleInfoBean(R.string.cpu_part, "");
        TitleInfoBean cpu_revision = new TitleInfoBean(R.string.cpu_revision, "");
        child_list.add(cpu_name);
        child_list.add(cpu_core_count);
        child_list.add(cpu_implementer);
        child_list.add(cpu_BogoMIPS);
        child_list.add(cpu_Features);
        child_list.add(cpu_architecture);
        child_list.add(cpu_variant);
        child_list.add(cpu_part);
        child_list.add(cpu_revision);

        try {
            InputStream is = new FileInputStream(path);
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length < 2) {
                    continue;
                }
                if (line.contains("Processor")) {
                    cpu_name.setInfo(data[1]);
                } else if (line.contains("implementer")) {
                    cpu_implementer.setInfo(data[1]);
                } else if (line.contains("BogoMIPS")) {
                    cpu_BogoMIPS.setInfo(data[1]);
                } else if (line.contains("Features")) {
                    cpu_Features.setInfo(data[1]);
                } else if (line.contains("architecture")) {
                    cpu_architecture.setInfo(data[1]);
                } else if (line.contains("variant")) {
                    cpu_variant.setInfo(data[1]);
                } else if (line.contains("part")) {
                    cpu_part.setInfo(data[1]);
                } else if (line.contains("revision")) {
                    cpu_revision.setInfo(data[1]);
                } else if (line.contains("processor")) {
                    count++;
                }
            }
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "getCpuFreq: " + e);
        }

        cpu_core_count.setInfo(String.valueOf(count));

        return child_list;

    }

//    public String getCpuFreq(final String path) {
//        StringBuilder sb = new StringBuilder();
//        ProcessBuilder cmd;
//        try {
//            String[] args = {CAT, path};
//            cmd = new ProcessBuilder(args);
//            Process process = cmd.start();
//            InputStream in = process.getInputStream();
//            byte[] re = new byte[24];
//            while (in.read(re) != -1) {
//                sb.append(new String(re));
//                sb.append("\n");
//            }
//            in.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return "N/A";
//        }
//        return sb.toString();
//    }
}
