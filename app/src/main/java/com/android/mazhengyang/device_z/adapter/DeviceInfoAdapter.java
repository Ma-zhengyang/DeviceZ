package com.android.mazhengyang.device_z.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.bean.DeviceInfoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-25.
 */

public class DeviceInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = DeviceInfoAdapter.class.getSimpleName();

    private Context context;
    private List<DeviceInfoBean> list;

    public DeviceInfoAdapter(Context context, List<DeviceInfoBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View v = LayoutInflater.from(context)
                .inflate(R.layout.deviceinfo_item, parent, false);
        DeviceInfoHolder vh = new DeviceInfoHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: ");

        DeviceInfoBean bean = list.get(position);

        Log.d(TAG, "onBindViewHolder: " + bean.getTitle());

        ((DeviceInfoHolder) holder).title.setText(bean.getTitle());
        ((DeviceInfoHolder) holder).info.setText(bean.getInfo());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DeviceInfoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_deviceinfo_title)
        TextView title;
        @BindView(R.id.tv_deviceinfo_info)
        TextView info;

        public DeviceInfoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
