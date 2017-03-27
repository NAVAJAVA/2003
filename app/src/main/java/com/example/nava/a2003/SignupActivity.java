package com.example.nava.a2003;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nava on 20/03/2017.
 */

public class SignupActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_signup);
        //setting name of app in center
        ActionBar ab = getSupportActionBar();
        TextView textview = new TextView(getApplicationContext());
        ActionBar.LayoutParams layoutparams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutparams);
        textview.setGravity(Gravity.CENTER);
        textview.setText(ab.getTitle().toString());
        textview.setTextSize(20);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView(textview);
        final Button btnSend = (Button) findViewById(R.id.btnSend);
        assert btnSend != null;
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.txtUsername);
                EditText password = (EditText) findViewById(R.id.txtPassword);
                EditText name = (EditText) findViewById(R.id.txtName);
                EditText email = (EditText) findViewById(R.id.txtEmail);


                if (username.getText().toString().equals("") ||
                        password.getText().toString().equals("") ||
                        name.getText().toString().equals("") ||
                        email.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                }
                //all details are ok - send to server?
                else {

                }
            }
        });
        /***********************************************************************
         * This button will clear all entered data by simply reloading activity
         **********************************************************************/
        final Button btnReset = (Button) findViewById(R.id.btnReset);
        assert btnReset != null;
        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }
}
