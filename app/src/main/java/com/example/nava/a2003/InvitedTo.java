package com.example.nava.a2003;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nava on 22/05/2017.
 */

/**
 * Created by Nava on 22/05/2017.
 */

public class InvitedTo extends Fragment {
    ListView listViewInvitedTo;
    List<Event> evnetsInvitedTO;
    DatabaseReference databaseEventsinvitedTo;

//TODO: same as MyEvents class just diaplay data from differnt jason tree
    //// TODO: 22/05/2017: how to be added to a gruop in whatapp
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invited_to, container, false);

        databaseEventsinvitedTo = FirebaseDatabase.getInstance().getReference("Events Invited To");
        listViewInvitedTo = (ListView) rootView.findViewById(R.id.lv_invitedTO);
        evnetsInvitedTO = new ArrayList<>();

        return rootView;
    }

}