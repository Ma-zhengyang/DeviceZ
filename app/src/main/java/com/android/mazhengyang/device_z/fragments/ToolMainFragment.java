package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.adapter.MainAdapter;
import com.android.mazhengyang.device_z.bean.MainBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-6.
 */

public class ToolMainFragment extends Fragment {

    private static final String TAG = ToolMainFragment.class.getSimpleName();


    private MainAdapter.OnItemClickListener onItemClickListener;

    @BindView(R.id.main_recyclerview)
    RecyclerView recyclerView;

    public void setListener(MainAdapter.OnItemClickListener listener) {
        onItemClickListener = listener;
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

        View view = inflater.inflate(R.layout.fragment_tool_main, null);
        ButterKnife.bind(this, view);

        Context context = getContext();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        // gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        List<MainBean> list = new ArrayList<>();
        list.add(new MainBean(
                R.color.tool_torch_color,
                R.drawable.icon_tool_torch,
                R.string.torch, 0));
        list.add(new MainBean(
                R.color.tool_compass_color,
                R.drawable.icon_tool_compass,
                R.string.compass, 1));
        list.add(new MainBean(
                R.color.tool_sensor_color,
                R.drawable.icon_tool_sensor,
                R.string.sensors, 2));

        MainAdapter adapter = new MainAdapter(context, list, onItemClickListener);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
