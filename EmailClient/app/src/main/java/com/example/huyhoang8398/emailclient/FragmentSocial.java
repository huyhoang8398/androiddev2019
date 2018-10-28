package com.example.huyhoang8398.emailclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class FragmentSocial extends Fragment {
    public FragmentSocial(){

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewPromotion = inflater.inflate(R.layout.fragment_social, container, false);

        return viewPromotion;
    }
}
