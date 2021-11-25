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

    /**
     * This method simply inflates (expands and adds) the list_item_account.xml format
     * and adds a single item to a ListView
     * @param context is a link to the overall application
     * @param cursor is the cursor linked to the database
     * @param parent is the parent viewgroup that the new layout can be attached to
     * @return the view that contains one item from the database
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_account, parent, false);
    }

    /**
     * This takes the inflated view from newView above and fills in any data of the list item
     * according the to information in the cursor row
     * @param view This is the list_item_account.xml that was added
     * @param context is a link to the overall application
     * @param cursor This is the cursor link to the database
     */
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
