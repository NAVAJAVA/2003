package com.example.nava.a2003.My_Events;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nava.a2003.General.LoginActivity;
import com.example.nava.a2003.R;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Nava on 19/09/2017.
 */

public class SmsFragment extends Fragment {
    private Button btnSendSms;
    private EditText textViewMessage;
    private EditText NumberSMS;
    private GuestsFragment.OnFragmentInteractionListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sms, container, false);
        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SEND_SMS};
        requestPermissions(PERMISSIONS, 1);
        textViewMessage = (EditText) v.findViewById(R.id.textViewMessage);
        NumberSMS = (EditText) v.findViewById(R.id.NumberSMS);
        btnSendSms = (Button) v.findViewById(R.id.btnSendSms);
        btnSendSms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if(!android.text.TextUtils.isDigitsOnly(textViewMessage.getText()) || textViewMessage.getText().length()!= 10 || textViewMessage.getText().toString().isEmpty() || NumberSMS.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(),"must enter corrrect text",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    sendSms(textViewMessage.getText().toString(), NumberSMS.getText().toString());
                    Toast.makeText(v.getContext(),"sms sent!",Toast.LENGTH_SHORT).show();
                    NumberSMS.setText("");
                    textViewMessage.setText("");

                }
            }
        });
        return v;
    }

    private void sendSms(String smsData, String number) {
        SmsManager manager= SmsManager.getDefault();
        manager.sendTextMessage(number, null, smsData, null, null);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}