package com.android.mazhengyang.device_z.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.bean.BaseBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-25.
 */

public class ChildBaseInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ChildBaseInfoAdapter.class.getSimpleName();

    private Context context;
    private List<BaseBean> child_list;

    public ChildBaseInfoAdapter(Context context, List<BaseBean> child_list) {
        this.context = context;
        this.child_list = child_list;
    }

    public void update(List<BaseBean> child_list) {
        this.child_list = child_list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");

        View v = LayoutInflater.from(context)
                .inflate(R.layout.childbaseinfo_item, parent, false);
        DeviceInfoHolder vh = new DeviceInfoHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        BaseBean bean = child_list.get(position);
        ((DeviceInfoHolder) holder).title.setText(bean.getTitle());
        ((DeviceInfoHolder) holder).info.setText(bean.getInfo());
    }

    @Override
    public int getItemCount() {
        if (child_list == null) {
            return 0;
        }
        return child_list.size();
    }

    public class DeviceInfoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_childbaseinfo_title)
        TextView title;
        @BindView(R.id.tv_childbaseinfo_info)
        TextView info;

        public DeviceInfoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
