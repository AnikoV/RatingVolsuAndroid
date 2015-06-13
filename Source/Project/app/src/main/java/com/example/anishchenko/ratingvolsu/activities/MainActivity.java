package com.example.anishchenko.ratingvolsu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.MarkBean;
import com.example.anishchenko.ratingvolsu.db.DatabaseManager;
import com.example.anishchenko.ratingvolsu.fragments.BaseListFragment;
import com.example.anishchenko.ratingvolsu.fragments.MainPageFragment;

public class MainActivity extends BaseSpiceActivity implements BaseListFragment.IPageSelector {

    private MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager.INSTANCE.init(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setTitle(R.string.main_title);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    protected void onResume() {
        super.onResume();
        if (mAdapter.registeredFragments.get(0) != null)
            mAdapter.registeredFragments.get(0).updateData();
        if (mAdapter.registeredFragments.get(1) != null)
            mAdapter.registeredFragments.get(1).updateData();
    }

    public void FobOnClick(View view) {
        startActivity(new Intent(this, InputDataActivity.class));
    }

    @Override
    public void onItemSelected(int type, Object value) {
        Intent i = new Intent(this, DetailInfoActivity.class);
        MarkBean b = (MarkBean) value;
        i.putExtra("mark", b.getId());
        i.putExtra("student", b.getSavedStudent());
        startActivity(i);
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {
        SparseArray<MainPageFragment> registeredFragments = new SparseArray<>();

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MainPageFragment f = new MainPageFragment();
            f.setIsFavorite(position == 1);
            registeredFragments.append(position, f);
            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return getString(R.string.tab_title_favorite);
                case 0:
                default:
                    return getString(R.string.tab_title_last);
            }
        }

    }

}
