package com.example.fitnesscenter;

import android.os.Bundle;

import com.example.fitnesscenter.helper.Account;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesscenter.ui.main.AdminSectionsPagerAdapter;
import com.example.fitnesscenter.databinding.ActivityMainBinding;

public class AdminActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Account myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        myAccount = (Account) bundle.getParcelable("USER_ACCOUNT");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdminSectionsPagerAdapter adminSectionsPagerAdapter = new AdminSectionsPagerAdapter(this, getSupportFragmentManager(), myAccount);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(adminSectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
}