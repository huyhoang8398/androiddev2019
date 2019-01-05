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
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GmailSendMail extends AsyncTask<String, Void, Message> {

    private APIListener listener;
    private Context context;
    private Gmail service;

    public void setListener(APIListener listener) {
        this.listener = listener;
    }

    public GmailSendMail(Context context) {
        this.context = context;
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
    protected Message doInBackground(String... params) {

        String from = params[0];
        String to = params[1];
        String subject = params[2];
        String body = params[3];


        try {
            MimeMessage mimeMessage = createEmail(to,from,subject,body);
            //Message messageMail = createMessageWithEmail(mimeMessage);
            Draft draft = createDraft(service,"me", mimeMessage);
            Message result = sendMessage(service,"me",mimeMessage);
            return result;

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Message email) {
        super.onPostExecute(email);
        //listener.onRequestSuccess(email);
    }

    public static MimeMessage createEmail(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }


    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedString = new String(Base64.encodeBase64(bytes));
//        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedString);
        return message;
    }

    public  Draft createDraft(Gmail service,
                                    String userId,
                                    MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
//        String userID = "me";
        Draft draft = new Draft();
        draft.setMessage(message);
        draft = service.users().drafts().create(userId, draft).execute();

        System.out.println("Draft id: " + draft.getId());
        System.out.println(draft.toPrettyString());
        return draft;
    }



    public static Message sendMessage(Gmail service,
                                      String userId,
                                      MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        return message;
    }





}