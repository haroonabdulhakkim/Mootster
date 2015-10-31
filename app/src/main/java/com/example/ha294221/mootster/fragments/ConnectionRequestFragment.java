package com.example.ha294221.mootster.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ha294221.mootster.R;
import com.example.ha294221.mootster.adapter.ConnectionReqAdminAdapter;
import com.example.ha294221.mootster.model.ConnectRequestAdminItem;

import java.util.ArrayList;
import java.util.List;

public class ConnectionRequestFragment extends Fragment {
    private RecyclerView connectReqListAdmin;
    private ConnectionReqAdminAdapter connectionReqAdminAdapter;

    public ConnectionRequestFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_connection_request, container, false);
        connectReqListAdmin = (RecyclerView) rootView.findViewById(R.id.connect_req_list_admin);
        connectReqListAdmin.setLayoutManager(new LinearLayoutManager(getActivity()));
        connectionReqAdminAdapter = new ConnectionReqAdminAdapter(getActivity(),getConnReqList());
        connectReqListAdmin.setAdapter(connectionReqAdminAdapter);
        return rootView;
    }

    private List<ConnectRequestAdminItem> getConnReqList() {
        List<ConnectRequestAdminItem> connReqListItems=new ArrayList<>();
        String[] names={"Haroon Abdul Hakkim"," Shyam Pillai","Ram Pillai", "Naveed Abdul","Sachin Tendulkar"," M S Dhoni","Sourav Ganguly", "Roger Federer","Rossi","Sania Mirza"};
        for(int i=0;i <names.length; i++){
            ConnectRequestAdminItem item=new ConnectRequestAdminItem();
            item.setName(names[i]);
            connReqListItems.add(item);
        }
        return connReqListItems;
    }
}
