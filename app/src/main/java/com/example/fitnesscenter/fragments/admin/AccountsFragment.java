package com.example.fitnesscenter.fragments.admin;

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
import com.example.fitnesscenter.database.AccountsAdapter;
import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.helper.Account;

import java.util.ArrayList;

public class AccountsFragment extends Fragment {

    private DBHelper database;
    private ArrayList<Account> accounts;
    private AccountsAdapter accountAdapter;

    ListView accountList;

    public static AccountsFragment newInstance(){
        return new AccountsFragment();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_accounts, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        // Initialize the database link
        database = new DBHelper(getActivity());

        // Find all associated views
        accountList = (ListView) getActivity().findViewById(R.id.accounts_list);

        // Filling the listview and activating its context menu
        accounts = database.getAllAccounts();
        accountAdapter = new AccountsAdapter(getActivity(), accounts);
        accountList.setAdapter(accountAdapter);
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
                // Move to the item selected
                Account user = accounts.get(index);
                // Delete the account
                int id_to_delete = user.getId();
                database.deleteAccount(id_to_delete);
                // Refresh the list
                accounts = database.getAllAccounts();
                accountAdapter = new AccountsAdapter(getActivity(), accounts);
                accountList.setAdapter(accountAdapter);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
