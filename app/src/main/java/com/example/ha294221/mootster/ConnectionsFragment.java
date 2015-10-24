package com.example.ha294221.mootster;

/**
 * Created by HA294221 on 8/29/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConnectionsFragment extends Fragment {

    public ConnectionsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_connections, container, false);

        return rootView;
    }
}

