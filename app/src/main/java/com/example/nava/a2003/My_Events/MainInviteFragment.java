package com.example.nava.a2003.My_Events;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nava.a2003.General.Event;
import com.example.nava.a2003.General.Guest;
import com.example.nava.a2003.General.Image;
import com.example.nava.a2003.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainInviteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainInviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainInviteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int REQUEST_CODE = 1234;
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://project-7aca3.appspot.com");


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Uri imgUri;
    private ImageView imageView;
    private EditText txtEventName;
    private EditText txtTime;
    private EditText txtDate;
    private EditText txtBankDetails;
    private Button btnCreate;
    private String currentIdEvent="";
    private Button btnInvitation;
    private static String FB_STORAGE_PATH = "Invitaions/";

    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Events");
    DatabaseReference refToEvent = FirebaseDatabase.getInstance().getReference("Events");

    private static int RESULT_LOAD_IMAGE = 1;
    private OnFragmentInteractionListener mListener;

    public MainInviteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainInviteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainInviteFragment newInstance(String param1, String param2) {
        MainInviteFragment fragment = new MainInviteFragment();
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
    public void onStart() {
        super.onStart();
        //first time the screen is shown pull data from db and show it on screen
        //if(first) {
          //  first=false;
            //showing the details of the event that was pressed
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //getting event
                        Event event = postSnapshot.getValue(Event.class);
                        //getting the event which was pressed
                        if (event.getIdEvent().equals(currentIdEvent)) {
                            txtEventName.setText(event.getName().toString().trim());
                            txtBankDetails.setText(event.getBankAccountDetails().toString().trim());
                            txtTime.setText(event.getTime().toString().trim());
                            txtDate.setText(event.getDate().toString().trim());
                            Image image = postSnapshot.child("invitaion").getValue(Image.class);

                            if(image!= null && 0!= image.getUrl().compareTo("")) {
                                Glide.with(getContext()).load(image.getUrl()).into(imageView);
                            }
                            refToEvent = postSnapshot.getRef();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        txtEventName = (EditText) view.findViewById(R.id.txtEventName);
        txtBankDetails = (EditText) view.findViewById(R.id.txtBankDetails);
        txtTime = (EditText) view.findViewById(R.id.txtTime);
        txtDate = (EditText) view.findViewById(R.id.txtDate);
        btnCreate = (Button) view.findViewById(R.id.btnCreate);
        imageView = (ImageView) view.findViewById(R.id.imgViewIn) ;

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String name = txtEventName.getText().toString().trim();
                String time = txtTime.getText().toString().trim();
                String date = txtDate.getText().toString().trim();
                String bank = txtBankDetails.getText().toString().trim();
                //checking if the value is provided
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(time)
                        && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(bank)) {
                    refToEvent.child("name").setValue(name);
                    refToEvent.child("time").setValue(time);
                    refToEvent.child("bankAccountDetails").setValue(bank);
                    refToEvent.child("date").setValue(date);
                    //go over guests which Rsvp and set counter and set conuter?
                    // refToEvent.child("counterGuests").setValue(4);
                    //save the image info db
                    if (imgUri != null) {
                        final ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setTitle("Uploading image");
                        dialog.show();
                        //add to storage
                        String path = FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri);
                        StorageReference ref = storage.getReference(path);

                        ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                dialog.dismiss();
                                Toast.makeText(getContext(), "IMAGE UPLOAD", Toast.LENGTH_SHORT).show();
                                //save the image info db
                                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                Image currentImg = new Image("", downloadUrl.toString());
                                refToEvent.child("invitaion").setValue(currentImg);

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                        @SuppressWarnings("VisibleForTests") double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                        dialog.setMessage("Uploaded");
                                    }


                                });

                    }
                }
            }


        });
        btnInvitation =(Button)view.findViewById(R.id.btnInvitation);
        btnInvitation.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View arg0){
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i.createChooser(i,"Select image"), REQUEST_CODE);
            }
        });


        return view;

    }


    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            cursor.close();
            //ImageView imageView = (ImageView) getView().findViewById(R.id.imgViewIn);

            //adding the path of the db
            refToEvent.child("urlInvitaion").setValue(selectedImage.toString().trim());

            Picasso.with(getContext()).load(selectedImage).noPlaceholder().centerCrop().fit()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView);
    }}

*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && null != data && data.getData()!= null ) {
              imgUri= data.getData();

            try{
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
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
