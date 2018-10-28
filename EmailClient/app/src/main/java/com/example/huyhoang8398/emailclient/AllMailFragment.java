package com.example.huyhoang8398.emailclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class AllMailFragment extends Fragment {

    public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        private final int PAGE_COUNT = 4;
        private String titles[] = new String[] { "Primary", "Social", "Promotions" , "All mail"};
        public HomeFragmentPagerAdapter(FragmentManager fm) {
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
                case 0: return new AllMailFragment();
                case 1: return new FragmentPrimary();
                case 2: return new FragmentSocial();
                case 3: return new FragmentPromotion();
            }
            return new AllMailFragment(); // failsafe
        }
        @Override
        public CharSequence getPageTitle(int position) {
// returns a tab title corresponding to the specified page
            return titles[position];
        }
    }


    ViewPager viewPager;
    TabLayout tabLayout;
    FragmentPagerAdapter adapterViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View viewAllMail = inflater.inflate(R.layout.fragment_allmail, container, false);
        adapterViewPager = new HomeFragmentPagerAdapter(getFragmentManager());

        viewPager = (ViewPager) viewPager.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapterViewPager);
        tabLayout = (TabLayout) viewPager.findViewById(R.id.tablayout);
        return  viewAllMail;

    }



}
