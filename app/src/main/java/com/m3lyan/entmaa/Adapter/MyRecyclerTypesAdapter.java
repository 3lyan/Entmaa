package com.m3lyan.entmaa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.m3lyan.entmaa.Activity.TypesActivity;
import com.m3lyan.entmaa.Activity.TypesDetailsActivity;
import com.m3lyan.entmaa.Model.CategoriesModel;
import com.m3lyan.entmaa.Model.TypesDataDetailsModel;
import com.m3lyan.entmaa.Model.TypesDataModel;
import com.m3lyan.entmaa.Model.TypesModel;
import com.m3lyan.entmaa.R;

import java.util.ArrayList;

public class MyRecyclerTypesAdapter  extends RecyclerView.Adapter<MyRecyclerTypesAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<TypesDataModel> data;

    public MyRecyclerTypesAdapter(Context context, ArrayList<TypesDataModel> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyRecyclerTypesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_types, parent, false);
        return new MyRecyclerTypesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyRecyclerTypesAdapter.ViewHolder holder, final int position) {
        holder.txtTitle.setText(data.get(position).getName());
        Glide.with(context).load(data.get(position).getImage()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                holder.relativeCat.setBackground(resource);
            }
        });
        holder.relativeCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TypesDetailsActivity.class);
                intent.putExtra("type_id",data.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

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
        RelativeLayout relativeCat;
        TextView txtTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            relativeCat=itemView.findViewById(R.id.relativeTypes);
            txtTitle=itemView.findViewById(R.id.txtTitle);



        }
    }
}
