package com.android.datastorageapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteActivity extends AppCompatActivity {

    int counter=0;
    private SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        counter = preferences.getInt("SQL_COUNTER",0);
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        counter = preferences.getInt("PREF_COUNTER",0);
    }

    public void onClickSave(View view){

        EditText txt_msg = (EditText)findViewById(R.id.msg);
        String msg = txt_msg.getText().toString();

        DBController dbController = new DBController(getBaseContext());
        dbController.openDB();
        long result = dbController.insert(msg);
        dbController.close();

        if(result!=-1){

            counter++;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("SQL_COUNTER", counter);
            editor.commit();

            try {
                OutputStreamWriter writer = new OutputStreamWriter(openFileOutput(PreferencesActivity.PREFS_FILE,MODE_APPEND));
                writer.write("\n SQL Lite "+counter+" , "+s.format(new Date()));
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void onClickCancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
