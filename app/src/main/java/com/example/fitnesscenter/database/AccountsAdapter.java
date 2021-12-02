package com.example.fitnesscenter.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.helper.Account;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Class that draws a link between a database cursor and a listview item
 * This displays the accounts in the database
 */
public class AccountsAdapter extends ArrayAdapter<Account> {

    public AccountsAdapter(Context context, ArrayList<Account> accounts) {
        super(context, 0, accounts);
    }

    /**
     * Fills one listview item with the information from one account
     * @param position in the list
     * @param convertView layout of the list item
     * @param parent parent layout
     * @return the filled in view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Account user = getItem(position);
        if ( convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_account, parent, false);
        }
        TextView usernameDisplay = convertView.findViewById(R.id.account_list_item_username);
        String username = user.getUsername();
        String type = Account.getTypeName(user.getType());
        usernameDisplay.setText(username+" - "+type);
        return convertView;
    }
}
