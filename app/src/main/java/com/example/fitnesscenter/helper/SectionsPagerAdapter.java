package com.example.fitnesscenter.helper;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fitnesscenter.R;
import com.example.fitnesscenter.mainapp.fragments.AccountsListFragment;
import com.example.fitnesscenter.mainapp.fragments.ClassTypesListFragment;
import com.example.fitnesscenter.mainapp.fragments.WelcomeFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_welcome, R.string.tab_text_class_types, R.string.tab_text_accounts};
    private final Context mContext;

    private Account userAccount;

    public SectionsPagerAdapter(Context context, FragmentManager fm, Account userAccount) {
        super(fm);
        mContext = context;
        this.userAccount = userAccount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return WelcomeFragment.newInstance(1, userAccount);
            case 1:
                return ClassTypesListFragment.newInstance(2);
            case 2:
                return AccountsListFragment.newInstance(3);
            default:
                return null;
        }
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