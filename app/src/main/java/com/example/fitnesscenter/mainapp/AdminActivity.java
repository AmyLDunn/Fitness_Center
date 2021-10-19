package com.example.fitnesscenter.mainapp;

import android.os.Bundle;

import com.example.fitnesscenter.helper.Account;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesscenter.helper.SectionsPagerAdapter;
import com.example.fitnesscenter.databinding.ActivityMainBinding;

public class AdminActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Account userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle data = getIntent().getExtras();
        userAccount = (Account) data.getParcelable("USER_ACCOUNT");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), userAccount);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
}