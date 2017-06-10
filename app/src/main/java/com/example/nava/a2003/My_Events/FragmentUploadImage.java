package com.example.nava.a2003.My_Events;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nava.a2003.General.Event;
import com.example.nava.a2003.General.Image;
import com.example.nava.a2003.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


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
    private Uri imgUri;
    private static String FB_STORAGE_PATH = "image/";
    private static int REQUEST_CODE = 1234;
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://project-7aca3.appspot.com");
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
        pictureName = (EditText)v.findViewById(R.id.pictureName);
        pictureName.setVisibility(View.GONE);
        imgViewGalleryUPload = (ImageView)  v.findViewById(R.id.imgViewGalleryUPload);
        buttonLoadPicture.setOnClickListener( new View.OnClickListener()
        {

            @Override
            public void onClick (View arg0){
                if(imgUri!= null){
                    final ProgressDialog dialog = new ProgressDialog(getContext());
                    dialog.setTitle("Uploading image");
                    dialog.show();
                    //add to storage
                    String path =  FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri);
                    StorageReference ref = storage.getReference(path);

                    ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.dismiss();
                            Toast.makeText(getContext(),"IMAGE UPLOAD",Toast.LENGTH_SHORT).show();
                            //save the image info db
                            @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            Image currentImg = new Image(pictureName.getText().toString(),downloadUrl.toString());
                            currentEventRef.child("Images").push().setValue(currentImg);
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                                }
                            })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                        @SuppressWarnings("VisibleForTests") double progress = (100*taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                        dialog.setMessage("Uploaded");
                                    }


                                });

                }
            }

        });

        buttonBrowsePicture.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View arg0){
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i.createChooser(i,"Select image"), REQUEST_CODE);
                pictureName.setVisibility(View.VISIBLE);

            }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && null != data && data.getData()!= null ) {
            imgUri= data.getData();
            try{
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imgUri);
                imgViewGalleryUPload.setImageBitmap(bm);
            }
           catch (FileNotFoundException e)
           {
               Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
               e.printStackTrace();
           }
           catch (IOException e)
           {
               Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
               e.printStackTrace();
           }

        }}


    public String getImageExt( Uri uri)
    {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

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
