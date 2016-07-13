package com.tan90.mynotebook;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.tan90.mynotebook.db.DBOpenHelper;
import com.tan90.mynotebook.db.NotebookProvider;

public class NotebookEditorActivity extends AppCompatActivity {

    private String action;
    private TextView editor;
    private TextView title;
    private String notebookFilter;
    private String oldText;
    private String oldTitle;

    private static final String LOG_TAG = "NotebookEditorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook_editor);

        editor = (TextView) findViewById(R.id.notebookDesc);
        title = (TextView) findViewById(R.id.notebookTitle);
        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(NotebookProvider.CONTENT_ITEM_TYPE);
        Log.d(LOG_TAG, "uri : " + uri);
        if (uri == null) {
            action = Intent.ACTION_INSERT;
            setTitle(R.string.new_notebook);
        } else {
            action = Intent.ACTION_EDIT;
            notebookFilter = DBOpenHelper.NOTEBOOK_ID + "=" + uri.getLastPathSegment();
            Cursor cursor = getContentResolver().query(uri, DBOpenHelper.NOTEBOOK_TABLE_COLUMNS,
                    notebookFilter, null, null);
            cursor.moveToFirst();
            oldText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTEBOOK_DESC));
            oldTitle = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTEBOOK_TITLE));
            title.setText(oldTitle);
            editor.setText(oldText);
            title.requestFocus();
            setTitle(getString(R.string.edit_notebook));
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
                deleteNotebook();
                break;
        }
        return true;
    }

    private void deleteNotebook() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int button) {
                if (button == DialogInterface.BUTTON_POSITIVE) {
                    getContentResolver().delete(NotebookProvider.CONTENT_URI, notebookFilter, null);
                    Toast.makeText(NotebookEditorActivity.this, R.string.notebook_deleted, Toast.LENGTH_LONG).show();
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
                    createNotebook(newText, newTitle);
                }
                break;
            case Intent.ACTION_EDIT:
                if (newText.length() == 0 && newTitle.length() == 0) {
                    deleteNotebook();
                }
                else if (newText.equals(oldText) && newTitle.equals(oldTitle)) {
                    setResult(RESULT_CANCELED);
                }
                else {
                    updateNotebook(newText, newTitle);
                }
        }
        finish();
    }

    private void updateNotebook(String text, String titleText) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.NOTEBOOK_DESC, text);
        contentValues.put(DBOpenHelper.NOTEBOOK_TITLE, titleText);
        getContentResolver().update(NotebookProvider.CONTENT_URI, contentValues, notebookFilter, null);
        Toast.makeText(this, R.string.notebook_updated, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void createNotebook(String text, String titleText) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.NOTEBOOK_DESC , text);
        contentValues.put(DBOpenHelper.NOTEBOOK_TITLE, titleText);
        Uri uri = getContentResolver().insert(NotebookProvider.CONTENT_URI, contentValues);
        Log.d(LOG_TAG, "inserted notebook " + uri.getLastPathSegment());
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        finishEditing();
    }
}
