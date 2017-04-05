package com.example.nava.a2003;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Nava on 20/03/2017.
 */

public class SignupActivity  extends AppCompatActivity {
    private Button btnReset;
    private Button btnSend;
    private EditText email;
    private EditText password;
    private EditText name;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
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
        //connect to layout
        btnSend = (Button) findViewById(R.id.btnSend);
        password = (EditText) findViewById(R.id.txtPassword);
        name = (EditText) findViewById(R.id.txtName);
        email = (EditText) findViewById(R.id.txtEmail);
       // progressBar = (ProgressBar) findViewById(R.id.progressBar);
        String strEmail = email.getText().toString().trim();
        String strPassword = password.getText().toString().trim();

        assert btnSend != null;
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strEmail = email.getText().toString().trim();
                String strPassword = password.getText().toString().trim();


                if (password.getText().toString().equals("") ||
                        name.getText().toString().equals("") ||
                        email.getText().toString().equals("") || strPassword.length()<6) {
                    if(strPassword.length() < 6){
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();

                    }
                    else{
                    Toast.makeText(getApplicationContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                }}

                //all details are ok - send to server?
                else {
                   // progressBar.setVisibility(View.VISIBLE);
                    //create user
                    auth.createUserWithEmailAndPassword(strEmail, strPassword)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                   // progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupActivity.this, MainActivity.class));


                                    } else {
                                        startActivity(new Intent(SignupActivity.this, OperationActivity.class));
                                        finish();
                                    }
                                }
                            });

                }
            }
        });
        /***********************************************************************
         * This button will clear all entered data by simply reloading activity
         **********************************************************************/
        btnReset = (Button) findViewById(R.id.btnReset);
        assert btnReset != null;
        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }
}
