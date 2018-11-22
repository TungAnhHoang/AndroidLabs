package com.example.tunganh.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    private ListView listView;
    private EditText editText;
    private Button sendButton;
    private final ArrayList<String> list = new ArrayList<String>();

    private SQLiteDatabase db;
    private Cursor cursor;

    private static String ACTIVITY_NAME = "ChatWindow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //initialize class variables
        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);


        ChatDatabaseHelper chatDataHelp = new ChatDatabaseHelper(this);
        db = chatDataHelp.getWritableDatabase();

        String[] columns = {ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE};
        cursor = db.query(ChatDatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        Log.i(ACTIVITY_NAME, "Cursor's column count =" + cursor.getColumnCount());

        cursor.moveToFirst();

        while(!cursor.isAfterLast() ){
            String newMessage = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            list.add(newMessage);
            Log.i(ACTIVITY_NAME, "SQL_MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }

        for(int i = 0;i < cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "Cursor's Column: " + cursor.getColumnName(i));
        }

        //in this case, “this” is the ChatWindow, which is-A Context object
        final ChatAdapter messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText.getText().toString();
                if(!value.isEmpty()) {
                    list.add(value);

                    ContentValues contentValue = new ContentValues();
                    contentValue.put(ChatDatabaseHelper.KEY_MESSAGE, value);
                    db.insert(ChatDatabaseHelper.TABLE_NAME, null, contentValue);

                    messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()
                }

                editText.setText("");
            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        private ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount(){
            return list.size();
        }

        @Override
        public String getItem(int position){
            return list.get(position);
        }

        @Override
        public  View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);}
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;

        }

        @Override
        public long getItemId(int position){
            return position;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.close();
        cursor.close();
    }
}
