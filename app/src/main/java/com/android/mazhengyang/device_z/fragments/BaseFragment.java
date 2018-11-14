package com.android.mazhengyang.device_z.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by mazhengyang on 18-11-13.
 */

public abstract class BaseFragment extends Fragment {

    private final String TAG = BaseFragment.class.getSimpleName();

    private int titleRes = -1;

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public abstract boolean isRunning();

}
