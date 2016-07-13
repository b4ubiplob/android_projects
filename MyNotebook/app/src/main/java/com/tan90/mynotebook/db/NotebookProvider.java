package com.tan90.mynotebook.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by I320626 on 7/6/2016.
 */
public class NotebookProvider extends ContentProvider {

    private static final String AUTHORITY = "com.tan90.mynotebook.notebookprovider";
    private static final String BASE_PATH = "notebooks";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final int NOTEBOOKS = 1;
    private static final int NOTEBOOKS_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String CONTENT_ITEM_TYPE = "notebooks";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, NOTEBOOKS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTEBOOKS_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getContext());
        database = dbOpenHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) == NOTEBOOKS_ID) {
            selection = DBOpenHelper.NOTEBOOK_ID + "=" + uri.getLastPathSegment();
        }
        Log.d("NotebookProvider", "uri : " + uri);
        return database.query(DBOpenHelper.TABLE_NOTEBOOK, DBOpenHelper.NOTEBOOK_TABLE_COLUMNS, selection, null,
                null, null, DBOpenHelper.NOTEBOOK_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(DBOpenHelper.TABLE_NOTEBOOK, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_NOTEBOOK, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_NOTEBOOK, values, selection, selectionArgs);
    }
}
