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


public class SocialFragment extends Fragment {
    public static SocialFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SocialFragment fragment = new SocialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewPromotion = inflater.inflate(R.layout.fragment_social, container, false);

        return viewPromotion;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton sendBtn = view.findViewById(R.id.sendBtn1);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewMailActivity.class);
                startActivity(intent);
            }
        });
    }
}
