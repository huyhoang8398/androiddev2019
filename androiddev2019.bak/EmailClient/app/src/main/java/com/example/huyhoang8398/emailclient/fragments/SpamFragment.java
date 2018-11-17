package com.example.huyhoang8398.emailclient.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.huyhoang8398.emailclient.activity.NewMailActivity;
import com.example.huyhoang8398.emailclient.R;

public class SpamFragment extends BaseFragment {
    public SpamFragment() {
        nameCurrentFragment = "Spam";


    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        String category = "in:spam";
        api.execute(category);
        return view;
    }
}
