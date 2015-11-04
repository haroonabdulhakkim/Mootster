package com.example.ha294221.mootster.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
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
    private RecyclerView parentRV;


    public ConnectionReqAdminAdapter(Context context,List<ConnectRequestAdminItem> data) {
        layoutInflater=LayoutInflater.from(context);
        reqList=data;

    }

    @Override
    public ConnectionReqAdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.connect_req_item,parent,false);
        ConnectionReqAdminViewHolder viewHolder=new ConnectionReqAdminViewHolder(view);
        parentRV= (RecyclerView) parent;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ConnectionReqAdminViewHolder holder, int position) {
        ColorGenerator generator = ColorGenerator.MATERIAL;

        holder.nameConnectReqAdmin.setText(reqList.get(position).getName());
        String letter = String.valueOf(holder.nameConnectReqAdmin.getText().charAt(0));
        TextDrawable drawable = TextDrawable.builder().buildRound(letter, generator.getRandomColor());
        holder.connReqFirstLetter.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return reqList.size();
    }


    public class ConnectionReqAdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView connReqFirstLetter;
        private TextView nameConnectReqAdmin;
        private ImageView decline_req_admin;
        private ImageView acceptReqAdmin;

        public ConnectionReqAdminViewHolder(View itemView) {
            super(itemView);
            connReqFirstLetter=(ImageView)itemView.findViewById(R.id.conn_req__first_letter);
            nameConnectReqAdmin=(TextView)itemView.findViewById(R.id.name_connect_req_admin);
            decline_req_admin=(ImageView)itemView.findViewById(R.id.decline_req_admin);
            acceptReqAdmin=(ImageView)itemView.findViewById(R.id.accept_req_admin);

            decline_req_admin.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.equals(decline_req_admin)){
                removeAt(getAdapterPosition());
            }
        }

        private void removeAt(int position) {
            try {
                reqList.remove(position);
                notifyItemRemoved(position);
                Snackbar.make(parentRV, "Request Ignored", Snackbar.LENGTH_SHORT).show();
            } catch (Exception e){
                Snackbar.make(parentRV, "Action Failed, Please Try Again!", Snackbar.LENGTH_SHORT).show();
            }

        }
    }
}
