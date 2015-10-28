package com.example.ha294221.mootster;

/**
 * Created by HA294221 on 8/29/2015.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ha294221.mootster.adapter.ViewPagerAdapter;

public class ConnectionsFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public ConnectionsFragment(){}

    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_connections, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.connection_view_pager);
        viewPager=setUpViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.connection_tab);
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }


    private ViewPager setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        Log.e("Adapter", adapter.toString());
        viewPager.setAdapter(adapter);
        return viewPager;

    }
}

