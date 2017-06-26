package com.example.nava.a2003.My_Events;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nava.a2003.Adapters.GuestAdapter;
import com.example.nava.a2003.General.Event;
import com.example.nava.a2003.General.Guest;
import com.example.nava.a2003.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GuestsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GuestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeatsFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText guestName;
    private EditText guestEmail;
    private EditText guestTableNumber;
    private  int counterGuests;
    private Button addButtonGuests;
    ListView listViewGuests;
    //a list to store all the guests from firebase database
    List<Guest> guests;
    //our database reference object
    DatabaseReference databaseEvents;
    private String currentIdEvent="";



    private OnFragmentInteractionListener mListener;

    public SeatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeatsFragment newInstance(String param1, String param2) {
        SeatsFragment fragment = new SeatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getActivity().getIntent();
        currentIdEvent = (String) i.getSerializableExtra("CurrentIdEvnet");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        counterGuests = 0;
        View rootView = inflater.inflate(R.layout.fragment_guests, container, false);
        addButtonGuests = (Button) rootView.findViewById(R.id.addButtonGuests);
        addButtonGuests.setText("how many Rsvp?");
        addButtonGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonGuests.setText(String.valueOf(counterGuests));

            }
        });


        //getting the reference of guests node
        databaseEvents = FirebaseDatabase.getInstance().getReference("Events");
        //find the ref to the current event (in order to add guests list in proper place)
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    if (event.getIdEvent().equals(currentIdEvent)) {
                        databaseEvents = postSnapshot.getRef().child("guests");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //getting views
        listViewGuests = (ListView) rootView.findViewById(R.id.listViewGuests);
        //list to store guests
        guests = new ArrayList<>();

        return rootView;
    }
    /*
    private void addGuest() {
        //getting the values to save
        String name = guestName.getText().toString().trim();
        String email = guestEmail.getText().toString().trim();
        String table = guestTableNumber.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {
            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our guest
            String id = databaseEvents.push().getKey();
            //creating a guest Object
            Guest guest = new Guest();
            guest.setName(name);
            guest.setEmail(email);
            guest.setSeat(table);
            //Saving the guest
            databaseEvents.child(id).setValue(guest);
            Toast.makeText(getActivity(), "Guest added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_LONG).show();
        }
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous guests list
                guests.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //need to go to event that is equal to current and to dispaly the list of it
                    //getting current event and add the guest to it
                    Event event = postSnapshot.getValue(Event.class);
                    if (event != null && event.getIdEvent().compareTo(currentIdEvent)==0){
                        //get all the guests
                        for( DataSnapshot currentGuest: postSnapshot.child("guests").getChildren())
                        {
                            Guest guest = currentGuest.getValue(Guest.class);
                            if(null!= guest) {
                                if(guest.getRsvp().compareTo("yes") == 0){
                                    counterGuests++;
                                }
                                guests.add(guest);
                            }
                        }
                    }
                }
                //sort the guests list by table number
                order(guests);
                GuestAdapter adpter = new GuestAdapter(getActivity(), guests);
                listViewGuests.setAdapter(adpter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    private void displayInputDialog() {
        final Dialog d = new Dialog(getContext());
        d.setContentView(R.layout.guest_dialog);
        guestName = (EditText) d.findViewById(R.id.guestName);
        guestEmail = (EditText) d.findViewById(R.id.guestEmail);
        guestTableNumber = (EditText) d.findViewById(R.id.guestTableNumber);
        final Button saveBtn = (Button) d.findViewById(R.id.guestSaveBtn);

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SIMPLE VALIDATION
                String name = guestName.getText().toString().trim();
                String email = guestEmail.getText().toString().trim();
                String table = guestTableNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email)
                        && !TextUtils.isEmpty(table)) {
                    addGuest();
                    guestName.setText("");
                    guestEmail.setText("");
                    guestTableNumber.setText("");
                    d.hide();
                    d.dismiss();

                }
                else
                {
                    Toast.makeText(getContext(), "Must enter all details", Toast.LENGTH_SHORT).show();
                }

            }
        });

        d.show();
    }*/
    private static void order(List<Guest> roles) {

        Collections.sort(roles, new Comparator() {
            public int compare(Object o1, Object o2) {

                int x1 = Integer.parseInt(((Guest) o1).getSeat());
                int x2 = Integer.parseInt(((Guest) o2).getSeat());

                if (x1 == x2) {
                    return 0;
                } else {
                    return x1 - x2;
                }
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
