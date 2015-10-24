package com.example.ha294221.mootster;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ha294221.mootster.adapter.MootListAdminAdapter;
import com.example.ha294221.mootster.model.MootListItem;

import java.util.ArrayList;
import java.util.List;


public class AdminDashFragment extends Fragment {
    private FloatingActionButton addMootFab;
    private RecyclerView listMootsAdmin;
    private MootListAdminAdapter mootListAdminAdapter;



    public AdminDashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
           }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_dash, container, false);
        addMootFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        listMootsAdmin = (RecyclerView) rootView.findViewById(R.id.mootListAdmin);
        listMootsAdmin.setLayoutManager(new LinearLayoutManager(getActivity()));
        mootListAdminAdapter= new MootListAdminAdapter(getActivity(),getMootList());
        listMootsAdmin.setAdapter(mootListAdminAdapter);
        setRetainInstance(true);
        addMootFab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                // Create new fragment and transaction
                Fragment newCreateMootFragment = new CreateMootFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_admin_container, newCreateMootFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        return rootView;
    }

    private static List<MootListItem> getMootList(){
        List<MootListItem> mootListItems=new ArrayList<>();
        String[] titles={"Surana&Surana","NIRC","Stetson","Jessup","Surana&Surana","NIRC","Stetson","Jessup","Surana&Surana","NIRC","Stetson","Jessup","Surana&Surana","NIRC","Stetson","Jessup"};
        String[] dates={"23 Aug 2015","30 July 2015", "29 Nov 2015", "Jan 2016","23 Aug 2015","30 July 2015", "29 Nov 2015", "Jan 2016","23 Aug 2015","30 July 2015", "29 Nov 2015", "Jan 2016","23 Aug 2015","30 July 2015", "29 Nov 2015", "Jan 2016"};
        String[] venues={"Pune"," New Delhi","Patiala", "Bengaluru","Pune"," New Delhi","Patiala", "Bengaluru","Pune"," New Delhi","Patiala", "Bengaluru","Pune"," New Delhi","Patiala", "Bengaluru"};
        for(int i=0;i <titles.length && i < dates.length && i < venues.length; i++){
            MootListItem item=new MootListItem();
            item.setMootTitle(titles[i]);
            item.setMootDate(dates[i]);
            item.setVenue(venues[i]);
            item.setExpanded(false);
            mootListItems.add(item);
        }
       return mootListItems;
    }


}
