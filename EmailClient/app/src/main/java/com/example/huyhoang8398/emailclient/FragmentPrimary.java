package com.example.huyhoang8398.emailclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentPrimary extends Fragment {
    public static FragmentPrimary newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentPrimary fragment = new FragmentPrimary();
        fragment.setArguments(args);
        return fragment;
    }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_primary, container, false);
            return view;
        }
}


