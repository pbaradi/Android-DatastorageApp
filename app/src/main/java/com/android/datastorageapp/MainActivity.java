package com.android.datastorageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        try {
            InputStream in = openFileInput(PreferencesActivity.PREFS_FILE);
            if(in!=null) {
                InputStreamReader tmp=new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(tmp);
                String str;
                StringBuilder buf=new StringBuilder();
                while((str=reader.readLine())!=null) {
                    buf.append(str +"\n");
                }
                in.close();
                TextView savedPref=(TextView)findViewById(R.id.textView2);
                savedPref.setText(buf.toString());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void onClickPreferences(View view){
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    public void onClickSQLite(View view){
        Intent intent = new Intent(this, SQLiteActivity.class);
        startActivity(intent);
    }

    public void onClickClose(View view){
        Log.i("MainActivity: ", "on close");
        this.finish();
    }
}
