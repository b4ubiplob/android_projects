package com.tan90.mynotebook;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.tan90.mynotebook.db.DBOpenHelper;

/**
 * Created by I320626 on 7/7/2016.
 */
public class NotebookCursorAdapter extends CursorAdapter {

    private static final String LOG_TAG = "NotebookCursorAdapter";

    public NotebookCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.note_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String text = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTEBOOK_DESC));
        String title = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTEBOOK_TITLE));

        Log.d(LOG_TAG, "title : " + title);
        Log.d(LOG_TAG, "desc : " + text);

        int position = text.indexOf(10);
        if (position != -1) {
            text = text.substring(0, position) + " ...";
        }

        TextView textView = (TextView) view.findViewById(R.id.tvNote);
        textView.setText(text);

        TextView titleView = (TextView) view.findViewById(R.id.tvNoteTitle);
        titleView.setText(title);
    }
}
