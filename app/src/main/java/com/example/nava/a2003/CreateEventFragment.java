package com.example.nava.a2003;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText txtEventName;
    private EditText txtTime;
    private EditText txtDate;
    private EditText txtBankDetails;
    private Button btnCreate;
    private Button btnInvitation;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static int RESULT_LOAD_IMAGE = 1;
    private  String picturePath;
    private View mContentView = null;





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFragment newInstance(String param1, String param2) {
        CreateEventFragment fragment = new CreateEventFragment();
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
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mContentView = inflater.inflate(R.layout.fragment_create_event , null);
    }
    public void onViewCreated(final View view, Bundle savedInstanceState) {
       // ImageView imageView = (ImageView) view.findViewById(R.id.imgViewIn);
        //imageView.setImageResource(R.drawable.black);
        mContentView = view;
        btnInvitation = (Button) view.findViewById(R.id.btnInvitation);
        btnInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        btnCreate = (Button) view.findViewById(R.id.btnCreate);
        txtEventName = (EditText) view.findViewById(R.id.txtEventName);
        txtDate = (EditText) view.findViewById(R.id.txtDate);
        txtTime = (EditText) view.findViewById(R.id.txtTime);
        txtBankDetails = (EditText) view.findViewById(R.id.txtBankDetails);



        //when btncreate is pressend add to database and go back to main?

        assert btnCreate != null;
        btnCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (txtEventName.getText().toString().equals("") ||
                      txtTime.getText().toString().equals("") ||
                        txtDate.getText().toString().equals("") ||
                        txtBankDetails.getText().toString().equals("") )
                {
                    Toast.makeText(getContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                }
                //all info was typed in
                else
                {
                    // Add event to database
                    DatabaseReference nameRef = database.getReference("Event Name");
                    DatabaseReference timeRef = database.getReference("Event Time");
                    DatabaseReference dateRef = database.getReference("Event Date");
                    DatabaseReference bankRef = database.getReference("Bank account details");




                    //to do - to ascribe the user table?
                    nameRef.setValue(txtEventName.getText().toString().trim());
                    timeRef.setValue(txtTime.getText().toString().trim());
                    dateRef.setValue(txtDate.getText().toString().trim());
                    bankRef.setValue(txtBankDetails.getText().toString().trim());
                }

            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_event, container, false);

    }

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
            picturePath = cursor.getString(columnIndex);
            cursor.close();


            DatabaseReference pathInvitation = database.getReference("path Invitation");
            pathInvitation.setValue(picturePath);
           // ImageView imageView = (ImageView) mContentView.findViewById(R.id.imgViewIn);
            //imageView.setImageResource(R.drawable.black);


            ImageView imageView = (ImageView) mContentView.findViewById(R.id.imgViewIn);
            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            File imgFile = new  File(picturePath);
            if(imgFile.exists())
            {
                imageView.setImageURI(Uri.fromFile(imgFile));
                Picasso.with(getContext()).load("http://www.grafix.co.il/wp-content/media/2016/09/weddinginvitationsgrafix1.jpg").into(imageView);

            }
        }

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
