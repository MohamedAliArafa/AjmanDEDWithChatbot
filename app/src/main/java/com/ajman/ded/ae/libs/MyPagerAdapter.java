package com.ajman.ded.ae.libs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by root on 10/5/17.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private boolean isRTL;
    private List<Fragment> mFragments;
    private String[] fragmentsTitles;

    public MyPagerAdapter(List<Fragment> mFragments, String[] fragmentsTitles, FragmentManager fragmentManager) {
        super(fragmentManager);
        Collections.reverse(Collections.singletonList(mFragments));
        Collections.reverse(Arrays.asList(fragmentsTitles));
        isRTL = Locale.getDefault().toString().contains("ar");
        this.mFragments = mFragments;
        this.fragmentsTitles = fragmentsTitles;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return mFragments.size();
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitles[position];
    }
}