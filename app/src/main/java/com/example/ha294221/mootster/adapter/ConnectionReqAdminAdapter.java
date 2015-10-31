package com.example.ha294221.mootster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ha294221.mootster.R;
import com.example.ha294221.mootster.model.ConnectRequestAdminItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by HA294221 on 10/29/2015.
 */
public class ConnectionReqAdminAdapter extends RecyclerView.Adapter<ConnectionReqAdminAdapter.ConnectionReqAdminViewHolder> {
    private LayoutInflater layoutInflater;
    private List<ConnectRequestAdminItem> reqList= Collections.EMPTY_LIST;

    public ConnectionReqAdminAdapter(Context context,List<ConnectRequestAdminItem> data) {
        layoutInflater=LayoutInflater.from(context);
        reqList=data;
    }

    @Override
    public ConnectionReqAdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.connect_req_item,parent,false);
        ConnectionReqAdminViewHolder viewHolder=new ConnectionReqAdminViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ConnectionReqAdminViewHolder holder, int position) {
                holder.nameConnectReqAdmin.setText(reqList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return reqList.size();
    }

    public class ConnectionReqAdminViewHolder extends RecyclerView.ViewHolder{

        private TextView nameConnectReqAdmin;
        private ImageView decline_req_admin;
        private ImageView acceptReqAdmin;

        public ConnectionReqAdminViewHolder(View itemView) {
            super(itemView);
            nameConnectReqAdmin=(TextView)itemView.findViewById(R.id.name_connect_req_admin);
            decline_req_admin=(ImageView)itemView.findViewById(R.id.decline_req_admin);
            acceptReqAdmin=(ImageView)itemView.findViewById(R.id.accept_req_admin);
        }
    }
}
