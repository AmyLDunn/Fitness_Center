package com.example.fitnesscenter.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fitnesscenter.fragments.WelcomeFragment;
import com.example.fitnesscenter.fragments.admin.AccountsFragment;
import com.example.fitnesscenter.fragments.admin.ClassTypesFragment;
import com.example.fitnesscenter.R;
import com.example.fitnesscenter.helper.Account;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class AdminSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private Account myAccount;

    public AdminSectionsPagerAdapter(Context context, FragmentManager fm, Account myAccount) {
        super(fm);
        mContext = context;
        this.myAccount = myAccount;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = WelcomeFragment.newInstance(myAccount);
                break;
            case 1:
                fragment = ClassTypesFragment.newInstance();
                break;
            case 2:
                fragment = AccountsFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}