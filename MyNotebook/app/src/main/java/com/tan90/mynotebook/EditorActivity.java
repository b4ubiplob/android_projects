package com.tan90.mynotebook;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.tan90.mynotebook.db.DBOpenHelper;
import com.tan90.mynotebook.db.NotesProvider;


public class EditorActivity extends AppCompatActivity {

    private String action;
    private TextView editor;
    private TextView title;
    private String noteFilter;
    private String oldText;
    private String oldTitle;
    private static final String LOG_TAG = "EditorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editor = (TextView) findViewById(R.id.editText);
        title = (TextView) findViewById(R.id.titleText);
        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(NotesProvider.CONTENT_ITEM_TYPE);
        if (uri == null) {
            action = Intent.ACTION_INSERT;
            setTitle(R.string.new_note);
        }
        else {
            action = Intent.ACTION_EDIT;
            noteFilter = DBOpenHelper.NOTE_ID + "=" + uri.getLastPathSegment();
            Cursor cursor = getContentResolver().query(uri, DBOpenHelper.ALL_COLUMNS,
                    noteFilter, null, null);
            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_TEXT));
            oldTitle = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_TITLE));
            title.setText(oldTitle);
            editor.setText(oldText);
            title.requestFocus();
            setTitle(getString(R.string.edit_note));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (action == Intent.ACTION_EDIT) {
            getMenuInflater().inflate(R.menu.menu_editor, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finishEditing();
                break;
            case R.id.action_delete:
                deleteNote();
                break;
        }
        return true;
    }

    private void deleteNote() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int button) {
                if (button == DialogInterface.BUTTON_POSITIVE) {
                    getContentResolver().delete(NotesProvider.CONTENT_URI, noteFilter, null);
                    Toast.makeText(EditorActivity.this, R.string.note_deleted, Toast.LENGTH_LONG).show();
                }
                setResult(RESULT_OK);
                finish();
            }
        };

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.are_you_sure)).setPositiveButton(android.R.string.yes, dialogClickListener)
                .setNegativeButton(getString(android.R.string.no), dialogClickListener).show();

    }

    private void finishEditing() {
        String newText = editor.getText().toString().trim();
        String newTitle = title.getText().toString().trim();
        switch (action) {
            case Intent.ACTION_INSERT:
                if (newText.length() == 0 && newTitle.length() == 0) {
                    setResult(RESULT_CANCELED);
                }
                else {
                    createNote(newText, newTitle);
                }
                break;
            case Intent.ACTION_EDIT:
                if (newText.length() == 0 && newTitle.length() == 0) {
                    deleteNote();
                }
                else if (newText.equals(oldText) && newTitle.equals(oldTitle)) {
                    setResult(RESULT_CANCELED);
                }
                else {
                    updateNote(newText, newTitle);
                }
        }
        finish();
    }

    private void updateNote(String text, String titleText) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.NOTE_TEXT , text);
        contentValues.put(DBOpenHelper.NOTE_TITLE, titleText);
        getContentResolver().update(NotesProvider.CONTENT_URI, contentValues, noteFilter, null);
        Toast.makeText(this, R.string.note_updated, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void createNote(String text, String titleText) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.NOTE_TEXT , text);
        contentValues.put(DBOpenHelper.NOTE_TITLE, titleText);
        Uri uri = getContentResolver().insert(NotesProvider.CONTENT_URI, contentValues);
        Log.d(LOG_TAG, "inserted note " + uri.getLastPathSegment());
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        finishEditing();
    }
}
