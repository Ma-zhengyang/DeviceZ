package com.android.mazhengyang.device_z.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.callback.ItemClickCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-6.
 */

public class ScreenHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_image)
    public ImageView imageView;
    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.tv_describe)
    public TextView describe;
    @BindView(R.id.btn_start)
    public Button start;

    public ScreenHolder(View itemView, final ItemClickCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.start(ScreenHolder.this.getPosition(), -1);
                }
            }
        });
    }
}
