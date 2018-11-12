package com.example.huyhoang8398.emailclient.interfaces;

import android.content.Context;
import android.os.AsyncTask;

import com.example.huyhoang8398.emailclient.R;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

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

public class GmailSendMail extends AsyncTask<Void, Void, List<Message>> {

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
    protected List<Message> doInBackground(Void... voids) {

        String from = "nghiadvcn@gmail.com";
        String to = "nghiadvptit@gmail.com";
        String body = "Test mail";
        String subject = "subject";

        try {
            sendMessage(service, "me", createEmail(to, from, subject, body));
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Message> email) {
        super.onPostExecute(email);
        //listener.onRequestSuccess(email);
    }

    private String sendMessage(Gmail service, String userId, MimeMessage email)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(email);
        // GMail's official method to send email with oauth2.0
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
        return message.getId();
    }

    private MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        InternetAddress tAddress = new InternetAddress(to);
        InternetAddress fAddress = new InternetAddress(from);

        email.setFrom(fAddress);
        email.addRecipient(javax.mail.Message.RecipientType.TO, tAddress);
        email.setSubject(subject);

        // Create Multipart object and add MimeBodyPart objects to this object
        Multipart multipart = new MimeMultipart();

        // Changed for adding attachment and text
        // This line is used for sending only text messages through mail
        // email.setText(bodyText);

        BodyPart textBody = new MimeBodyPart();
        textBody.setText(bodyText);
        multipart.addBodyPart(textBody);

//        if (!(activity.fileName.equals(""))) {
//            // Create new MimeBodyPart object and set DataHandler object to this object
//            MimeBodyPart attachmentBody = new MimeBodyPart();
//            String filename = activity.fileName; // change accordingly
//            DataSource source = new FileDataSource(filename);
//            attachmentBody.setDataHandler(new DataHandler(source));
//            attachmentBody.setFileName(filename);
//            multipart.addBodyPart(attachmentBody);
//        }

        // Set the multipart object to the message object
        email.setContent(multipart);
        return email;
    }

    private Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        email.writeTo(bytes);
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }


}