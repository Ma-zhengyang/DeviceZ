package com.android.mazhengyang.device_z.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.bean.MainBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-2.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = MainAdapter.class.getSimpleName();

    private Context context;
    private List<MainBean> list;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    public MainAdapter(Context context, List<MainBean> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        onItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.main_item, parent, false);
        MainHolder vh = new MainHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MainBean bean = list.get(position);

        Resources resources = context.getResources();
        ((MainHolder) holder).parent.setBackgroundColor(resources.getColor(bean.getBackground()));
        ((MainHolder) holder).icon.setImageDrawable(resources.getDrawable(bean.getImgRes()));
        ((MainHolder) holder).title.setText(resources.getString(bean.getTitleRes()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Animation animation;

        @BindView(R.id.parent)
        View parent;
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.title)
        TextView title;

        public MainHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            animation = AnimationUtils.loadAnimation(context, R.anim.slide_scale_out);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    itemView.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(list.get(getPosition()).getId());
                        itemView.setClickable(true);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        @Override
        public void onClick(View v) {
            parent.startAnimation(animation);
        }
    }
}
