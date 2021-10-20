package com.example.fitnesscenter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnesscenter.databinding.ActivityMainBinding;
import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.ui.main.AdminSectionsPagerAdapter;
import com.example.fitnesscenter.ui.main.MemberSectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MemberActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Account myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        myAccount = (Account) bundle.getParcelable("USER_ACCOUNT");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MemberSectionsPagerAdapter memberSectionsPagerAdapter = new MemberSectionsPagerAdapter(this, getSupportFragmentManager(), myAccount);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(memberSectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
}