package com.android.mazhengyang.device_z.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.bean.AboutBean;
import com.android.mazhengyang.device_z.bean.IconTitleInfoBean;
import com.android.mazhengyang.device_z.bean.MemoryBean;
import com.android.mazhengyang.device_z.bean.TitleInfoBean;
import com.android.mazhengyang.device_z.bean.ScreenBean;
import com.android.mazhengyang.device_z.bean.SensorBean;
import com.android.mazhengyang.device_z.adapter.holder.AboutHolder;
import com.android.mazhengyang.device_z.adapter.holder.IconTitleInfoHolder;
import com.android.mazhengyang.device_z.adapter.holder.MemoryHolder;
import com.android.mazhengyang.device_z.adapter.holder.TitleInfoHolder;
import com.android.mazhengyang.device_z.adapter.holder.ScreenHolder;
import com.android.mazhengyang.device_z.adapter.holder.SensorHolder;
import com.android.mazhengyang.device_z.ItemClickCallback;

import java.util.List;

/**
 * Created by mazhengyang on 18-10-25.
 */

public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ChildAdapter.class.getSimpleName();

    private static final int TYPE_ONLY_TITLE_INFO = 0;
    private static final int TYPE_ICON_TITLE_INFO = 1;
    private static final int TYPE_SCREEN = 2;
    private static final int TYPE_MEMORY = 3;
    private static final int TYPE_SENSOR = 4;
    private static final int TYPE_ABOUT = 5;

    private Context context;
    private List<?> child_list;
    private ItemClickCallback listener;

    public ChildAdapter(Context context, List<?> child_list, ItemClickCallback listener) {
        this.context = context;
        this.child_list = child_list;
        this.listener = listener;
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
                    .inflate(R.layout.child_item_title_info, parent, false);
            TitleInfoHolder vh = new TitleInfoHolder(v);
            return vh;
        } else if (viewType == TYPE_ICON_TITLE_INFO) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.child_item_icon_title_info, parent, false);
            IconTitleInfoHolder vh = new IconTitleInfoHolder(v);
            return vh;
        } else if (viewType == TYPE_SCREEN) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.child_item_screen, parent, false);
            ScreenHolder vh = new ScreenHolder(v, listener);
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
        } else if (viewType == TYPE_ABOUT) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.child_item_about, parent, false);
            AboutHolder vh = new AboutHolder(v);
            return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        Object bean = child_list.get(position);
        if (bean instanceof TitleInfoBean) {

            String title = context.getString(((TitleInfoBean) bean).getTitleRes());
            String info = ((TitleInfoBean) bean).getInfo();

            ((TitleInfoHolder) holder).title.setText(title);
            ((TitleInfoHolder) holder).info.setText(info);
        } else if (bean instanceof IconTitleInfoBean) {

            int imgRes = ((IconTitleInfoBean) bean).getImgRes();
            String title = context.getString(((IconTitleInfoBean) bean).getTitleRes());
            String info = ((IconTitleInfoBean) bean).getInfo();

            ((IconTitleInfoHolder) holder).imageView.setImageResource(imgRes);
            ((IconTitleInfoHolder) holder).title.setText(title);
            ((IconTitleInfoHolder) holder).info.setText(info);

        } else if (bean instanceof ScreenBean) {

            int imgRes = ((ScreenBean) bean).getImgRes();
            String title = context.getString(((ScreenBean) bean).getTitleRes());
            String describe = context.getString(((ScreenBean) bean).getDescribeRes());

            ((ScreenHolder) holder).imageView.setImageResource(imgRes);
            ((ScreenHolder) holder).title.setText(title);
            ((ScreenHolder) holder).describe.setText(describe);
        } else if (bean instanceof MemoryBean) {

            String title = context.getString(((MemoryBean) bean).getTitleRes());
            String available = ((MemoryBean) bean).getAvailableValue();
            String total = ((MemoryBean) bean).getTotalValue();
            int progress = ((MemoryBean) bean).getProgress();

            ((MemoryHolder) holder).title.setText(title);
            ((MemoryHolder) holder).availableValue.setText(available);
            ((MemoryHolder) holder).totalValue.setText(total);
            ((MemoryHolder) holder).progressBar.setMax(100);
            ((MemoryHolder) holder).progressBar.setProgress(progress);
        } else if (bean instanceof SensorBean) {

            String title = context.getString(((SensorBean) bean).getTitleRes());
            int imgRes = ((SensorBean) bean).getImgRes();

            ((SensorHolder) holder).imageView.setImageResource(imgRes);
            ((SensorHolder) holder).title.setText(title);
        } else if (bean instanceof AboutBean) {

            int imgRes = ((AboutBean) bean).getImgRes();
            String title = context.getString(((AboutBean) bean).getTitleRes());
            String info = ((AboutBean) bean).getInfo();

            ((AboutHolder) holder).imageView.setImageResource(imgRes);
            ((AboutHolder) holder).title.setText(title);
            if (info.contains("http")) {
                ((AboutHolder) holder).info.setText(Html.fromHtml(info));
                ((AboutHolder) holder).info.setMovementMethod(LinkMovementMethod.getInstance());
                ((AboutHolder) holder).info.setLinkTextColor(0xFF53868B);
            } else {
                ((AboutHolder) holder).info.setText(info);
            }
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
            return TYPE_ONLY_TITLE_INFO;
        } else if (child_list.get(position) instanceof IconTitleInfoBean) {
            return TYPE_ICON_TITLE_INFO;
        } else if (child_list.get(position) instanceof ScreenBean) {
            return TYPE_SCREEN;
        } else if (child_list.get(position) instanceof MemoryBean) {
            return TYPE_MEMORY;
        } else if (child_list.get(position) instanceof SensorBean) {
            return TYPE_SENSOR;
        } else if (child_list.get(position) instanceof AboutBean) {
            return TYPE_ABOUT;
        } else {
            return -1;
        }
    }

}
