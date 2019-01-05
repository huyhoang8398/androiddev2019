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
import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import java.util.Arrays;

public class GmailDeleteMailForever extends AsyncTask<String, Void, Void> {
    private APIListener listener;
    private Context context;
    private Gmail service;

    public GmailDeleteMailForever(Context context) {
        this.context = context;
    }

    public static void untrashMessage(Gmail service, String userId, String msgId)
            throws IOException {
        service.users().messages().untrash(userId, msgId).execute();
        System.out.println("Message with id: " + msgId + " has been untrashed.");
    }



    public void setListener(APIListener listener) {
        this.listener = listener;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        listener.onPreExecute();

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
        service = new Gmail.Builder(
                transport, jsonFactory, mCredential)
                .setApplicationName(context.getResources().getString(R.string.app_name))
                .build();

    }

    @Override
    protected Void doInBackground(String... strings) {
        String msgID = strings[0];
        String userId = "me";
        try {
            untrashMessage(service,userId,msgID);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
