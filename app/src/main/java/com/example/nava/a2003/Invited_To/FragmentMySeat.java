package com.example.nava.a2003.Invited_To;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nava.a2003.General.Event;
import com.example.nava.a2003.General.FirebaseManager;
import com.example.nava.a2003.General.Guest;
import com.example.nava.a2003.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMySeat.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMySeat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMySeat extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String currentIdEvent="";
    private Button btnTableNumber;
    private String seat="";
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String CurentEmailID = auth.getCurrentUser().getEmail().trim();
    DatabaseReference EventsRef = FirebaseDatabase.getInstance().getReference("Events");
    FirebaseManager manager = new FirebaseManager();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentMySeat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMainInvited.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMySeat newInstance(String param1, String param2) {
        FragmentMySeat fragment = new FragmentMySeat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getting the current event which has been pressed
        Intent i = getActivity().getIntent();
        currentIdEvent = (String) i.getSerializableExtra("CurrentIdEvnet");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void getTableNumber()
    {


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_five, container, false);
        btnTableNumber = (Button) v.findViewById(R.id.btnTableNumber);
        //go over all events, find eventKey and CurentEmailID. set seat.
        EventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    if (event != null && event.getIdEvent().equals(currentIdEvent))
                    {
                        //get all the guests
                        for( DataSnapshot currentGuest: postSnapshot.child("guests").getChildren())
                        {
                            Guest guest = currentGuest.getValue(Guest.class);
                            if(null!= guest && guest.getEmail().trim().compareTo(CurentEmailID) == 0) {
                                seat = guest.getSeat().trim();
                                btnTableNumber.setText(seat);
                                break;
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
