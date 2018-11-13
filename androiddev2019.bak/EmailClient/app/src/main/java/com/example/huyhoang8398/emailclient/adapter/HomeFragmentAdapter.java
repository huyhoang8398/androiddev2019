package com.example.huyhoang8398.emailclient.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.huyhoang8398.emailclient.fragments.PrimaryFragment;
import com.example.huyhoang8398.emailclient.fragments.PromotionFragment;
import com.example.huyhoang8398.emailclient.fragments.SocialFragment;

public class HomeFragmentAdapter extends FragmentStatePagerAdapter {
    private final int PAGE_COUNT = 3;
    private String titles[] = new String[]{"Primary", "Social", "Promotions"};

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT; // number of pages for a ViewPager
    }

    @Override
    public Fragment getItem(int page) {
// returns an instance of Fragment corresponding to the specified page
        switch (page) {
            case 0: {
                return new PrimaryFragment();
            }
            case 1: {
                return new SocialFragment();
            }
            case 2: {
                return new PromotionFragment();
            }
            default: {
                return new PrimaryFragment();

            }
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
// returns a tab title corresponding to the specified page
        return titles[position];
    }
}

