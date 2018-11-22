package com.example.tunganh.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "ChatDatabaseHelper";

    private static final String DATABASE_NAME = "Messages.db";
    private static final int VERSION_NUM = 3;
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "_mgs";
    public static final String TABLE_NAME = "messages";

    ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE " + TABLE_NAME + "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " TEXT)");

        Log.i(TAG, "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        Log.i(TAG, "Calling onUpgrade, oldVersion= " + oldVer + " newVersion= " + newVer);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
