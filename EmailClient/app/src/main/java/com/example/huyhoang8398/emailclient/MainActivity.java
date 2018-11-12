package com.example.huyhoang8398.emailclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huyhoang8398.emailclient.fragments.AllMailFragment;
import com.example.huyhoang8398.emailclient.fragments.SendFragment;
import com.example.huyhoang8398.emailclient.fragments.SpamFragment;
import com.example.huyhoang8398.emailclient.fragments.TrashFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    MaterialSearchView searchView;
    ImageView ProfPicNav;
    TextView NameNav, EmailNav;
    GoogleApiClient mGoogleApiClient;

    private static final String[] SCOPES = {GmailScopes.GMAIL_READONLY};

    @Override
    protected void onStart() {
        //googleAuthen();
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        System.out.println();

        new MyTask().execute();

    }


    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String[] SCOPES = {
                        GmailScopes.GMAIL_LABELS,
                        GmailScopes.GMAIL_COMPOSE,
                        GmailScopes.GMAIL_INSERT,
                        GmailScopes.GMAIL_MODIFY,
                        GmailScopes.GMAIL_READONLY,
                        GmailScopes.MAIL_GOOGLE_COM
                };

                GoogleAccountCredential mCredential = GoogleAccountCredential.usingOAuth2(
                        getApplicationContext(), Arrays.asList(SCOPES))
                        .setBackOff(new ExponentialBackOff());

                mCredential.setSelectedAccount(mCredential.getAllAccounts()[0]);

                HttpTransport transport = AndroidHttp.newCompatibleTransport();
                JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
                Gmail service = new com.google.api.services.gmail.Gmail.Builder(
                        transport, jsonFactory, mCredential)
                        .setApplicationName(getResources().getString(R.string.app_name))
                        .build();


                List<Message> messages = listMessagesMatchingQuery(service, "me", "");

                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        public List<Message> listMessagesMatchingQuery(Gmail service, String userId, String query) throws IOException {
            ListMessagesResponse response = service.users().messages().list(userId).execute();

            List<Message> messages = new ArrayList<Message>();
            while (response.getMessages() != null) {
                messages.addAll(response.getMessages());
                if (response.getNextPageToken() != null) {
                    String pageToken = response.getNextPageToken();
                    response = service.users().messages().list(userId).setQ(query).setPageToken(pageToken).execute();
                } else {
                    break;
                }
            }
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        searchView = findViewById(R.id.search_view);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        NameNav = (TextView) headerView.findViewById(R.id.prof_name_nav);
        EmailNav = (TextView) headerView.findViewById(R.id.prof_email_nav);
        ProfPicNav = (ImageView) headerView.findViewById(R.id.prof_pic_nav);

        Intent intent = this.getIntent();

        String nameNav = intent.getStringExtra("name");
        String emailNav = intent.getStringExtra("email");
        String imgUrlNav = intent.getStringExtra("img_prof");
        Glide.with(this).load(imgUrlNav).into(ProfPicNav);


        NameNav.setText(nameNav);
        EmailNav.setText(emailNav);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportActionBar().setTitle("All inboxes");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllMailFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_email);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_sent:
                getSupportActionBar().setTitle("Sent");

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SendFragment()).commit();
                break;
            case R.id.nav_email:
                getSupportActionBar().setTitle("All inboxes");

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AllMailFragment()).commit();
                break;
            case R.id.nav_spam:
                getSupportActionBar().setTitle("Spam");

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SpamFragment()).commit();
                break;
            case R.id.nav_trash:
                getSupportActionBar().setTitle("Trash");

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TrashFragment()).commit();
                break;


            case R.id.sign_out:
                signOut();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                        mGoogleApiClient.clearDefaultAccountAndReconnect();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer((GravityCompat.START));
        } else {
            super.onBackPressed();
        }
    }


}
