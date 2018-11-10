package com.example.huyhoang8398.emailclient;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawer;
    MaterialSearchView searchView;
    ImageView ProfPicNav;
    TextView NameNav, EmailNav;


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

        String nameNav= intent.getStringExtra("name");
        String emailNav = intent.getStringExtra("email");
        String imgUrlNav = intent.getStringExtra("img_prof");
        Glide.with(this).load(imgUrlNav).into(ProfPicNav);


        NameNav.setText(nameNav);
        EmailNav.setText(emailNav);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportActionBar().setTitle("All inboxes");

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AllMailFragment()).commit();
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
        switch (menuItem.getItemId()){
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
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer((GravityCompat.START));
        }
        else {
            super.onBackPressed();
        }
    }


}
