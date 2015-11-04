package com.example.ha294221.mootster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ha294221.mootster.R;
import com.example.ha294221.mootster.model.ConnectedAdminItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by HA294221 on 11/1/2015.
 */
public class ConnectedAdminAdapter extends RecyclerView.Adapter<ConnectedAdminAdapter.ConnectedAdminViewHolder> {
    private LayoutInflater layoutInflater;
    private List<ConnectedAdminItem> reqList= Collections.EMPTY_LIST;

    public ConnectedAdminAdapter(Context context,List<ConnectedAdminItem> data) {
        layoutInflater=LayoutInflater.from(context);
        reqList=data;
    }
    @Override
    public ConnectedAdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.connected_admin_item,parent,false);
        ConnectedAdminViewHolder viewHolder=new ConnectedAdminViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ConnectedAdminViewHolder holder, int position) {
        holder.connectedNameAdmin.setText(reqList.get(position).getName());
        holder.collegeConnListAdmin.setText(reqList.get(position).getCollege());
    }

    @Override
    public int getItemCount() {
        return reqList.size();
    }

    public class ConnectedAdminViewHolder  extends RecyclerView.ViewHolder{
        private ImageView connectedAdminImg;
        private TextView connectedNameAdmin;
        private TextView collegeConnListAdmin;

        public ConnectedAdminViewHolder(View itemView) {
            super(itemView);
            connectedAdminImg=(ImageView)itemView.findViewById(R.id.connected_admin_img);
            connectedNameAdmin=(TextView)itemView.findViewById(R.id.connected_name_admin);
            collegeConnListAdmin=(TextView)itemView.findViewById(R.id.college_conn_list_admin);
        }
    }
}
