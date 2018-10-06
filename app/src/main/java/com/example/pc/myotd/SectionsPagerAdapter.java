package com.example.pc.myotd;

//QUESTA CLASSE SERVE PER GESTIRE L'ADAPTER PER LO SWIPE TRA FRAGMENT

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)  {
        if(position==0) {
            return new Otd_fragment();
        }
        if(position==1) {
            return new Armadio_fragment();
        }
        else return null;
    }
    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "OTD";
            case 1:
                return "ARMADIO";

        }
        return null;
    }
}