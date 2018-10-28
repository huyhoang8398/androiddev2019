package com.example.huyhoang8398.emailclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class FragmentPromotion extends Fragment {
        // Store instance variables
        private String titlePromotion;
        private int pagePromotion;

        // newInstance constructor for creating fragment with arguments
        public static FragmentPromotion newInstance(int page, String title) {
            FragmentPromotion fragmentFirst = new FragmentPromotion();
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
            pagePromotion = getArguments().getInt("Promotion", 0);
            titlePromotion = getArguments().getString("Promotion");
        }

        // Inflate the view for the fragment based on layout XML
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View viewPromotion = inflater.inflate(R.layout.fragment_promotion, container, false);

            return viewPromotion;
        }
    }