package com.example.nava.a2003;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nava on 22/05/2017.
 */

public class ListEvent extends Fragment {


    ListView listViewEvents;
    EditText editTextName;
    EditText dateTxt;
    EditText timeTxt;
    //a list to store all the events from firebase database
    List<Event> evnets;
    //our database reference object
    DatabaseReference databaseEvents;


    public ListEvent() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_show_events, container, false);
        //getting the reference of artists node
        databaseEvents = FirebaseDatabase.getInstance().getReference("My Events");
        //getting views
        listViewEvents = (ListView) rootView.findViewById(R.id.listViewEvents);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);

        //list to store artists
        evnets = new ArrayList<>();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
            }
        });

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
                String time = timeTxt.getText().toString().trim();
                String date = dateTxt.getText().toString().trim();
                //  String desc = descriptionTxt.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(time)
                        && !TextUtils.isEmpty(date)) {
                    addEvent();
                    editTextName.setText("");
                    timeTxt.setText("");
                    dateTxt.setText("");
                    //  descriptionTxt.setText("");
                    d.hide();

                }
                else
                {
                    Toast.makeText(getContext(), "Must enter name", Toast.LENGTH_SHORT).show();
                }

            }
        });

        d.show();
    }
    private void addEvent() {
        //getting the values to save
        String name = editTextName.getText().toString().trim();
        String time = timeTxt.getText().toString().trim();
        String date = dateTxt.getText().toString().trim();
        // String desc = descriptionTxt.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(time)
                && !TextUtils.isEmpty(date) ) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseEvents.push().getKey();

            //creating an Event Object

            Event event = new Event();
            event.setName(name);
            event.setDate(date);
            event.setTime(time);
            //event.setDescription(desc);


            //Saving the Artist
            databaseEvents.child(id).setValue(event);


            //displaying a success toast
            Toast.makeText(getActivity(), "Event added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                evnets.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Event event = postSnapshot.getValue(Event.class);
                    //adding artist to the list
                    evnets.add(event);
                }
                //if not good just cange art to event addpterererush
                //creating adapter
                //  ArtistList artistAdapter = new ArtistList(getActivity(), artists);
                // listViewEvents.setAdapter(artistAdapter);
                CustomAdapter adpter = new CustomAdapter(getActivity(), evnets);
                listViewEvents.setAdapter(adpter);
                //attaching adapter to the listview

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}



