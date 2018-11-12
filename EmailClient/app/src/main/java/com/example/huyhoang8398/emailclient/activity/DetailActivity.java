package com.example.huyhoang8398.emailclient.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.huyhoang8398.emailclient.R;
import com.example.huyhoang8398.emailclient.interfaces.MessageSingleton;
import com.google.api.services.gmail.model.Message;

public class DetailActivity extends AppCompatActivity {


    TextView tv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MessageSingleton messageSingleton = MessageSingleton.getInstance();
        Message message = messageSingleton.getMessage();

        tv_content = findViewById(R.id.tv_content);
        tv_content.setText(message.getSnippet());

    }
}
