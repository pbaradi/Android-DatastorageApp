package com.android.datastorageapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by pavanibaradi on 9/27/16.
 */
public class DBController {

    Context context;
    SQLiteDatabase db;
    MessageDBHelper dbHelper;

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DataEntry.TABLE_NAME + " (" +
                    DataEntry._ID + " INTEGER PRIMARY KEY," +
                    DataEntry.COLUMN_NAME_MSG + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DataEntry.TABLE_NAME;

    public DBController(Context context){
        this.context=context;
        dbHelper=new MessageDBHelper(context);
    }

    public DBController openDB(){
        db = dbHelper.getWritableDatabase();
        return  this;
    }

    public long insert(String message){

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DataEntry.COLUMN_NAME_MSG, message);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DataEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public void close(){
        db.close();
    }

    /* Inner class that defines the table contents */
    public static class DataEntry implements BaseColumns {
        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_NAME_MSG = "message";
    }

    public class MessageDBHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "DataStorageApp.db";

        public MessageDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }


}
