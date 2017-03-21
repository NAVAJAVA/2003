package com.example.nava.a2003;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Nava on 20/03/2017.
 */

public class SignupActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_signup);
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
