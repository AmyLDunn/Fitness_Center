package com.example.fitnesscenter.mainapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.databinding.FragmentAccountsBinding;
import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.helper.DBHelper;
import com.example.fitnesscenter.helper.PageViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountsListFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentAccountsBinding binding;

    private DBHelper database;

    public static AccountsListFragment newInstance(int index) {
        AccountsListFragment fragment = new AccountsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new DBHelper(getActivity());
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        ArrayList<Account> allAccounts = database.getAllAccounts();
        String[] accounts = new String[allAccounts.size()];
        for ( int i = 0; i < allAccounts.size(); i++ ) {
            accounts[i] = allAccounts.get(i).getUsername();
        }
        ListView listView = (ListView) view.findViewById(R.id.AccountsListView);
        ArrayAdapter<String> accountsListAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, accounts);
        listView.setAdapter(accountsListAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}