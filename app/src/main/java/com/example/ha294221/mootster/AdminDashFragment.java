package com.example.ha294221.mootster;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ha294221.mootster.adapter.MootListAdminAdapter;
import com.example.ha294221.mootster.model.MootListItem;
import com.example.ha294221.mootster.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class AdminDashFragment extends Fragment {
    private FloatingActionButton addMootFab;
    private RecyclerView listMootsAdmin;
    private MootListAdminAdapter mootListAdminAdapter;
    private List<MootListItem> mootList;


    public AdminDashFragment() {
        // Fetching the list of moots
        // Have to figure out if this is the right place to make the json call
        mootList=getMootList();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int expPos=-1;
        // Retrieving the position of the expanded item((if any, otherwise -1)) from the bundle
        if(null!=savedInstanceState){
            expPos=savedInstanceState.getInt("ExpandedPos");
        }
        listMootsAdmin.setLayoutManager(new LinearLayoutManager(getActivity()));
        mootListAdminAdapter= new MootListAdminAdapter(getActivity(),mootList,expPos);
        listMootsAdmin.setAdapter(mootListAdminAdapter);
        listMootsAdmin.addItemDecoration(new DividerItemDecoration(getActivity(), null));
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Saving the position of the expanded item (if any, otherwise -1) in the bundle to handle orientation change
        outState.putInt("ExpandedPos", mootListAdminAdapter.getExpandedPos());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Setting the title of the fragment
        getActivity().setTitle(R.string.title_fragment_admin_dash);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_dash, container, false);
        addMootFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        listMootsAdmin = (RecyclerView) rootView.findViewById(R.id.mootListAdmin);

        // onClick(), redirects to create moot fragment
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
            mootListItems.add(item);
        }
       return mootListItems;
    }


}
