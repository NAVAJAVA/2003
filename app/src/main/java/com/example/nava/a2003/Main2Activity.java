package com.example.nava.a2003;

import android.app.Dialog;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
     SectionsPagerAdapter mSectionsPagerAdapter;
     ViewPager mViewPager;



    /**
     * The {@link ViewPager} that will host the section contents.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarActivity_main2);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_activity_main2);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_main2) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        ListView listViewArtists;
        EditText editTextName;
        EditText dateTxt;
        EditText timeTxt;

        //a list to store all the artist from firebase database
        List<Artist> artists;

        //our database reference object
        DatabaseReference databaseArtists;


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_main3, container, false);
            //getting the reference of artists node
            databaseArtists = FirebaseDatabase.getInstance().getReference("artists");
            //getting views
            listViewArtists = (ListView) rootView.findViewById(R.id.listViewArtists);

            //list to store artists
            artists = new ArrayList<>();
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
                    fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 displayInputDialog();
             }
         });


           // TextView textView = (TextView) rootView.findViewById(R.id.section_label_main2);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }

        private void displayInputDialog() {
            final Dialog d = new Dialog(getContext());
            d.setContentView(R.layout.input_dialog);

            editTextName = (EditText) d.findViewById(R.id.nameEditText);
            dateTxt = (EditText) d.findViewById(R.id.dateEditText);
            timeTxt = (EditText) d.findViewById(R.id.timeEditText);
            final Button saveBtn = (Button) d.findViewById(R.id.saveBtn);

            //SAVE
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SIMPLE VALIDATION
                    String name = editTextName.getText().toString().trim();
                    if (name != null && name.length() > 0) {
                        addArtist();
                        editTextName.setText("");
                        timeTxt.setText("");
                        dateTxt.setText("");
                        d.hide();

                    }
                    else
                    {
                        Toast.makeText(getContext(), "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();

                    }
                    //GET DATA
                    //

             //       String date = dateTxt.getText().toString();
               //     String time= timeTxt.getText().toString();

                    //SET DATA
                    /*
                    Event event = new Event();
                    event.setName(name);
                    event.setDate(date);
                    event.setTime(time);

                    //SIMPLE VALIDATION
                    if (name != null && name.length() > 0) {
                        //THEN SAVE
                        if (helper.save(event)) {
                            //IF SAVED CLEAR EDITXT
                            nameEditTxt.setText("");
                            timeTxt.setText("");
                            dateTxt.setText("");
                            d.hide();
                            adapter = new CustomAdapter(getContext(), helper.retrieve());
                            lv.setAdapter(adapter);
                        }
                    } else {
                        Toast.makeText(getContext(), "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                    }
*/
                }
            });

            d.show();
        }
        private void addArtist() {
            //getting the values to save
            String name = editTextName.getText().toString().trim();

            //checking if the value is provided
            if (!TextUtils.isEmpty(name)) {

                //getting a unique id using push().getKey() method
                //it will create a unique id and we will use it as the Primary Key for our Artist
                String id = databaseArtists.push().getKey();

                //creating an Artist Object
                Artist artist = new Artist(id, name);

                //Saving the Artist
                databaseArtists.child(id).setValue(artist);

                //setting edittext to blank again
                editTextName.setText("");

                //displaying a success toast
                Toast.makeText(getActivity(), "Artist added", Toast.LENGTH_LONG).show();
            } else {
                //if the value is not given displaying a toast
                Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onStart() {
            super.onStart();
            //attaching value event listener
            databaseArtists.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //clearing the previous artist list
                    artists.clear();

                    //iterating through all the nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting artist
                        Artist artist = postSnapshot.getValue(Artist.class);
                        //adding artist to the list
                        artists.add(artist);
                    }

                    //creating adapter
                    ArtistList artistAdapter = new ArtistList(getActivity(), artists);
                    //attaching adapter to the listview
                    listViewArtists.setAdapter(artistAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Invited to";
                case 1:
                    return "My events";
            }
            return null;
        }
    }
}
