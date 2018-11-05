package com.android.mazhengyang.device_z.fragments;


import android.content.Context;
import android.os.Build;
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
import com.android.mazhengyang.device_z.bean.TitleInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-19.
 */

public class DeviceInfoFragment extends Fragment {

    private static final String TAG = DeviceInfoFragment.class.getSimpleName();

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

        //layout_height/layout_margin等属性会无效
//        View view = inflater.inflate(R.layout.fragment_deviceinfo, null);

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_deviceinfo, container, false);

        ButterKnife.bind(this, view);

        Context context = getContext();

        List<List<TitleInfoBean>> parent_list = new ArrayList<>();
        parent_list.add(getChildList1(context));
        parent_list.add(getChildList2(context));

        //必须设置layout manager，否则adapter的onCreateViewHolder和onBindViewHolder不会调用
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);
        ParentAdapter parentAdapter = new ParentAdapter(context, parent_list);
        parentRecyclerView.setAdapter(parentAdapter);

        return view;
    }

    private List<TitleInfoBean> getChildList1(Context context) {

        List<TitleInfoBean> child_list = new ArrayList<>();
        child_list.add(new TitleInfoBean(context.getString(R.string.device_brand), android.os.Build.BRAND));
        child_list.add(new TitleInfoBean(context.getString(R.string.device_model), android.os.Build.MODEL));
        child_list.add(new TitleInfoBean(context.getString(R.string.device_product), android.os.Build.PRODUCT));

        return child_list;
    }

    private List<TitleInfoBean> getChildList2(Context context) {

        List<TitleInfoBean> child_list = new ArrayList<>();
        child_list.add(new TitleInfoBean(context.getString(R.string.device_board), android.os.Build.BOARD));
        child_list.add(new TitleInfoBean(context.getString(R.string.hardware), Build.HARDWARE));
        child_list.add(new TitleInfoBean(context.getString(R.string.os_build), android.os.Build.VERSION.RELEASE));
        child_list.add(new TitleInfoBean(context.getString(R.string.os_build_display), android.os.Build.DISPLAY));
        child_list.add(new TitleInfoBean(context.getString(R.string.sdk), Build.VERSION.SDK));
        child_list.add(new TitleInfoBean(context.getString(R.string.os_build_id), android.os.Build.ID));
       // child_list.add(new TitleInfoBean(context.getString(R.string.os_time), android.os.Build.TIME));
        child_list.add(new TitleInfoBean(context.getString(R.string.os_type), android.os.Build.TYPE));
        child_list.add(new TitleInfoBean(context.getString(R.string.fingerprint), android.os.Build.FINGERPRINT));
        child_list.add(new TitleInfoBean(context.getString(R.string.tag), Build.TAGS));

        return child_list;
    }

}
