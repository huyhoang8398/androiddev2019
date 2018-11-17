package com.example.huyhoang8398.emailclient.interfaces;

import android.content.Context;
import android.os.AsyncTask;

import com.example.huyhoang8398.emailclient.R;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GmailAPIRequester extends AsyncTask<String, Void, List<Message>> {

    private APIListener listener;
    private Context context;
    private Gmail service;
    public void setListener(APIListener listener) {
        this.listener = listener;
    }
    public GmailAPIRequester(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPreExecute();

        String[] SCOPES = {
                GmailScopes.GMAIL_LABELS,
                GmailScopes.GMAIL_COMPOSE,
                GmailScopes.GMAIL_INSERT,
                GmailScopes.GMAIL_MODIFY,
                GmailScopes.GMAIL_READONLY,
                GmailScopes.MAIL_GOOGLE_COM
        };

        GoogleAccountCredential mCredential = GoogleAccountCredential.usingOAuth2(
                context, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        mCredential.setSelectedAccount(mCredential.getAllAccounts()[0]);

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        service = new com.google.api.services.gmail.Gmail.Builder(
                transport, jsonFactory, mCredential)
                .setApplicationName(context.getResources().getString(R.string.app_name))
                .build();

    }

    @Override
    protected List<Message> doInBackground(String... param) {
        String category = param[0];

        try {
            List<Message> messages = listMessagesMatchingQuery("me", category);
            List<Message> emails = new ArrayList<>();
            for (Message message : messages) {
                emails.add(getEmailFormMessage(message));
            }
            return emails;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Message> email) {
        super.onPostExecute(email);
        listener.onRequestSuccess(email);
    }

    private Message getEmailFormMessage(Message message) {
        try {
            Message messageDetail = service.users().messages().get("me", message.getId()).execute();
            ArrayList<String> contents = new ArrayList<>();
            MessagePart part = messageDetail.getPayload();
            for (int i = 0; i < part.getHeaders().size(); i++) {
                contents.add(part.getHeaders().get(i).getName() + " -- " + part.getHeaders().get(i).getValue());
            }
            return messageDetail;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> listMessagesMatchingQuery(String userId, String query) throws IOException {
        long maxResult = 3;
        ListMessagesResponse response = service.users().messages().list(userId).setQ(query)
                .setMaxResults(maxResult).execute();
        List<Message> messages = new ArrayList<Message>();
        messages.addAll(response.getMessages());
        return messages;
    }





}