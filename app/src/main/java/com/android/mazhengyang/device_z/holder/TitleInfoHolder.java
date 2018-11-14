package com.android.mazhengyang.device_z.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-6.
 */

public class TitleInfoHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.tv_info)
    public TextView info;

    public TitleInfoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}