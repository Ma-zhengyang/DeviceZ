package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.adapter.MainAdapter;
import com.android.mazhengyang.device_z.bean.MainBean;
import com.android.mazhengyang.device_z.callback.ItemClickCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-2.
 */

public class MainFragment extends BaseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private ItemClickCallback callback;

    @BindView(R.id.main_recyclerview)
    RecyclerView recyclerView;

    public void setListener(ItemClickCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, view);

        Context context = getContext();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        // gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean(
                R.color.system_info_color,
                R.drawable.icon_system,
                R.string.system_info, 0));
        list.add(new MainBean(
                R.color.cpu_info_color,
                R.drawable.icon_cpu,
                R.string.cpu_info, 1));
        list.add(new MainBean(
                R.color.screen_info_color,
                R.drawable.icon_screen,
                R.string.screen_info, 2));
        list.add(new MainBean(
                R.color.battery_info_color,
                R.drawable.icon_battery,
                R.string.battery_info, 3));
        list.add(new MainBean(
                R.color.memory_info_color,
                R.drawable.icon_memory,
                R.string.memory_info, 4));
        list.add(new MainBean(
                R.color.tools_info_color,
                R.drawable.icon_tools,
                R.string.tools_info, 5));
        list.add(new MainBean(
                R.color.about_info_color,
                R.drawable.icon_about,
                R.string.about_info, 6));

        MainAdapter adapter = new MainAdapter(context, list, callback);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
