package com.sourcey.intelligentmedicalsystem.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/*
疾病详情fragment适配器
 */

public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments ;
    private List<String> mTitles ;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ?0:mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
