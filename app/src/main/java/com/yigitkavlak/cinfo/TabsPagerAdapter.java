package com.yigitkavlak.cinfo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);          //deprecated
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FuelTab1Fragment();
            case 1:
                return new FuelTab2Fragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "İlan Ekle";
            case 1:
                return "İlan Listele";
            default:
                return null;
        }
    }
}