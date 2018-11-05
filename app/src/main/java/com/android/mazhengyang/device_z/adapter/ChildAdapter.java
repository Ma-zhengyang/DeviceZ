package com.android.mazhengyang.device_z.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.bean.MemoryBean;
import com.android.mazhengyang.device_z.bean.TitleInfoBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-25.
 */

public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ChildAdapter.class.getSimpleName();

    private static final int TYPE_BASE = 0;
    private static final int TYPE_MEMORYBASE = 1;

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

        if (viewType == TYPE_BASE) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.child_item_only_title_info, parent, false);
            OnlyTitleInfoHolder vh = new OnlyTitleInfoHolder(v);
            return vh;
        } else if (viewType == TYPE_MEMORYBASE) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.child_item_memory, parent, false);
            MemoryHolder vh = new MemoryHolder(v);
            return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        Object bean = child_list.get(position);
        if (bean instanceof TitleInfoBean) {
            ((OnlyTitleInfoHolder) holder).title.setText(((TitleInfoBean) bean).getTitle());
            ((OnlyTitleInfoHolder) holder).info.setText(((TitleInfoBean) bean).getInfo());
        } else if (bean instanceof MemoryBean) {
            ((MemoryHolder) holder).title.setText(((MemoryBean) bean).getTitle());

            Long free = ((MemoryBean) bean).getFreeValue();
            Long total = ((MemoryBean) bean).getTotalValue();

            ((MemoryHolder) holder).freeValue.setText(String.valueOf(free) + "MB");
            ((MemoryHolder) holder).totalValue.setText(String.valueOf(total) + "MB");
            ((MemoryHolder) holder).progressBar.setMax(100);
            ((MemoryHolder) holder).progressBar.setProgress((int) ((1 - free * 1.0f / total) * 100));
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
        if (child_list.get(position) instanceof TitleInfoBean) {
            return TYPE_BASE;
        } else if (child_list.get(position) instanceof MemoryBean) {
            return TYPE_MEMORYBASE;
        } else {
            return -1;
        }
    }

    public class OnlyTitleInfoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_info)
        TextView info;

        public OnlyTitleInfoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MemoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_free_value)
        TextView freeValue;
        @BindView(R.id.tv_total_value)
        TextView totalValue;
        @BindView(R.id.progress)
        ProgressBar progressBar;

        public MemoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
