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

public class PreferencesActivity extends AppCompatActivity {

    public static final String PREFS_FILE = "MyPrefsFile.txt";
    int counter=0;
    private SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy-hh:mm a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        counter = preferences.getInt("PREF_COUNTER",0);
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        counter = preferences.getInt("PREF_COUNTER",0);
    }

    public void onClickSave(View view){
        EditText txt_book = (EditText) findViewById(R.id.book);
        EditText txt_author = (EditText) findViewById(R.id.author);
        EditText txt_desc = (EditText) findViewById(R.id.desc);

        String book = txt_book.getText().toString();
        String author = txt_author.getText().toString();
        String desc = txt_desc.getText().toString();

        if(book!=null && author!=null && desc!=null){
            counter++;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("PREF_COUNTER", counter);
            editor.commit();

            try {
                OutputStreamWriter writer = new OutputStreamWriter(openFileOutput(PREFS_FILE,MODE_APPEND));
                writer.write("\n Saved Preference "+counter+" , "+s.format(new Date()));
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
