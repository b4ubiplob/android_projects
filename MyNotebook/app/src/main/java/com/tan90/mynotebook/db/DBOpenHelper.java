package com.tan90.mynotebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by I320626 on 6/30/2016.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DBOpenHelper";

    //Constants for db name and version
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 2;

    //Constants for identifying table and columns
    public static final String TABLE_NOTES = "notes";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_TITLE = "noteTitle";
    public static final String NOTE_TEXT = "noteText";
    public static final String NOTE_CREATED = "noteCreated";

    public static final String[] ALL_COLUMNS = {NOTE_ID, NOTE_TITLE, NOTE_TEXT, NOTE_CREATED};
    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTE_TITLE + " TEXT, " +
                    NOTE_TEXT + " TEXT, " +
                    NOTE_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Creating database");
        Log.d(LOG_TAG, "Query : " + TABLE_CREATE);
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "Updating database");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }
}
