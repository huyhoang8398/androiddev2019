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

    ViewPager viewPager;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View viewAllMail = inflater.inflate(R.layout.fragment_allmail, container, false);

        viewPager = viewAllMail.findViewById(R.id.viewpager);
        tabLayout = viewAllMail.findViewById(R.id.tablayout);
        HomeFragmentAdapter homeFragmentAdapter = new HomeFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(homeFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setTabsFromPagerAdapter(homeFragmentAdapter);
        return  viewAllMail;

    }



}
