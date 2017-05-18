package com.example.nava.a2003;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //// User is logged in, go to operation activity
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, Main2Activity.class));
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        final Button btnLogIn = (Button) findViewById(R.id.buttonLogin);
        assert btnLogIn != null;
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });


        final Button btnSignUp = (Button) findViewById(R.id.buttonSignUp);
        assert btnSignUp != null;
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignupActivity.class));
            }
        });
    }
}
