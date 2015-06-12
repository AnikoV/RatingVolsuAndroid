package com.example.anishchenko.ratingvolsu.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.BasePredmetBean;
import com.example.anishchenko.ratingvolsu.beans.BaseStudentBean;
import com.example.anishchenko.ratingvolsu.db.DatabaseManager;
import com.example.anishchenko.ratingvolsu.fragments.BaseListFragment;
import com.example.anishchenko.ratingvolsu.fragments.GroupRatingFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.SortedMap;

public class DetailInfoActivity extends BaseSpiceActivity implements BaseListFragment.IPageSelector {

    private ViewPager viewPager;
    private GroupRetingPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Рейтинг группы");
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (getIntent().getExtras() != null) {
            ArrayList<BaseStudentBean> data = DatabaseManager.INSTANCE.getStudentList(getIntent().getExtras().getString("mark"));
            mAdapter = new GroupRetingPagerAdapter(getSupportFragmentManager(), data.size() > 0 ? data.get(0) : null, getIntent().getExtras().getString("mark"));
            viewPager.setAdapter(mAdapter);
            tablayout.setupWithViewPager(viewPager);
            tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    @Override
    public void onItemSelected(int type, Object value) {

    }

    private class GroupRetingPagerAdapter extends FragmentPagerAdapter {
        private BaseStudentBean data;
        private String markSet;

        public GroupRetingPagerAdapter(FragmentManager fm, BaseStudentBean data, String markSet) {
            super(fm);
            this.markSet = markSet;
            if (data == null) {
                this.data = new BaseStudentBean("", new LinkedHashMap<String, String>(), "", "");
            } else {
                this.data = data;
            }
        }

        @Override
        public Fragment getItem(int position) {
            GroupRatingFragment fragment = new GroupRatingFragment();
            int cPos = 0;
            for (String key : data.Predmet.keySet()) {
                if (cPos == position) {
                    fragment.setData(key, markSet);
                    break;
                }
                cPos++;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return data.Predmet.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int cPos = 0;
            for (String key : data.Predmet.keySet()) {
                if (cPos == position) {
                    return DatabaseManager.INSTANCE.getObject(key, BasePredmetBean.class).Name;
                }
                cPos++;
            }
            return "Unknow";
        }

    }
}
