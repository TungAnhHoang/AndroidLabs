package com.example.tunganh.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    protected static final String MY_PREF = "MY_PREF";
    protected static final String DEFAULT_EMAIL_KEY = "DEFAULT_EMAIL_KEY";

    private Button buttonLogin;
    private SharedPreferences sharePref;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i(ACTIVITY_NAME, "In onCreate()");

        // references buttonLogin and emailEditText
        buttonLogin = findViewById(R.id.button);
        emailEditText = findViewById(R.id.login);

        // initial or get SharedPreference
        sharePref = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);

        String defaultEmail = sharePref.getString(DEFAULT_EMAIL_KEY, "email@domain.com");
        emailEditText.setText(defaultEmail);

        // login button - click listener: inside login button click listener
        //  take value from emailExitText
        // save it back to SharedPrefer
        //next, transit to StartActive
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = emailEditText.getText().toString();
                SharedPreferences.Editor editor = sharePref.edit();
                editor.putString(DEFAULT_EMAIL_KEY, input);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}