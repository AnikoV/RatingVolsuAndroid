package com.example.anishchenko.ratingvolsu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.BasePredmetBean;
import com.example.anishchenko.ratingvolsu.beans.BaseStudentBean;
import com.example.anishchenko.ratingvolsu.beans.MarkBean;
import com.example.anishchenko.ratingvolsu.db.DatabaseManager;
import com.example.anishchenko.ratingvolsu.dialogs.LoadingDialog;
import com.example.anishchenko.ratingvolsu.fragments.MainPageFragment;

import java.util.ArrayList;

public class MainActivity extends BaseSpiceActivity {

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
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        LoadingDialog d = new LoadingDialog();
        d.show(getSupportFragmentManager(), "loading");
    }

    public void FobOnClick(View view) {
        startActivity(new Intent(this, InputDataActivity.class));
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MainPageFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_title_favorite);
                case 1:
                default:
                    return getString(R.string.tab_title_last);
            }
        }

    }

}
