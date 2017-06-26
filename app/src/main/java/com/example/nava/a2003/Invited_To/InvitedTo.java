package com.example.nava.a2003.Invited_To;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nava.a2003.Adapters.CustomAdapter;
import com.example.nava.a2003.General.Event;
import com.example.nava.a2003.General.FirebaseManager;
import com.example.nava.a2003.General.Guest;
import com.example.nava.a2003.General.LoginActivity;
import com.example.nava.a2003.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class InvitedTo extends Fragment {
    ListView listViewInvitedTo;
    List<Event> evnetsInvitedTO;
    DatabaseReference databaseEvents;
    ProgressBar progressBar;
    int idOfFragment = 0;
    FirebaseManager manager;
    FirebaseAuth auth = FirebaseAuth.getInstance();



    public InvitedTo() {
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        listViewInvitedTo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                Log.v("long clicked","pos: " + pos);

                return true;
            }
        });

    }



                             @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_show_events, container, false);
        manager = new FirebaseManager();
        //getting the reference of events node
        databaseEvents = FirebaseDatabase.getInstance().getReference("Events");
        //getting views
        listViewInvitedTo = (ListView) rootView.findViewById(R.id.listView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarShowEvents);
        progressBar.setVisibility(View.VISIBLE);
        //list to store events
        evnetsInvitedTO = new ArrayList<>();
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        fab.hide();
        return rootView;
    }

    public void IsGuestInvited(){
        if(auth != null){
        final String CurentEmailID = auth.getCurrentUser().getEmail().trim();
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous events list
                evnetsInvitedTO.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    if (event != null)
                    {
                        //get all the guests
                        for( DataSnapshot currentGuest: postSnapshot.child("guests").getChildren())
                        {
                            Guest guest = currentGuest.getValue(Guest.class);
                            if(null!= guest && guest.getEmail().trim().compareTo(CurentEmailID) == 0) {
                                //ADD EVENT TO LIST
                                evnetsInvitedTO.add(event); }
                        }
                    }
                }
                CustomAdapter adpter = new CustomAdapter(getActivity(), evnetsInvitedTO, idOfFragment);
                listViewInvitedTo.setAdapter(adpter);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    }

    @Override
    public void onStart() {
        super.onStart();
        IsGuestInvited();
    }
}



