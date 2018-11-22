package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.os.storage.StorageManager;
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
import com.android.mazhengyang.device_z.bean.MemoryBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-5.
 */

public class MemoryFragment extends BaseFragment {

    private static final String TAG = MemoryFragment.class.getSimpleName();

    private Handler handler = new Handler();
    private static final long INTERVAL = 2000;

    List<List<MemoryBean>> parent_list = new ArrayList<>();

    private ParentAdapter parentAdapter;

    @BindView(R.id.bar_title)
    TextView title;
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

        View view = inflater.inflate(R.layout.fragment_memory, null);
        ButterKnife.bind(this, view);

        title.setText(getTitleRes());

        Context context = getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);

        parent_list.clear();
        parent_list.add(0, getRamMemroy());
        parent_list.add(1, getRomMemroy());
        parent_list.add(2, getSdMemroy());

        parentAdapter = new ParentAdapter(context, parent_list, R.anim.layout_scale_out, null);
        parentRecyclerView.setAdapter(parentAdapter);

        handler.postDelayed(ramRunnable, INTERVAL);

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
        handler.removeCallbacks(ramRunnable);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    private Runnable ramRunnable = new Runnable() {
        @Override
        public void run() {
            parent_list.set(0, getRamMemroy());
            parentAdapter.update(parent_list);
            handler.postDelayed(this, INTERVAL);
        }
    };

    private List<MemoryBean> getRamMemroy() {
        Log.d(TAG, "getRamMemroy: start");

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
        int progress = 0;
        if (total != 0) {
            progress = (int) ((1 - available * 1.0f / total) * 100);
        }

        child_list.add(new MemoryBean(R.string.memory_capacity_ram,
                formatKB(available),
                formatKB(total),
                progress));

        Log.d(TAG, "getRamMemroy: end");

        return child_list;
    }

    public List<MemoryBean> getRomMemroy() {
        Log.d(TAG, "getRomMemroy: start");

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
        int progress = 0;
        if (total != 0) {
            progress = (int) ((1 - available * 1.0f / total) * 100);
        }

        Log.d(TAG, "getRomMemroy: available=" + available);
        Log.d(TAG, "getRomMemroy: total=" + total);

        child_list.add(new MemoryBean(R.string.memory_capacity_rom,
                formatByte(available),
                formatByte(total),
                progress));

        Log.d(TAG, "getRomMemroy: end");

        return child_list;
    }

    public List<MemoryBean> getSdMemroy() {

        Log.d(TAG, "getSdMemroy: start");

        Context context = getContext();

        boolean isMounted = false;
        long total = 0;
        long available = 0;
        int progress = 0;

        try {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
            getVolumeList.setAccessible(true);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);
            if (invokes != null) {
                for (int i = 0; i < invokes.length; i++) {
                    Object obj = invokes[i];
                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    String path = (String) getPath.invoke(obj, new Object[0]);
                    Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);

                    boolean removeable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();
                    Log.d(TAG, "path: " + path + " removeable: " + removeable);

                    if (removeable) {

                        String state = null;
                        try {
                            Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
                            state = (String) getVolumeState.invoke(storageManager, path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        isMounted = state.equals(Environment.MEDIA_MOUNTED);
                        if (isMounted) {
                            //Total SD memory
                            StatFs stat = new StatFs(path);
                            long blockSize = stat.getBlockSizeLong();
                            long totalBlocks = stat.getBlockCountLong();
                            total = totalBlocks * blockSize;

                            //Available SD memory
                            long availableBlocks = stat.getAvailableBlocksLong();
                            available = blockSize * availableBlocks;

                            Log.d(TAG, "getSdMemroy: available=" + available);
                            Log.d(TAG, "getSdMemroy: total=" + total);

                            if (total != 0) {
                                progress = (int) ((1 - available * 1.0f / total) * 100);
                            }
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        List<MemoryBean> child_list = new ArrayList<>();

        if (isMounted) {
            child_list.add(new MemoryBean(R.string.memory_capacity_sdcard,
                    formatByte(available),
                    formatByte(total),
                    progress));
        } else {
            child_list.add(new MemoryBean(R.string.memory_capacity_sdcard_not_mounted,
                    formatByte(available),
                    formatByte(total),
                    progress));
        }

        Log.d(TAG, "getSdMemroy: end");

        return child_list;
    }

    private static String formatKB(long size) {
        return String.format("%dMB", size / 1024);
    }

    private static String formatByte(long size) {
        return String.format("%dMB", size / 1024 / 1024);
    }
}
