package com.example.huyhoang8398.emailclient.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huyhoang8398.emailclient.R;
import com.example.huyhoang8398.emailclient.model.Email;

import java.util.List;

public class RecyclerMailAdapter extends RecyclerView.Adapter<RecyclerMailAdapter.MyViewHolder> {

    private List<Email> emails;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView subject, description, sender_name;

        public MyViewHolder(View view) {
            super(view);
            subject = (TextView) view.findViewById(R.id.subject);
            description = (TextView) view.findViewById(R.id.description);
            sender_name = (TextView) view.findViewById(R.id.sender_name);
        }
    }


    public RecyclerMailAdapter(List<Email> emails) {
        this.emails = emails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Email email = emails.get(position);
        holder.subject.setText(email.getSubject());
        holder.sender_name.setText(email.getSenderName());
        holder.description.setText(email.getDescription());
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }
}