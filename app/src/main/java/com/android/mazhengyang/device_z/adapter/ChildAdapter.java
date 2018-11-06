package com.android.mazhengyang.device_z.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.bean.MemoryBean;
import com.android.mazhengyang.device_z.bean.OnlyTitleInfoBean;
import com.android.mazhengyang.device_z.bean.ScreenBean;
import com.android.mazhengyang.device_z.bean.SensorBean;
import com.android.mazhengyang.device_z.holder.MemoryHolder;
import com.android.mazhengyang.device_z.holder.OnlyTitleInfoHolder;
import com.android.mazhengyang.device_z.holder.ScreenHolder;
import com.android.mazhengyang.device_z.holder.SensorHolder;

import java.util.List;

/**
 * Created by mazhengyang on 18-10-25.
 */

public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ChildAdapter.class.getSimpleName();

    private static final int TYPE_ONLY_TITLE_INFO = 0;
    private static final int TYPE_SCREEN = 1;
    private static final int TYPE_MEMORY = 2;
    private static final int TYPE_SENSOR = 3;

    private Context context;
    private List<?> child_list;

    public ChildAdapter(Context context, List<?> child_list) {
        this.context = context;
        this.child_list = child_list;
    }

    public void update(List<?> child_list) {
        this.child_list = child_list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");

        if (viewType == TYPE_ONLY_TITLE_INFO) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.child_item_only_title_info, parent, false);
            OnlyTitleInfoHolder vh = new OnlyTitleInfoHolder(v);
            return vh;
        } else if (viewType == TYPE_SCREEN) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.child_item_screen, parent, false);
            ScreenHolder vh = new ScreenHolder(v);
            return vh;
        } else if (viewType == TYPE_MEMORY) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.child_item_memory, parent, false);
            MemoryHolder vh = new MemoryHolder(v);
            return vh;
        } else if (viewType == TYPE_SENSOR) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.child_item_sensor, parent, false);
            SensorHolder vh = new SensorHolder(v);
            return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        Object bean = child_list.get(position);
        if (bean instanceof OnlyTitleInfoBean) {

            String title = context.getString(((OnlyTitleInfoBean) bean).getTitleRes());
            String info = ((OnlyTitleInfoBean) bean).getInfo();

            ((OnlyTitleInfoHolder) holder).title.setText(title);
            ((OnlyTitleInfoHolder) holder).info.setText(info);
        } else if (bean instanceof ScreenBean) {

            int imgRes = ((ScreenBean) bean).getImgRes();
            String title = context.getString(((ScreenBean) bean).getTitleRes());
            String describe = context.getString(((ScreenBean) bean).getDescribeRes());

            ((ScreenHolder) holder).imageView.setImageResource(imgRes);
            ((ScreenHolder) holder).title.setText(title);
            ((ScreenHolder) holder).describe.setText(describe);
        } else if (bean instanceof MemoryBean) {

            String title = context.getString(((MemoryBean) bean).getTitleRes());
            Long free = ((MemoryBean) bean).getFreeValue();
            Long total = ((MemoryBean) bean).getTotalValue();

            ((MemoryHolder) holder).title.setText(title);
            ((MemoryHolder) holder).freeValue.setText(String.valueOf(free) + "MB");
            ((MemoryHolder) holder).totalValue.setText(String.valueOf(total) + "MB");
            ((MemoryHolder) holder).progressBar.setMax(100);
            ((MemoryHolder) holder).progressBar.setProgress((int) ((1 - free * 1.0f / total) * 100));
        } else if (bean instanceof SensorBean) {

            String title = context.getString(((SensorBean) bean).getTitleRes());
            int imgRes = ((SensorBean) bean).getImgRes();

            ((SensorHolder) holder).title.setText(title);
            ((SensorHolder) holder).imageView.setImageResource(imgRes);
        }
    }

    @Override
    public int getItemCount() {
        if (child_list == null) {
            return 0;
        }
        return child_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (child_list.get(position) instanceof OnlyTitleInfoBean) {
            return TYPE_ONLY_TITLE_INFO;
        } else if (child_list.get(position) instanceof ScreenBean) {
            return TYPE_SCREEN;
        } else if (child_list.get(position) instanceof MemoryBean) {
            return TYPE_MEMORY;
        } else if (child_list.get(position) instanceof SensorBean) {
            return TYPE_SENSOR;
        } else {
            return -1;
        }
    }

}
