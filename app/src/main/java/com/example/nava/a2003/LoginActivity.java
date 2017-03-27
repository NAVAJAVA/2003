package com.example.nava.a2003;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;


/*****************************************************************************
 * This Class will handle all the Login actions
 *****************************************************************************/
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        final Button btnLogIn = (Button) findViewById(R.id.btnLogin);
        assert btnLogIn != null;
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.txtUsernameLogin);
                EditText password = (EditText) findViewById(R.id.txtPasswordLogin);


                if (username.getText().toString().equals("") ||
                        password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                }
                //all details are ok - send to server?
                else {
                    startActivity(new Intent(LoginActivity.this, OperationActivity.class));

                }




            }
        });


    }
}