package com.example.huyhoang8398.emailclient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class NewMailActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    MenuItem attachment;
    Uri imageUri;
    public NewMailActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mail);

        // edit action bar
        ActionBar newMailBar = getSupportActionBar();
        newMailBar.setTitle("New Mail");


        //get menu item

        /// add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    /// add back button


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        switch (item.getItemId()) {
            case R.id.attach_img:
                openGallery();

                break;
        }

        return super.onOptionsItemSelected(item);
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
