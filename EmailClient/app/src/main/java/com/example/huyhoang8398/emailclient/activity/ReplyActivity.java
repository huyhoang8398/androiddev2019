package com.example.huyhoang8398.emailclient.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huyhoang8398.emailclient.R;
import com.example.huyhoang8398.emailclient.interfaces.GmailDraft;
import com.example.huyhoang8398.emailclient.interfaces.GmailSendMail;

public class ReplyActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    MenuItem attachment;
    Uri imageUri;

    EditText ed_from, ed_to, ed_subject, ed_content;

    public ReplyActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mail);

        ed_from = findViewById(R.id.new_mail_from);
        ed_content =findViewById(R.id.new_mail_compose);
        ed_to = findViewById(R.id.new_mail_to);
        ed_subject = findViewById(R.id.new_mail_subject);

        Intent intent = this.getIntent();

        String to = intent.getStringExtra("to");
        String from = intent.getStringExtra("from");
        String subject = intent.getStringExtra("subject");
        String body = intent.getStringExtra("body");

        ed_content.setText(body);
        ed_to.setText(to);
        ed_from.setText(from);
        ed_subject.setText(subject);

        // edit action bar
        ActionBar newMailBar = getSupportActionBar();
        newMailBar.setTitle("New Mail");


        //get menu item

        /// add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
    /// add back button


    private void sendMail(){
        String from = ed_from.getText().toString();
        String to = ed_to.getText().toString();
        String subject = ed_subject.getText().toString();
        String content = ed_content.getText().toString();


        if (to.matches("") || from.matches("") || content.matches("") || subject.matches("")) {
            Toast.makeText(this, "You have to input full information", Toast.LENGTH_SHORT).show();
        } else {

            new GmailSendMail(this).execute(to, from, subject, content);
            Toast.makeText(this, "The message has been sent", Toast.LENGTH_SHORT).show();


        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            createDraft();
            this.finish();
        }
        switch (item.getItemId()) {
            case R.id.attach_img:
                openGallery();
                break;

            case R.id.send:
                sendMail();
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDraft(){
        String from = ed_from.getText().toString();
        String to = ed_to.getText().toString();
        String subject = ed_subject.getText().toString();
        String content = ed_content.getText().toString();


        new GmailDraft(this).execute(to,from,subject,content);
        Toast.makeText(this, "The message has been saved to draft", Toast.LENGTH_SHORT).show();


    }
    // open gallery
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_mail, menu);

        return true;

    }

}
