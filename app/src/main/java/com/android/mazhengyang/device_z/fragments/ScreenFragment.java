package com.android.mazhengyang.device_z.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-5.
 */

public class ScreenFragment extends Fragment {

    private static final String TAG = ScreenFragment.class.getSimpleName();

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
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, null);
        ButterKnife.bind(this, view);

        Context context = getContext();

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        tvScreenDpi.setText(String.format(context.getString(R.string.screen_dpi_value), metrics.densityDpi));
        tvScreenResolution.setText(String.format("%d*%d", metrics.widthPixels, metrics.heightPixels));
        tvScreenDensity.setText(String.valueOf(metrics.density));

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
}
