package com.android.mazhengyang.device_z.adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.sensors.DeviceSensor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-10-22.
 */

public class SensorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = SensorAdapter.class.getSimpleName();

    private Context context;
    private List<DeviceSensor> list;

    private int mLastPosition = -1;

    public SensorAdapter(Context context, List<DeviceSensor> list) {
        this.context = context;
        this.list = list;
    }

    public void enableAll() {
        for (DeviceSensor device : list) {
            device.enable();
        }
    }

    public void disableAll() {
        for (DeviceSensor device : list) {
            device.disable();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View v = LayoutInflater.from(context)
                .inflate(R.layout.sensor_item, parent, false);
        SensorHolder vh = new SensorHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: ");

        DeviceSensor deviceSensor = list.get(position);

        ((SensorHolder) holder).title.setText(deviceSensor.getName());

        if (deviceSensor.isEnabled()) {
            ((SensorHolder) holder).title.setTextColor(Color.WHITE);
            ((SensorHolder) holder).title.setText(deviceSensor.getName());
            String event = deviceSensor.getEvent();
            if (event != null) {
                String[] eventSplit = event.split(",");
                if (eventSplit.length >= 2) {
                    ((SensorHolder) holder).value.setText(eventSplit[0]);
                    ((SensorHolder) holder).event.setText(eventSplit[1]);
                } else {
                    ((SensorHolder) holder).value.setText(event);
                }
            }
        } else {
            ((SensorHolder) holder).title.setTextColor(Color.GRAY);
            ((SensorHolder) holder).value.setText("");
            ((SensorHolder) holder).event.setText("");
        }

        addAnimation(holder);

    }

    public void addAnimation(RecyclerView.ViewHolder holder) {

            if (holder.getLayoutPosition() > mLastPosition) {

                Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_scale_out);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        enableAll();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                holder.itemView.startAnimation(animation);
                mLastPosition = holder.getLayoutPosition();
            }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SensorHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sensor_name)
        TextView title;
        @BindView(R.id.tv_sensor_value)
        TextView value;
        @BindView(R.id.tv_sensor_event)
        TextView event;

        public SensorHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
