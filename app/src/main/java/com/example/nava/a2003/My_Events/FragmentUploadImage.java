package com.example.nava.a2003.My_Events;

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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nava.a2003.General.Event;
import com.example.nava.a2003.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentUploadImage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentUploadImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUploadImage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String currentIdEvent;
    private Button buttonLoadPicture;
    private Button buttonBrowsePicture;
    private ImageView imgViewGalleryUPload;
    private EditText pictureName;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    DatabaseReference EventsRef = FirebaseDatabase.getInstance().getReference("Events");
    DatabaseReference currentEventRef = FirebaseDatabase.getInstance().getReference("Events");

    private OnFragmentInteractionListener mListener;

    public FragmentUploadImage() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();
        //go over all events, find eventKey and set current currentEventRef to it.
        EventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    if (event != null && event.getIdEvent().equals(currentIdEvent)) {
                         currentEventRef = postSnapshot.getRef();
                        Log.d("curennt",event.getIdEvent());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentUploadImage.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentUploadImage newInstance(String param1, String param2) {
        FragmentUploadImage fragment = new FragmentUploadImage();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upload_images, container, false);
        buttonBrowsePicture = (Button) v.findViewById(R.id.buttonBrowsePicture);
        buttonLoadPicture = (Button) v.findViewById(R.id.buttonLoadPicture);
        //todo - when the buttons are preesded - by viedo
        //

        String path = "fire/" + UUID.randomUUID() +".png";
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://project-7aca3.appspot.com");
        StorageReference ref = storage.getReference(path);
        //StorageMetadata metadata = new StorageMetadata.Builder()
        UploadTask uploadTask = ref.putFile(Uri.EMPTY);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

        // Inflate the layout for this fragment
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
