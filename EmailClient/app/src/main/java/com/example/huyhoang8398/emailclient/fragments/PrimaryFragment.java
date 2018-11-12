package com.example.huyhoang8398.emailclient.fragments;

import android.app.ProgressDialog;
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

import com.example.huyhoang8398.emailclient.R;
import com.example.huyhoang8398.emailclient.activity.DetailActivity;
import com.example.huyhoang8398.emailclient.activity.NewMailActivity;
import com.example.huyhoang8398.emailclient.adapter.RecyclerMailAdapter;
import com.example.huyhoang8398.emailclient.interfaces.APIListener;
import com.example.huyhoang8398.emailclient.interfaces.GmailAPIRequester;
import com.example.huyhoang8398.emailclient.interfaces.MessageSingleton;
import com.example.huyhoang8398.emailclient.interfaces.OnItemClickListener;
import com.google.api.services.gmail.model.Message;

import java.util.ArrayList;
import java.util.List;


public class PrimaryFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private Context context;
    private List<Message> messages;
    RecyclerMailAdapter mailAdapter;

    public PrimaryFragment() {
        messages = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_primary, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_primary_email);

        mailAdapter = new RecyclerMailAdapter(messages);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mailAdapter);

        mailAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Message currentMail = messages.get(position);
                MessageSingleton instance = MessageSingleton.getInstance();
                instance.setMessage(currentMail);
                Intent intent = new Intent(context, DetailActivity.class);
                startActivity(intent);
            }
        });

        GmailAPIRequester api = new GmailAPIRequester(context);
        api.setListener(new APIListener() {
            @Override
            public void onPreExecute() {
                showProgress(true);
            }

            @Override
            public void onRequestSuccess(Object object) {
                showProgress(false);
                messages = (ArrayList<Message>) object;
                mailAdapter.update(messages);
            }

            @Override
            public void onRequestFailure(String message, int errorCode) {

            }
        });
        api.execute();
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


    public void showProgress(boolean status) {
        if (status) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(getString(R.string.message_loading));
                progressDialog.setTitle(getString(R.string.loading));
            }
            progressDialog.show();
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }
}


