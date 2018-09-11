package com.m3lyan.entmaa.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m3lyan.entmaa.Model.AddQModel;
import com.m3lyan.entmaa.Model.ReplyQDataModel;
import com.m3lyan.entmaa.R;

import java.util.ArrayList;

public class MyRecyclerReplyAdapter extends RecyclerView.Adapter<MyRecyclerReplyAdapter.ViewHolder>{
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<ReplyQDataModel> data;


    public MyRecyclerReplyAdapter(Context context, ArrayList<ReplyQDataModel> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyRecyclerReplyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = mInflater.inflate(R.layout.item_reply_header, parent, false);
            return new MyRecyclerReplyAdapter.ViewHolder(view);
        } else if(viewType == 2) {
            View view = mInflater.inflate(R.layout.item_reply_footer, parent, false);
            return new MyRecyclerReplyAdapter.ViewHolder(view);
        }
        else {
            View view = mInflater.inflate(R.layout.item_reply, parent, false);
            return new MyRecyclerReplyAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(MyRecyclerReplyAdapter.ViewHolder holder, int position) {
        holder.txtQ.setText(data.get(position).getAnswer());
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
        public ViewHolder(View itemView) {
            super(itemView);
            txtQ=itemView.findViewById(R.id.txtQ);


        }
    }
}
