package com.m3lyan.entmaa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m3lyan.entmaa.Activity.TypesDetailsActivity;
import com.m3lyan.entmaa.Model.NotifyModel;
import com.m3lyan.entmaa.Model.TypesModel;
import com.m3lyan.entmaa.R;

import java.util.ArrayList;

public class MyRecyclerNotifyAdapter extends RecyclerView.Adapter<MyRecyclerNotifyAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<NotifyModel> data;

    public MyRecyclerNotifyAdapter(Context context, ArrayList<NotifyModel> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyRecyclerNotifyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_notify, parent, false);
        return new MyRecyclerNotifyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerNotifyAdapter.ViewHolder holder, int position) {
        holder.txtBy.setText(data.get(position).getPostBy());
        holder.txtDetails.setText(data.get(position).getDetails());
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
        TextView txtBy;
        TextView txtDetails;
        public ViewHolder(View itemView) {
            super(itemView);
            txtBy=itemView.findViewById(R.id.txtBy);
            txtDetails=itemView.findViewById(R.id.txtDetails);



        }
    }
}
