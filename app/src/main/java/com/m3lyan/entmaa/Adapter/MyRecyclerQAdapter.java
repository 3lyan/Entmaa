package com.m3lyan.entmaa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m3lyan.entmaa.Activity.ReplyQActivity;
import com.m3lyan.entmaa.Activity.TypesDetailsActivity;
import com.m3lyan.entmaa.Model.AddQModel;
import com.m3lyan.entmaa.Model.QuestionsDataModel;
import com.m3lyan.entmaa.R;

import java.util.ArrayList;

public class MyRecyclerQAdapter extends RecyclerView.Adapter<MyRecyclerQAdapter.ViewHolder>{
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<QuestionsDataModel> data;


    public MyRecyclerQAdapter(Context context, ArrayList<QuestionsDataModel> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyRecyclerQAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = mInflater.inflate(R.layout.item_q_header, parent, false);
            return new MyRecyclerQAdapter.ViewHolder(view);
        } else if(viewType == 2) {
            View view = mInflater.inflate(R.layout.item_q_footer, parent, false);
            return new MyRecyclerQAdapter.ViewHolder(view);
        }
        else {
            View view = mInflater.inflate(R.layout.item_q, parent, false);
            return new MyRecyclerQAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(MyRecyclerQAdapter.ViewHolder holder, final int position) {
        holder.txtQ.setText(data.get(position).getPost());
        holder.relativeQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReplyQActivity.class);
                intent.putExtra("q_id",data.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        int type=data.get(position).getPos();
        if (type==0) {
            return 0;
        } else if(type ==2)
            return 2;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQ;
        RelativeLayout relativeQ;
        public ViewHolder(View itemView) {
            super(itemView);
            txtQ=itemView.findViewById(R.id.txtQ);
            relativeQ=itemView.findViewById(R.id.relativeQ);


        }
    }
}
