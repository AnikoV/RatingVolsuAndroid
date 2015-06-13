package com.example.anishchenko.ratingvolsu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.BasePredmetBean;
import com.example.anishchenko.ratingvolsu.beans.BaseStudentBean;
import com.example.anishchenko.ratingvolsu.beans.MarkBean;
import com.example.anishchenko.ratingvolsu.db.DatabaseManager;
import com.example.anishchenko.ratingvolsu.fragments.BaseListFragment;
import com.example.anishchenko.ratingvolsu.fragments.GroupRatingFragment;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.SortedMap;

public class DetailInfoActivity extends BaseSpiceActivity implements BaseListFragment.IPageSelector {

    private ViewPager viewPager;
    private GroupRetingPagerAdapter mAdapter;
    private FloatingActionButton fab;
    private MarkBean mark;

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
        mark = DatabaseManager.INSTANCE.getObject(getIntent().getExtras().getString("mark"), MarkBean.class);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        if(mark.isFavorite())
        {
            fab.hide(true);
        }
        else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mark.setIsFavorite(true);
                    DatabaseManager.INSTANCE.addObject(mark, MarkBean.class);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.show_person) {
            Intent i = new Intent(this, DetailedStudentRatingActivity.class);
            i.putExtra("student_id", getIntent().getExtras().getString("student"));
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            fragment.setFAB(fab);
            if (position == 0) {
                fragment.setData("all", markSet);
                return fragment;
            }
            int cPos = 0;
            for (String key : data.Predmet.keySet()) {
                if (cPos == position - 1) {
                    fragment.setData(key, markSet);
                    break;
                }
                cPos++;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return data.Predmet.size() + 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
                return "Общий рейтинг";
            int cPos = 0;
            for (String key : data.Predmet.keySet()) {
                if (cPos == position - 1) {
                    return DatabaseManager.INSTANCE.getObject(key, BasePredmetBean.class).Name;
                }
                cPos++;
            }
            return "Unknow";
        }

    }
}
