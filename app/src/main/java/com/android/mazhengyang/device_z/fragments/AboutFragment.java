package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mazhengyang.device_z.BuildConfig;
import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.adapter.ParentAdapter;
import com.android.mazhengyang.device_z.bean.AboutBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-14.
 */

public class AboutFragment extends BaseFragment {

    private static final String TAG = AboutFragment.class.getSimpleName();

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

        View view = inflater.inflate(R.layout.fragment_about, null);
        ButterKnife.bind(this, view);

        title.setText(getTitleRes());

        Context context = getContext();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        parentRecyclerView.setLayoutManager(layoutManager);

        String appName = "";
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            int labelRes = applicationInfo.labelRes;
            appName = context.getResources().getString(labelRes);
            // versionName = String.valueOf(packageInfo.versionName);// eclipse 写在manifest中
            versionName = BuildConfig.VERSION_NAME;// AS 写在${modle_name}.gradle中
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        List<AboutBean> child_list1 = new ArrayList<>();
        child_list1.add(new AboutBean(R.drawable.author_appname, R.string.author_appname, appName));
        child_list1.add(new AboutBean(R.drawable.author_appversion, R.string.author_appversion, versionName));
        child_list1.add(new AboutBean(R.drawable.author_qq, R.string.author_qq, "373591737"));
        String link = "<a href='https://github.com/Ma-zhengyang'>https://github.com/Ma-zhengyang</a>";
        child_list1.add(new AboutBean(R.drawable.author_github, R.string.author_github, link));

        List<List<?>> parent_list = new ArrayList<>();
        parent_list.add(child_list1);

        ParentAdapter parentAdapter = new ParentAdapter(context, parent_list, R.anim.layout_slide_right_out, null);
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

    @Override
    public boolean isRunning() {
        return false;
    }
}
