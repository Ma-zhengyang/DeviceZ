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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.adapter.DeviceInfoAdapter;
import com.android.mazhengyang.device_z.bean.DeviceInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-19.
 */

public class DeviceInfoFragment extends Fragment {

    private final String TAG = DeviceInfoFragment.class.getSimpleName();

    @BindView(R.id.deviceinfo_recyclerview)
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");

        View view = inflater.inflate(R.layout.fragment_deviceinfo, null);
        ButterKnife.bind(this, view);

        Context context = getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        List<DeviceInfoBean> list = new ArrayList<>();

        StringBuilder sb = new StringBuilder();

        DeviceInfoBean device = new DeviceInfoBean();
        sb.append(String.format(context.getString(R.string.device_board), android.os.Build.BOARD));
        sb.append(String.format(context.getString(R.string.device_brand), android.os.Build.BRAND));
        sb.append(String.format(context.getString(R.string.device_device), android.os.Build.DEVICE));
        sb.append(String.format(context.getString(R.string.device_model), android.os.Build.MODEL));
        sb.append(String.format(context.getString(R.string.device_product), android.os.Build.PRODUCT));
        device.setTitle(String.format(context.getString(R.string.device)));
        device.setInfo(sb.toString());
        list.add(device);

        sb.setLength(0);
        DeviceInfoBean os = new DeviceInfoBean();
        sb.append(String.format(context.getString(R.string.os_build), android.os.Build.VERSION.RELEASE,
                android.os.Build.VERSION.INCREMENTAL));
        sb.append(String.format(context.getString(R.string.os_build_display), android.os.Build.DISPLAY));
        sb.append(String.format(context.getString(R.string.os_build_id), android.os.Build.ID));
        sb.append(String.format(context.getString(R.string.os_time), android.os.Build.TIME));
        sb.append(String.format(context.getString(R.string.os_type), android.os.Build.TYPE));
        os.setTitle(String.format(context.getString(R.string.os)));
        os.setInfo(sb.toString());
        list.add(os);

        sb.setLength(0);
        DeviceInfoBean screen = new DeviceInfoBean();
        sb.append(String.format(context.getString(R.string.screen_width), metrics.widthPixels));
        sb.append(String.format(context.getString(R.string.screen_height), metrics.heightPixels));
        sb.append(String.format(context.getString(R.string.density_density), metrics.density));
        sb.append(String.format(context.getString(R.string.density_dpi), metrics.densityDpi));
        sb.append(String.format(context.getString(R.string.density_xdpi), metrics.xdpi));
        sb.append(String.format(context.getString(R.string.density_ydpi), metrics.ydpi));
        screen.setTitle(String.format(context.getString(R.string.screen)));
        screen.setInfo(sb.toString());
        list.add(screen);

        DeviceInfoAdapter deviceInfoAdapter = new DeviceInfoAdapter(context, list);
        recyclerView.setAdapter(deviceInfoAdapter);

        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(context,
                R.anim.layout_slide_right_out);
        recyclerView.setLayoutAnimation(animationController);
        recyclerView.scheduleLayoutAnimation();

        return view;
    }

}
