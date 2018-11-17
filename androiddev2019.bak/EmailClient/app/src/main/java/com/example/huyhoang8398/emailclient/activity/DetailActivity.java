package com.example.huyhoang8398.emailclient.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import com.example.huyhoang8398.emailclient.R;
import com.example.huyhoang8398.emailclient.interfaces.GmailDeleteMail;
import com.example.huyhoang8398.emailclient.interfaces.MessageSingleton;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;

//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.codec.binary.StringUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView tv_subject, tv_sender, tv_date, tv_receiver;
    private TextView tv_content;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_subject = findViewById(R.id.tv_subject);
        tv_sender = findViewById(R.id.tv_sender_name);
        tv_receiver = findViewById(R.id.tv_receiver_name);
        tv_date = findViewById(R.id.tv_receiver_date);
        tv_content = findViewById(R.id.tv_content);
//        webView = findViewById(R.id.webview);

        /// add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MessageSingleton messageSingleton = MessageSingleton.getInstance();
        Message message = messageSingleton.getMessage();

        List<MessagePartHeader> partHeader = message.getPayload().getHeaders();
        for (int i = 0; i < partHeader.size(); i++) {
            String key = partHeader.get(i).getName();
            switch (key) {
                case "Subject": {
                    tv_subject.setText(partHeader.get(i).getValue());
                    break;
                }
                case "From": {
                    tv_sender.setText(partHeader.get(i).getValue());
                    break;
                }
                case "To": {
                    tv_receiver.setText(partHeader.get(i).getValue());
                    break;
                }
                case "Date": {
                    tv_date.setText(partHeader.get(i).getValue());
                }
            }
        }


//get webview instead of text view body content
//
//        String mailBody = "";
//        String mimeType = message.getPayload().getMimeType();
//        List<MessagePart> part2 = message.getPayload().getParts();
//        if (mimeType.contains("alternative")) {
//            for (MessagePart part : part2) {
//                mailBody = new String(Base64.decodeBase64(part.getBody().getData().getBytes()));
//            }
//        }


        String content = message.getSnippet();
        tv_content.setText(content);

        // get webview
//        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
//        webView.loadData(header+mailBody, "text/html", "UTF-8");
////
//
//
// webView.loadData(mailBody, "text/html; charset=UTF-8", null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        switch (item.getItemId()) {
            case R.id.action_delete:
                delMail();

        }
        return super.onOptionsItemSelected(item);
    }

    private void delMail() {
        MessageSingleton messageSingleton = MessageSingleton.getInstance();
        Message message = messageSingleton.getMessage();
        String msgID =  message.getId(); // phai lay duoc tu trong message dang chon. no co trong message day
        new GmailDeleteMail(this).execute(msgID);

    }
}
