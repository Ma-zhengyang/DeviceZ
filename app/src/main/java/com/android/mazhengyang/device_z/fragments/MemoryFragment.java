package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.adapter.ParentAdapter;
import com.android.mazhengyang.device_z.bean.MemoryBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-5.
 */

public class MemoryFragment extends Fragment {

    private static final String TAG = MemoryFragment.class.getSimpleName();

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

        List<List<MemoryBean>> parent_list = new ArrayList<>();
        parent_list.add(getRomMemroy(context));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);
        ParentAdapter parentAdapter = new ParentAdapter(context, parent_list);
        parentRecyclerView.setAdapter(parentAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public List<MemoryBean> getRomMemroy(Context context) {

        List<MemoryBean> child_list = new ArrayList<>();

        //Total rom memory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        long total = totalBlocks * blockSize / 1024 / 1024;

        //Available rom memory
        long availableBlocks = stat.getAvailableBlocksLong();
        long available = blockSize * availableBlocks / 1024 / 1024;
        child_list.add(new MemoryBean(R.string.memory_capacity, available, total));

        return child_list;
    }
}
