package com.example.fitnesscenter.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.AccountsCursorAdapter;
import com.example.fitnesscenter.database.DBHelper;

public class AccountsFragment extends Fragment {

    private DBHelper database;
    Cursor accountCursor;
    AccountsCursorAdapter cursorAdapter;

    public static AccountsFragment newInstance(){
        return new AccountsFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accounts, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        database = new DBHelper(getActivity());
        accountCursor = database.getAllAccounts();
        ListView accountList = (ListView) getActivity().findViewById(R.id.accounts_list);
        cursorAdapter = new AccountsCursorAdapter(getActivity(), accountCursor);
        accountList.setAdapter(cursorAdapter);
        registerForContextMenu(accountList);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.accounts_context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)  item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()){
            case R.id.account_option_delete:
                accountCursor.moveToPosition(index);
                int id_to_delete = accountCursor.getInt(accountCursor.getColumnIndexOrThrow(DBHelper.ACCOUNTS_COLUMN_ID));
                database.deleteAccount(id_to_delete);
                accountCursor = database.getAllAccounts();
                cursorAdapter.changeCursor(accountCursor);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
