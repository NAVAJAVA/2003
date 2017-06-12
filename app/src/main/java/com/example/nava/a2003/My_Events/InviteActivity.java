package com.example.nava.a2003.My_Events;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.nava.a2003.Invited_To.GalleryFragment;
import com.example.nava.a2003.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InviteActivity extends  AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GuestsFragment.OnFragmentInteractionListener,
        FragmentUploadImage.OnFragmentInteractionListener,CalendarFragment.OnFragmentInteractionListener,
        MainInviteFragment.OnFragmentInteractionListener,EditFragment.OnFragmentInteractionListener,
        SeatsFragment.OnFragmentInteractionListener{
   private Fragment fragment = null;
   private Class fragmentClass = null;
    DatabaseReference databaseEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        databaseEvents = FirebaseDatabase.getInstance().getReference("Events");
        //gets the currentIdEvent from customAdapter
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String CurrentIdEvnet = bundle.getString("CurrentIdEvnet");
        //send  CurrentIdEvnet to MainIvitedFragment
        getIntent().putExtra("CurrentIdEvnet", CurrentIdEvnet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInvited);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = MainInviteFragment.class;

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContentInvite, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutInvited);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewInvited);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutInvited);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.operation, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Calendar) {
           // FirebaseAuth.getInstance().signOut();

            fragmentClass = CalendarFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContentInvite, fragment).commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_guests) {
            fragmentClass = GuestsFragment.class;
        }   else if (id == R.id.nav_gallery) {
            fragmentClass = FragmentUploadImage.class;
        }   else if (id == R.id.nav_seats) {
            fragmentClass = SeatsFragment.class;
        } else if (id == R.id.nav_edit) {
            fragmentClass = EditFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContentInvite, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutInvited);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

