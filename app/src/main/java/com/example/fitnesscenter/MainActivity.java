package com.example.fitnesscenter;

import android.os.Bundle;

import com.example.fitnesscenter.database.SharedPreferencesManager;
import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.ui.main.InstructorSectionsPagerAdapter;
import com.example.fitnesscenter.ui.main.MemberSectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnesscenter.ui.main.AdminSectionsPagerAdapter;
import com.example.fitnesscenter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferencesManager SP = new SharedPreferencesManager(this);
        type = SP.getUserType();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewPager viewPager = binding.viewPager;

        FragmentPagerAdapter pagerAdapter = null;
        if ( type == 2 ) {
            pagerAdapter = new AdminSectionsPagerAdapter(this, getSupportFragmentManager());
        } else if ( type == 1 ) {
            pagerAdapter = new InstructorSectionsPagerAdapter(this, getSupportFragmentManager());
        } else {
            pagerAdapter = new MemberSectionsPagerAdapter(this, getSupportFragmentManager());
        }
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }
}