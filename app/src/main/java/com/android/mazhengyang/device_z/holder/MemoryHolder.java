package com.android.mazhengyang.device_z.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-6.
 */

public class MemoryHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.tv_available_value)
    public TextView availableValue;
    @BindView(R.id.tv_total_value)
    public TextView totalValue;
    @BindView(R.id.progress)
    public ProgressBar progressBar;

    public MemoryHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}