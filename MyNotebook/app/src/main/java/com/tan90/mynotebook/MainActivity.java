package com.tan90.mynotebook;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tan90.mynotebook.db.DBOpenHelper;
import com.tan90.mynotebook.db.NotesProvider;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = "MainActivity";
    private CursorAdapter cursorAdapter;
    private static final int EDITOR_REQUEST_INT_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivityForResult(intent, EDITOR_REQUEST_INT_CODE);
            }
        });


        cursorAdapter = new NotesCursorAdapter(this, null, 0);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                Uri uri = Uri.parse(NotesProvider.CONTENT_URI + "/" + id);
                intent.putExtra(NotesProvider.CONTENT_ITEM_TYPE, uri);
                startActivityForResult(intent, EDITOR_REQUEST_INT_CODE);
            }
        });

        getLoaderManager().initLoader(0, null, this);
    }

    private void insertNote(String title, String newNote) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.NOTE_TEXT , newNote);
        contentValues.put(DBOpenHelper.NOTE_TITLE, title);
        Uri uri = getContentResolver().insert(NotesProvider.CONTENT_URI, contentValues);
        Log.d(LOG_TAG, "inserted note " + uri.getLastPathSegment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_create_sample:
                insertSampleData();
                break;
            case R.id.delete_all_notes:
                deleteAllNotes();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertSampleData() {
        insertNote("First note", "Simple note");
        insertNote("Second note", "Multi -line \n note");
        insertNote("Third note", "Very long note with a lot of text which should exceed the width of the screen");
        restartLoader();
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }
    private void deleteAllNotes() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int button) {
                if (button == DialogInterface.BUTTON_POSITIVE) {
                    getContentResolver().delete(NotesProvider.CONTENT_URI, null, null);
                    restartLoader();
                    Toast.makeText(MainActivity.this, getString(R.string.all_deleted), Toast.LENGTH_SHORT).show();
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.are_you_sure)).setPositiveButton(android.R.string.yes, dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, NotesProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    @Override
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        if (requestcode == EDITOR_REQUEST_INT_CODE) {
            if (resultCode == RESULT_OK) {
                restartLoader();
            }
        }
    }
}
