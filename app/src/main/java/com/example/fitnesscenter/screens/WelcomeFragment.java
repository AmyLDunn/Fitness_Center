package com.example.fitnesscenter.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.database.SharedPreferencesManager;
import com.example.fitnesscenter.helper.Account;

public class WelcomeFragment extends Fragment {

    String username;
    String type;

    public static WelcomeFragment newInstance(){
        WelcomeFragment fragment = new WelcomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        SharedPreferencesManager SP = new SharedPreferencesManager(getContext());
        username = SP.getUsername();
        type = Account.getTypeName(SP.getUserType());

        TextView title = (TextView) getActivity().findViewById(R.id.welcome_username);
        title.setText("Welcome, "+username+"!");
        TextView titleTwo = (TextView) getActivity().findViewById(R.id.welcome_account_type);
        titleTwo.setText("You are logged in as "+type+".");
    }
}
