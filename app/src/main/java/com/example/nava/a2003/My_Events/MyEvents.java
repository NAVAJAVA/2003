package com.example.nava.a2003.My_Events;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nava.a2003.Adapters.CustomAdapter;
import com.example.nava.a2003.General.Event;
import com.example.nava.a2003.General.Image;
import com.example.nava.a2003.R;
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth auth;
    private String CurrentAuthkey ;
    EditText editTextName;
    EditText dateTxt;
    EditText timeTxt;
    EditText bankTxt;
    //a list to store all the events from firebase database
    List<Event> evnets;
    //our database reference object
    DatabaseReference databaseEvents;
    ProgressBar progressBar;
    int idOfFragment = 1;

    public MyEvents() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_show_events, container, false);
        //getting the reference of event node
        databaseEvents = FirebaseDatabase.getInstance().getReference("Events");
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarShowEvents);
        progressBar.setVisibility(View.VISIBLE);
        //getting views
        listViewEvents = (ListView) rootView.findViewById(R.id.listView);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        auth = FirebaseAuth.getInstance();
        CurrentAuthkey = auth.getCurrentUser().getUid().trim();
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
            event.setInvitaion(new Image("",""));
            event.setName(name);
            event.setDate(date);
            event.setTime(time);
            event.setBankAccountDetails(bank);
            event.setIdEvent(id);
            ArrayList<String> L = new ArrayList<String>();
            L.add(CurrentAuthkey);
            event.setOwnersKeys(L);
            //Saving the event
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

                //clearing the previous events list
                evnets.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting event
                    Event event = postSnapshot.getValue(Event.class);
                    if(checkOwner(event.getOwnersKeys())) {
                        //adding event to the list
                        evnets.add(event);
                    }
                }

                CustomAdapter adpter = new CustomAdapter(getActivity(), evnets,idOfFragment);
                listViewEvents.setAdapter(adpter);
                progressBar.setVisibility(View.GONE);


            }

            public boolean checkOwner(ArrayList<String> list)
            {
                for (String s:list) {
                    if(0 == s.compareTo(CurrentAuthkey))
                    {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}



