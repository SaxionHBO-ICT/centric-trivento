package com.trivento.deventerkroegenapp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.trivento.deventerkroegenapp.R;
import com.trivento.deventerkroegenapp.model.Kroeg;
import com.trivento.deventerkroegenapp.model.KroegData;
import com.trivento.deventerkroegenapp.tasks.CategoryTask;
import com.trivento.deventerkroegenapp.util.Reference;

public class MainKroegListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private KroegData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = KroegData.getInstance();

        setContentView(R.layout.activity_main_kroeg_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Resources res = getResources();
        String usernameNavBar = res.getString(R.string.user_not_logged_in);

        //TODO check if user logged in here and edit username
        //WARNING https://code.google.com/p/android/issues/detail?id=190226 Headerview may not work as expected...
        View header = navigationView.getHeaderView(0);

        CategoryTask categoryTask = new CategoryTask(navigationView.getMenu());
        categoryTask.execute();

        TextView tvNavUser = (TextView) header.findViewById(R.id.tv_nav_user);
        tvNavUser.setText(usernameNavBar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d(Reference.TAG, "onNavigationItemSelected: id: " + id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (id) {
            case R.id.nav_login:
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
                break;
            case 1:
                KroegData.searchData();
                break;
            default:
                KroegData.searchData(item.getTitle().toString());
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}