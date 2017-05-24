package com.example.nava.a2003.My_Events;

import android.content.Context;
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
import com.example.nava.a2003.General.Guest;
import com.example.nava.a2003.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GuestsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GuestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button addButtonGuests;
    private EditText editTextGuest;
    ListView listViewGuests;
    //a list to store all the guests from firebase database
    List<Guest> guests;
    //our database reference object
    DatabaseReference databaseEvents;


    private OnFragmentInteractionListener mListener;

    public GuestsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuestsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuestsFragment newInstance(String param1, String param2) {
        GuestsFragment fragment = new GuestsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_guests, container, false);
        //getting the reference of guests node
        databaseEvents = FirebaseDatabase.getInstance().getReference("My Events");
        //getting views
        listViewGuests = (ListView) rootView.findViewById(R.id.listViewGuests);
        addButtonGuests = (Button) rootView.findViewById(R.id.addButtonGuests);
        editTextGuest = (EditText) rootView.findViewById(R.id.editTextGuest);
        //list to store guests
        guests = new ArrayList<>();
        addButtonGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGuest();
            }
        });

        return rootView;
    }
    private void addGuest() {
        //getting the values to save
        String name = editTextGuest.getText().toString().trim();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our guest
            String id = databaseEvents.push().getKey();
            //creating a guest Object

            Log.d("", "here " + id);

            Guest guest = new Guest();
            guest.setName(name);
            //currentEventPredded.addGUESTLIST(GUEST); AND ALSO IN DB.

            //Saving the guest
            databaseEvents.child(id).setValue(guest);


            //displaying a success toast
            Toast.makeText(getActivity(), "Guest added", Toast.LENGTH_LONG).show();
            editTextGuest.setText("");


        } else {
            //if the value is not given displaying a toast
            Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_LONG).show();
        }
    }

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

                //clearing the previous events list
                guests.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Guest guest = postSnapshot.getValue(Guest.class);
                    //adding artist to the list
                    guests.add(guest);
                }

                GuestAdapter adpter = new GuestAdapter(getActivity(), guests);
                listViewGuests.setAdapter(adpter);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}
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