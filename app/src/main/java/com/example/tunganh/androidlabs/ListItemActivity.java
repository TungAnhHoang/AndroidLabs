package com.example.tunganh.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemActivity extends Activity {

    protected static final String ACTIVITY_NAME = "ListItemActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton imageButton;
    private Switch switchButton;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        Log.i(ACTIVITY_NAME, "In onCreate()");

        imageButton = findViewById(R.id.buttonImage);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intentPhoto.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intentPhoto, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        switchButton = findViewById(R.id.switch1);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                CharSequence text = (checked ? "Switch is On" : "Switch is Off");
                int duration = (checked ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);

                Toast toast = Toast.makeText(ListItemActivity.this, text, duration);
                toast.show();
            }
        });

        checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                if(checked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemActivity.this);
                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage(R.string.dialog_message)   //Add a dialog message to strings.xml
                            .setTitle(R.string.dialog_title)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                 public void onClick(DialogInterface dialog, int id) {
                                    // User clicked OK button
                                    Intent resultIntent = new Intent( );
                                    resultIntent.putExtra("Response", "Here is my response");
                                    setResult(Activity.RESULT_OK, resultIntent);
                                    finish();
                                 }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                    checkBox.setChecked(false);
                                }
                            })
                            .show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
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