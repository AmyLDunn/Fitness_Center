package com.example.fitnesscenter.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.fitnesscenter.R;

public class AccountsCursorAdapter extends CursorAdapter {

    public AccountsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_account, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView usernameDisplay = view.findViewById(R.id.account_list_item_username);
        String username = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNTS_COLUMN_USERNAME));
        int type = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.ACCOUNTS_COLUMN_TYPE));
        if ( type == 0 ) {
            usernameDisplay.setText(username + " - Gym member");
        } else {
            usernameDisplay.setText(username + " - Instructor");
        }
    }
}
