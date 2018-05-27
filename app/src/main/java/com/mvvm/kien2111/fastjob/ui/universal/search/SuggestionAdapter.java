package com.mvvm.kien2111.fastjob.ui.universal.search;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.mvvm.kien2111.fastjob.R;

/**
 * Created by WhoAmI on 05/05/2018.
 */

public class SuggestionAdapter extends CursorAdapter {
    public SuggestionAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.search_view_suggestion_row, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String query = cursor.getString(cursor.getColumnIndexOrThrow("query"));
        TextView text1 = view.findViewById(android.R.id.text1);
        text1.setText(query);
    }
}
