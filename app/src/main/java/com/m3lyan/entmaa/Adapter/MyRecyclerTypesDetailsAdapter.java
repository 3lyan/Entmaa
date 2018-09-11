package com.m3lyan.entmaa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.m3lyan.entmaa.Activity.TypesDetailsActivity;
import com.m3lyan.entmaa.Model.TypesDataDetailsModel;
import com.m3lyan.entmaa.Model.TypesDetailsModel;
import com.m3lyan.entmaa.Model.TypesModel;
import com.m3lyan.entmaa.R;

import java.util.ArrayList;

public class MyRecyclerTypesDetailsAdapter extends RecyclerView.Adapter<MyRecyclerTypesDetailsAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<String> data;

    public MyRecyclerTypesDetailsAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyRecyclerTypesDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_types_details, parent, false);
        return new MyRecyclerTypesDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyRecyclerTypesDetailsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(data.get(position)).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                holder.frameImgs.setBackground(resource);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameImgs;
        public ViewHolder(View itemView) {
            super(itemView);
            frameImgs=itemView.findViewById(R.id.frameImgs);



        }
    }
}
