package com.example.huyhoang8398.emailclient.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PrimaryFragment extends BaseFragment {
    public PrimaryFragment() {

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        String category = "category:primary";
        api.execute(category);
        return view;
    }
}


