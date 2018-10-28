package com.example.huyhoang8398.emailclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class FragmentPromotion extends Fragment {
    public FragmentPromotion(){

    }

        // Inflate the view for the fragment based on layout XML
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View viewPromotion = inflater.inflate(R.layout.fragment_promotion, container, false);

            return viewPromotion;
        }

    public static FragmentPromotion newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentPromotion fragment = new FragmentPromotion();
        fragment.setArguments(args);
        return fragment;
    }


    public static class newInstance extends Fragment {
        
    }
}