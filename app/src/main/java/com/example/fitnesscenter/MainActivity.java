package com.example.fitnesscenter;

import android.os.Bundle;

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
    private Account myAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        myAccount = (Account) bundle.getParcelable("USER_ACCOUNT");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewPager viewPager = binding.viewPager;

        FragmentPagerAdapter pagerAdapter = null;
        if ( myAccount.getType() == 2 ) {
            pagerAdapter = new AdminSectionsPagerAdapter(this, getSupportFragmentManager(), myAccount);
        } else if ( myAccount.getType() == 1 ) {
            pagerAdapter = new InstructorSectionsPagerAdapter(this, getSupportFragmentManager(), myAccount);
        } else {
            pagerAdapter = new MemberSectionsPagerAdapter(this, getSupportFragmentManager(), myAccount);
        }
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }
}