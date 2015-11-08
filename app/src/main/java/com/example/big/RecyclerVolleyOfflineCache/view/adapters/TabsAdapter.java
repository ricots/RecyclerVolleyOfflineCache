package com.example.big.RecyclerVolleyOfflineCache.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.big.RecyclerVolleyOfflineCache.view.fragments.CardsFragment;
import com.example.big.RecyclerVolleyOfflineCache.view.fragments.FiltersFragment;

/**
 * Created by Vasileios on 6/11/2015.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return CardsFragment.newInstance();
            case 1:
                return FiltersFragment.newInstance();
            case 2:
                return CardsFragment.newInstance();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Cards";
            case 1:
                return "Filters";
            case 2:
                return "Map";
        }
        return "";
    }
}