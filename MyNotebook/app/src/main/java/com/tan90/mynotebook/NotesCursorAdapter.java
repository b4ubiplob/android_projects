package com.tan90.mynotebook;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.tan90.mynotebook.db.DBOpenHelper;

/**
 * Created by I320626 on 7/2/2016.
 */
public class NotesCursorAdapter extends CursorAdapter {

    public NotesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.note_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String text = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_TEXT));
        String title = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_TITLE));


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
