package com.example.nava.a2003.Invited_To;

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

import com.example.nava.a2003.My_Events.CalendarFragment;
import com.example.nava.a2003.R;

public class OperationActivity extends  AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentMainInvited.OnFragmentInteractionListener,
        FragmentTwo.OnFragmentInteractionListener, FragmentThree.OnFragmentInteractionListener,
        FragmentRsvp.OnFragmentInteractionListener,CalendarFragment.OnFragmentInteractionListener, FragmentMySeat.OnFragmentInteractionListener, FragmentPayment.OnFragmentInteractionListener{
    private Fragment fragment = null;
    private Class fragmentClass = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOperationActivity);
        setSupportActionBar(toolbar);
        //get the id of the event which has been preesed
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String CurrentIdEvnet = bundle.getString("CurrentIdEvnet");
        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            fragmentClass = FragmentMainInvited.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        if (id == R.id.action_settings) {
            fragmentClass = CalendarFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_gallery) {
            fragmentClass = FragmentThree.class;

        } else if (id == R.id.nav_rsvp) {
            fragmentClass = FragmentRsvp.class;
        }
        else if (id == R.id.nav_mySeat) {
            fragmentClass = FragmentMySeat.class;
        } else if (id == R.id.nav_payment) {
            fragmentClass = FragmentPayment.class;
        }
        /* if (id == R.id.nav_calendar) {
            fragmentClass = FragmentTwo.class;
        }
         tools - the user can control notifications before events etc..
        else if (id == R.id.nav_manage) {

        }*/

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
