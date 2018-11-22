package com.example.tunganh.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    private Button button;
    private Button chatButton;
    private Button weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.i(ACTIVITY_NAME, "In onCreate()");

        button = findViewById(R.id.button);
        button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StartActivity.this, ListItemActivity.class);
                startActivityForResult(intent, 50);
            }
        });

        chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });

        weather = findViewById(R.id.weather);
        weather.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Weather Forecast");
                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 50 && resultCode == Activity.RESULT_OK){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            if (data != null) {
                String messagePassed = data.getStringExtra("Response");
                Toast toast = Toast.makeText(this , messagePassed, Toast.LENGTH_LONG);
                toast.show();
            }
        }
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