package com.example.huyhoang8398.emailclient.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.huyhoang8398.emailclient.R;
import com.example.huyhoang8398.emailclient.interfaces.MessageSingleton;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView tv_subject, tv_sender, tv_date, tv_receiver;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_subject = findViewById(R.id.tv_subject);
        tv_sender = findViewById(R.id.tv_sender_name);
        tv_receiver = findViewById(R.id.tv_receiver_name);
        tv_date = findViewById(R.id.tv_receiver_date);
        tv_content = findViewById(R.id.tv_content);

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
            }
        }

        String content = message.getSnippet();
        tv_content.setText(content);

    }
}
