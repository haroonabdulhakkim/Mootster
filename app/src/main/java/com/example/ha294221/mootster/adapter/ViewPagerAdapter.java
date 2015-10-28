package com.example.ha294221.mootster.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ha294221.mootster.AdminDashFragment;
import com.example.ha294221.mootster.CreateMootFragment;
import com.example.ha294221.mootster.EditProfileAdminFragment;
import com.example.ha294221.mootster.FindPeopleFragment;
import com.example.ha294221.mootster.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HA294221 on 10/28/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Connection Requests";
            case 1:return "Connected";
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new HomeFragment();
            case 1:return new FindPeopleFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
