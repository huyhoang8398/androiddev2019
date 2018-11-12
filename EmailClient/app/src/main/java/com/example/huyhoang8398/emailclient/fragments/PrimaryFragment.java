package com.example.huyhoang8398.emailclient.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.huyhoang8398.emailclient.NewMailActivity;
import com.example.huyhoang8398.emailclient.R;
import com.example.huyhoang8398.emailclient.adapter.RecyclerMailAdapter;
import com.example.huyhoang8398.emailclient.model.Email;

import java.util.ArrayList;
import java.util.List;


public class PrimaryFragment extends Fragment {
    private RecyclerView recyclerView;

    public PrimaryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_primary, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_primary_email);
        List<Email> emails = new ArrayList<>();
        RecyclerMailAdapter mailAdapter = new RecyclerMailAdapter(emails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mailAdapter);

        return view;
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


