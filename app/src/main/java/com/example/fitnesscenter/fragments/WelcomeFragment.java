package com.example.fitnesscenter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.helper.Account;

public class WelcomeFragment extends Fragment {

    Account myAccount;

    public static WelcomeFragment newInstance(Account myAccount){
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("USER_ACCOUNT", myAccount);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        myAccount = bundle.getParcelable("USER_ACCOUNT");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        TextView title = (TextView) getActivity().findViewById(R.id.welcome_username);
        title.setText("Welcome, "+myAccount.getUsername()+"!");
        TextView titleTwo = (TextView) getActivity().findViewById(R.id.welcome_account_type);
        titleTwo.setText("You are logged in as "+myAccount.getTypeName()+".");
    }
}
