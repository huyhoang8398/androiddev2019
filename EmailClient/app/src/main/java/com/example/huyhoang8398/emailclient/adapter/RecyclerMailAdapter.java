package com.example.huyhoang8398.emailclient.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huyhoang8398.emailclient.R;
import com.example.huyhoang8398.emailclient.interfaces.OnItemClickListener;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;

import java.util.List;

public class RecyclerMailAdapter extends RecyclerView.Adapter<RecyclerMailAdapter.MyViewHolder> {

    private List<Message> emails;

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView subject, description, sender_name;

        public MyViewHolder(View view, OnItemClickListener listener) {
            super(view);
            subject = (TextView) view.findViewById(R.id.subject);
            description = (TextView) view.findViewById(R.id.description);
            sender_name = (TextView) view.findViewById(R.id.sender_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null)
                listener.onItemClick(view, getAdapterPosition());
        }
    }


    public RecyclerMailAdapter(List<Message> emails) {
        this.emails = emails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_list_row, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Message email = emails.get(position);
        List<MessagePartHeader> part = email.getPayload().getHeaders();

        for (int i = 0; i < part.size(); i++) {
            String key = part.get(i).getName();
            switch (key) {
                case "Subject": {
                    holder.subject.setText(part.get(i).getValue());
                    break;
                }
                case "From": {
                    holder.sender_name.setText(part.get(i).getValue());
                    break;
                }
            }
        }

        String fullDescription = email.getSnippet();
        String description = fullDescription.length() > 50 ? fullDescription.substring(0, 49) : fullDescription;
        holder.description.setText(description);
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    public void update(List<Message> email) {
        emails.clear();
        emails.addAll(email);
        notifyDataSetChanged();
    }
}