package com.example.fitnesscenter.mainapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.R;
import com.example.fitnesscenter.databinding.FragmentWelcomeBinding;
import com.example.fitnesscenter.helper.PageViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class WelcomeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentWelcomeBinding binding;

    private Account userAccount;

    public static WelcomeFragment newInstance(int index, Account user) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putParcelable("USER_ACCOUNT", user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        userAccount = (Account) getArguments().getParcelable("USER_ACCOUNT");

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView usernameDisplay = (TextView) getView().findViewById(R.id.welcome_fragment_username_label);
        usernameDisplay.setText("Username: "+userAccount.getUsername());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}