package com.android.mazhengyang.device_z.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.widget.MarqueeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-15.
 */

public class AboutHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_image)
    public ImageView imageView;
    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.tv_info)
    public MarqueeTextView info;

    public AboutHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
