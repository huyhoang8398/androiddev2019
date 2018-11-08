package com.example.huyhoang8398.emailclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class FragmentSocial extends Fragment {
    public static FragmentSocial newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentSocial fragment = new FragmentSocial();
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
                Intent intent = new Intent(getActivity(), New_Mail.class);
                startActivity(intent);
            }
        });
    }
}
