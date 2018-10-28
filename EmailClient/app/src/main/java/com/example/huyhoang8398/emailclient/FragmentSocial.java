package com.example.huyhoang8398.emailclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class FragmentSocial extends Fragment {
    // Store instance variables
    private String titleSocial;
    private int pageSocial;

    // newInstance constructor for creating fragment with arguments
    public static FragmentSocial newInstance(int page, String title) {
        FragmentSocial fragmentFirst = new FragmentSocial();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageSocial = getArguments().getInt("Social", 0);
        titleSocial = getArguments().getString("Social");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewPromotion = inflater.inflate(R.layout.fragment_social, container, false);

        return viewPromotion;
    }
}
