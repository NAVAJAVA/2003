package com.example.nava.a2003;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;


/*****************************************************************************
 * This Class will handle all the Login actions
 *****************************************************************************/
public class LoginActivity extends AppCompatActivity {

    private EditText txtUsernameLogin;
    private EditText txtPasswordLogin;
    private Button btnLogin;
    private Button btnResetPassword;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //// User is logged in, go to operation activity
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, ChoiceActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnResetPassword = (Button) findViewById(R.id.btnForgotPassword);
        txtUsernameLogin = (EditText) findViewById(R.id.txtUsernameLogin);
        txtPasswordLogin = (EditText) findViewById(R.id.txtPasswordLogin);

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

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        assert btnResetPassword != null;
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });


        assert btnLogin != null;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (txtUsernameLogin.getText().toString().equals("") ||
                        txtPasswordLogin.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                }
                //all details are ok - send to server?
                else {
                    //startActivity(new Intent(LoginActivity.this, OperationActivity.class));
                    String email = txtUsernameLogin.getText().toString();
                    final String password = txtPasswordLogin.getText().toString();
                    //authenticate user
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                   // progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (password.length() < 6) {
                                            txtPasswordLogin.setError(getString(R.string.minimum_password));
                                        } else {
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, OperationActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }




            }
        });


    }
}