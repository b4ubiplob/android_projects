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
    public static final String NOTEBOOK_ID_REF = "notebookId";

    public static final String TABLE_NOTEBOOK = "notebook";
    public static final String NOTEBOOK_ID = "_id";
    public static final String NOTEBOOK_TITLE = "notebookTitle";
    public static final String NOTEBOOK_DESC = "notebookDesc";
    public static final String NOTEBOOK_CREATED = "notebookCreated";

    public static final String[] NOTEBOOK_TABLE_COLUMNS = {NOTEBOOK_ID, NOTEBOOK_TITLE, NOTEBOOK_DESC, NOTEBOOK_CREATED};
    public static final String[] NOTES_TABLE_COLUMNS = {NOTE_ID, NOTE_TITLE, NOTE_TEXT, NOTE_CREATED};
    //SQL to create table


    private static String NOTEBOOK_TABLE_CREATE = null;
    private static String NOTE_TABLE_CREATE = null;
    private static String NOTEBOOK_INSERT = null;
    private static String NOTE_INSERT = null;

    static {

       NOTEBOOK_TABLE_CREATE = "CREATE TABLE " + TABLE_NOTEBOOK
                + " (" + NOTEBOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOTEBOOK_TITLE + " TEXT, "
                + NOTEBOOK_DESC + " TEXT, "
                + NOTEBOOK_CREATED + " TEXT default CURRENT_TIMESTAMP)";
        NOTE_TABLE_CREATE = "CREATE TABLE " + TABLE_NOTES + " (" +
                        NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NOTE_TITLE + " TEXT, " +
                        NOTE_TEXT + " TEXT, " +
                        NOTE_CREATED + " TEXT default CURRENT_TIMESTAMP, " +
                        NOTEBOOK_ID_REF + " INTEGER default 1, " +
                        "FOREIGN KEY (" + NOTEBOOK_ID_REF + ") REFERENCES " +
                        TABLE_NOTEBOOK + "(" + NOTEBOOK_ID + ")" +
                        ")";

        NOTEBOOK_INSERT = "INSERT INTO " + TABLE_NOTEBOOK +
                "(" + NOTEBOOK_TITLE + ", " + NOTEBOOK_DESC +") " +
                " VALUES (\"First Notebook\", \"First Notebook\");";

        NOTE_INSERT = "INSERT INTO " + TABLE_NOTES +
                "(" + NOTE_TITLE + ", " + NOTE_TEXT +") " +
                " VALUES (\"First note\", \"First note\");";
    }



    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Creating database");

        db.execSQL(NOTEBOOK_TABLE_CREATE);
        db.execSQL(NOTE_TABLE_CREATE);
        db.execSQL(NOTEBOOK_INSERT);
        db.execSQL(NOTE_INSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "Updating database");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTEBOOK);
        onCreate(db);
    }
}
