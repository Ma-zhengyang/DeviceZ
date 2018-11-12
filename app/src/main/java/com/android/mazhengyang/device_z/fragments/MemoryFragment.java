package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.adapter.ParentAdapter;
import com.android.mazhengyang.device_z.bean.MemoryBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-5.
 */

public class MemoryFragment extends Fragment {

    private static final String TAG = MemoryFragment.class.getSimpleName();

    private Handler handler = new Handler();
    private static final long INTERVAL = 2000;

    List<List<MemoryBean>> parent_list = new ArrayList<>();

    private ParentAdapter parentAdapter;

    @BindView(R.id.parent_recyclerview)
    RecyclerView parentRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory, null);
        ButterKnife.bind(this, view);

        Context context = getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);
        parentAdapter = new ParentAdapter(context, null);
        parentRecyclerView.setAdapter(parentAdapter);

        handler.post(runnable);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            parent_list.clear();
            parent_list.add(getRamMemroy());
            parent_list.add(getRomMemroy());
            parentAdapter.update(parent_list);
            handler.postDelayed(this, INTERVAL);
        }
    };

    public List<MemoryBean> getRamMemroy() {

        Context context = getContext();

        List<MemoryBean> child_list = new ArrayList<>();

        long total = 0;
        long available = 0;

        try {
            InputStream is = new FileInputStream("/proc/meminfo");
            InputStreamReader ir = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(ir);
            String line;

            while ((line = br.readLine()) != null) {

                String[] array = line.split(":");
                if (array.length < 2) {
                    continue;
                }

                String data = array[1].replace("kB", "").trim();

                if (line.startsWith("MemTotal")) {
                    total = Long.valueOf(data);
                } else if (line.startsWith("MemAvailable")) {
                    available = Long.valueOf(data);
                }
            }
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "getCpuFreq: " + e);
        }

        Log.d(TAG, "getRamMemroy: total=" + total);
        Log.d(TAG, "getRamMemroy: available=" + available);

//        //单位是KB，乘以1024转换为Byte
//        available *= 1024;
//        total *= 1024;
//        Formatter.formatFileSize(context, available);

        int progress = (int) ((1 - available * 1.0f / total) * 100);

        child_list.add(new MemoryBean(R.string.memory_capacity_ram,
                formatKB(available),
                formatKB(total),
                progress));

        return child_list;
    }

    public List<MemoryBean> getRomMemroy() {

        Context context = getContext();

        List<MemoryBean> child_list = new ArrayList<>();

        //Total rom memory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        long total = totalBlocks * blockSize;

        //Available rom memory
        long availableBlocks = stat.getAvailableBlocksLong();
        long available = blockSize * availableBlocks;
        int progress = (int) ((1 - available * 1.0f / total) * 100);

        child_list.add(new MemoryBean(R.string.memory_capacity_rom,
                formatByte(available),
                formatByte(total),
                progress));

        return child_list;
    }

    private static String formatKB(long size) {
        return String.format("%dMB", size / 1024);
    }

    private static String formatByte(long size) {
        return String.format("%dMB", size / 1024 / 1024);
    }
}
