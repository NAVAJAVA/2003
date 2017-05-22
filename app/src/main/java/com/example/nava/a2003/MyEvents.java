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
import android.widget.ProgressBar;
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

public class MyEvents extends Fragment {


    ListView listViewEvents;
    EditText editTextName;
    EditText dateTxt;
    EditText timeTxt;
    EditText bankTxt;
    //a list to store all the events from firebase database
    List<Event> evnets;
    //our database reference object
    DatabaseReference databaseEvents;
    ProgressBar progressBar;


    public MyEvents() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_show_events, container, false);
        //getting the reference of artists node
        databaseEvents = FirebaseDatabase.getInstance().getReference("My Events");
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarShowEvents);
        progressBar.setVisibility(View.VISIBLE);
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
        bankTxt = (EditText) d.findViewById(R.id.bankEditText);
        final Button saveBtn = (Button) d.findViewById(R.id.saveBtn);

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SIMPLE VALIDATION
                String name = editTextName.getText().toString().trim();
                String time = timeTxt.getText().toString().trim();
                String date = dateTxt.getText().toString().trim();
                String bank = bankTxt.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(time)
                        && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(bank)) {
                    addEvent();
                    editTextName.setText("");
                    timeTxt.setText("");
                    dateTxt.setText("");
                    bankTxt.setText("");
                    d.hide();

                }
                else
                {
                    Toast.makeText(getContext(), "Must enter all details", Toast.LENGTH_SHORT).show();
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
        String bank = bankTxt.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(time)
                && !TextUtils.isEmpty(date)&& !TextUtils.isEmpty(bank) ) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our event
            String id = databaseEvents.push().getKey();

            //creating an Event Object

            Event event = new Event();
            event.setName(name);
            event.setDate(date);
            event.setTime(time);
            event.setBankAccountDetails(bank);


            //Saving the event
            databaseEvents.child(id).setValue(event);


            //displaying a success toast
            Toast.makeText(getActivity(), "Event added", Toast.LENGTH_LONG).show();
            //startActivity(new Intent(this.getActivity(),OperationActivity.class));

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

                //clearing the previous events list
                evnets.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Event event = postSnapshot.getValue(Event.class);
                    //adding artist to the list
                    evnets.add(event);
                }

                CustomAdapter adpter = new CustomAdapter(getActivity(), evnets);
                listViewEvents.setAdapter(adpter);
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}



