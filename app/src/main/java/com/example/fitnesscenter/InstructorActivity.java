package com.example.fitnesscenter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.fitnesscenter.databinding.ActivityMainBinding;
import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.ui.main.AdminSectionsPagerAdapter;
import com.example.fitnesscenter.ui.main.InstructorSectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class InstructorActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Account myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        myAccount = (Account) bundle.getParcelable("USER_ACCOUNT");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InstructorSectionsPagerAdapter instructorSectionsPagerAdapter = new InstructorSectionsPagerAdapter(this, getSupportFragmentManager(), myAccount);
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(instructorSectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
}