package com.example.milan.milanscholtentodo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Milan on 23-11-2017 for app studio.
 */

public class TodoAdapter extends ResourceCursorAdapter {
    public TodoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_todo, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
            TextView Title = view.findViewById(R.id.Title);
            int color = cursor.getInt(2);
            if (color == 1) {
                Title.setBackgroundColor(Color.YELLOW);
            }
            else{
                Title.setBackgroundColor(Color.WHITE);
            }
            Title.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        }
    }
