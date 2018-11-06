package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.adapter.ParentAdapter;
import com.android.mazhengyang.device_z.bean.ScreenBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-5.
 */

public class ScreenFragment extends Fragment {

    private static final String TAG = ScreenFragment.class.getSimpleName();

    private ParentAdapter parentAdapter;

    @BindView(R.id.parent_recyclerview)
    RecyclerView parentRecyclerView;

    @BindView(R.id.tv_screen_dpi)
    TextView tvScreenDpi;
    @BindView(R.id.tv_screen_resolution)
    TextView tvScreenResolution;
    @BindView(R.id.tv_screen_density)
    TextView tvScreenDensity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_screen, null);
        ButterKnife.bind(this, view);

        Context context = getContext();

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        tvScreenDpi.setText(String.format(context.getString(R.string.screen_dpi_value), metrics.densityDpi));
        tvScreenResolution.setText(String.format("%d*%d", metrics.widthPixels, metrics.heightPixels));
        tvScreenDensity.setText(String.valueOf(metrics.density));

        List<List<ScreenBean>> parent_list = new ArrayList<>();
        List<ScreenBean> child_list = new ArrayList<>();
        child_list.add(new ScreenBean(R.mipmap.screen_bad_pixel_detection,
                R.string.screen_bad_pixel_detection,
                R.string.screen_bad_pixel_detection_des));
        child_list.add(new ScreenBean(R.mipmap.screen_edge_detection,
                R.string.screen_edge_detection,
                R.string.screen_edge_detection_des));
        child_list.add(new ScreenBean(R.mipmap.screen_multi_touch_detection,
                R.string.screen_multi_touch_detection,
                R.string.screen_multi_touch_detection_des));
        parent_list.add(child_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);
        parentAdapter = new ParentAdapter(context, parent_list);
        parentRecyclerView.setAdapter(parentAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
