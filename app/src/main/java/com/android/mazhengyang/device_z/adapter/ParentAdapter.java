package com.android.mazhengyang.device_z.adapter;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.android.mazhengyang.device_z.R;
import com.android.mazhengyang.device_z.ItemClickCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mazhengyang on 18-11-2.
 */

public class ParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ParentAdapter.class.getSimpleName();

    private Context context;
    private List<?> parent_list;
    private int animRes;
    private ItemClickCallback listener;

    public ParentAdapter(Context context, List<?> lists, int animRes, ItemClickCallback listener) {
        this.context = context;
        this.parent_list = lists;
        this.animRes = animRes;
        this.listener = listener;
    }

    public void update(List<?> parent_list) {
        this.parent_list = parent_list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View v = LayoutInflater.from(context)
                .inflate(R.layout.parent_item, parent, false);
        ParentHolder vh = new ParentHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (this.parent_list == null) {
            return;
        }

        List<?> child_list = (ArrayList<?>) parent_list.get(position);

        RecyclerView.Adapter adapter = ((ParentHolder) holder).childRecyclerView.getAdapter();
        if (adapter == null) {

            RecyclerView recyclerView = ((ParentHolder) holder).childRecyclerView;

            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            if (child_list.size() > 1) {
                recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            }

            ChildAdapter childBaseInfoAdapter = new ChildAdapter(context, child_list, listener);
            recyclerView.setAdapter(childBaseInfoAdapter);

            LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(context,
                    animRes);
            recyclerView.setLayoutAnimation(animationController);
            recyclerView.scheduleLayoutAnimation();
        } else {
            ((ChildAdapter) adapter).update(child_list);
        }
    }

    @Override
    public int getItemCount() {
        if (parent_list == null) {
            return 0;
        }
        return parent_list.size();
    }

    public class ParentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.child_recyclerview)
        RecyclerView childRecyclerView;

        public ParentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
