package com.example.ha294221.mootster.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ha294221.mootster.R;
import com.example.ha294221.mootster.adapter.ConnectedAdminAdapter;
import com.example.ha294221.mootster.model.ConnectedAdminItem;
import com.example.ha294221.mootster.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ConnectedFragment extends Fragment {
    private RecyclerView connectedListAdmin;
    private ConnectedAdminAdapter connectedAdminAdapter;
    public ConnectedFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connected, container, false);
        connectedListAdmin= (RecyclerView) rootView.findViewById(R.id.connected_list_admin);
        connectedListAdmin.setLayoutManager(new LinearLayoutManager(getActivity()));
        connectedAdminAdapter= new ConnectedAdminAdapter(getActivity(),getConnectedList());
        connectedListAdmin.setAdapter(connectedAdminAdapter);
        connectedListAdmin.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        return rootView;
    }


    public List<ConnectedAdminItem> getConnectedList() {
        List<ConnectedAdminItem> connectedAdminItems = new ArrayList<>();
        String[] names={"Haroon Abdul Hakkim","Shyam Pillai","Ram Pillai", "Naveed Abdul","Sachin Tendulkar","M S Dhoni","Sourav Ganguly", "Roger Federer","Rossi","Sania Mirza","The Rock","Tyrion Lannister","Khal Drogo"};
        String[] colleges={"IIT Madras","Symbiosis","AAvo", "Rajagiri","Ivideyo","Cool Institute","Dadagiri", "Swiss Bank","Biker Association","Tennis Federation","WWE","King's Landing","Essos"};
        for(int i=0;i <names.length && i <colleges.length; i++){
            ConnectedAdminItem item=new ConnectedAdminItem();
            item.setName(names[i]);
            item.setCollege(colleges[i]);
            connectedAdminItems.add(item);
        }
        return connectedAdminItems;
    }
}
