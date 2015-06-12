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

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.BasePredmetBean;
import com.example.anishchenko.ratingvolsu.beans.BaseStudentBean;
import com.example.anishchenko.ratingvolsu.beans.FacultBean;
import com.example.anishchenko.ratingvolsu.beans.GroupBean;
import com.example.anishchenko.ratingvolsu.beans.MarkBean;
import com.example.anishchenko.ratingvolsu.beans.StudentBean;
import com.example.anishchenko.ratingvolsu.db.DatabaseManager;
import com.example.anishchenko.ratingvolsu.fragments.BaseListFragment;
import com.example.anishchenko.ratingvolsu.fragments.GroupListFragment;
import com.example.anishchenko.ratingvolsu.fragments.InstituteListFragment;
import com.example.anishchenko.ratingvolsu.fragments.MainPageFragment;
import com.example.anishchenko.ratingvolsu.fragments.SemestrListFragment;
import com.example.anishchenko.ratingvolsu.fragments.StudentListFragment;
import com.example.anishchenko.ratingvolsu.requests.GetRatingOfGroupRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InputDataActivity extends BaseSpiceActivity implements BaseListFragment.IPageSelector {

    private ViewPager viewPager;
    private InputDataPagerAdapter mAdapter;
    private FacultBean selectedFacultet = null;
    private GroupBean selecteGroup = null;
    private SemestrListFragment.SemestrBean selectedSemesr = null;
    private StudentBean selectedStudent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_data_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.main_title);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new InputDataPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onItemSelected(int type, Object value) {
        int nextPosition = viewPager.getCurrentItem() + 1;
        switch (type) {
            case 0:
                selectedFacultet = (FacultBean) value;
                selecteGroup = null;
                selectedSemesr = null;
                selectedStudent = null;
                viewPager.setCurrentItem(nextPosition, true);
                if (mAdapter.registeredFragments.get(nextPosition) != null)
                    ((GroupListFragment) mAdapter.registeredFragments.get(nextPosition)).setFackId(selectedFacultet.Id);
                break;
            case 1:
                selecteGroup = (GroupBean) value;
                selectedSemesr = null;
                selectedStudent = null;
                viewPager.setCurrentItem(nextPosition, true);
                if (mAdapter.registeredFragments.get(nextPosition) != null)
                    ((SemestrListFragment) mAdapter.registeredFragments.get(nextPosition)).setGroup(selecteGroup);
                break;
            case 2:
                selectedSemesr = (SemestrListFragment.SemestrBean) value;
                selectedStudent = null;
                viewPager.setCurrentItem(nextPosition, true);
                if (mAdapter.registeredFragments.get(nextPosition) != null)
                    ((StudentListFragment) mAdapter.registeredFragments.get(nextPosition)).setGroup(selecteGroup);
                break;
            case 3:
                selectedStudent = (StudentBean) value;
                //TODO show FAB
                getSpiceManager().execute(new GetRatingOfGroupRequest(selectedFacultet.Id, selecteGroup.Id, String.valueOf(selectedSemesr.count)),
                        new RequestListener<JsonElement>() {
                            @Override
                            public void onRequestFailure(SpiceException spiceException) {

                            }

                            @Override
                            public void onRequestSuccess(JsonElement jsonElement) {
                                DatabaseManager dManager = DatabaseManager.INSTANCE;
                                ArrayList<BasePredmetBean> data = new ArrayList<>();
                                for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().get("Predmet").getAsJsonObject().entrySet()) {
                                    JsonObject object = entry.getValue().getAsJsonObject();
                                    data.add(new BasePredmetBean(object.get("Name").getAsString(), object.get("Type").getAsString(), entry.getKey()));
                                }
                                dManager.AddList(data, BasePredmetBean.class);
                                String id = selectedFacultet.Id + selecteGroup.Id + selectedSemesr.title;
                                dManager.addObject(new MarkBean(id), MarkBean.class);
                                ArrayList<BaseStudentBean> students = new ArrayList<>();
                                for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().get("Table").getAsJsonObject().entrySet()) {
                                    JsonObject object = entry.getValue().getAsJsonObject();
                                    String name = object.get("Name").getAsString();
                                    LinkedHashMap<String, String> allPredmets = new LinkedHashMap<>();
                                    for (Map.Entry<String, JsonElement> p_entry : object.get("Predmet").getAsJsonObject().entrySet()) {
                                        allPredmets.put(p_entry.getKey(), p_entry.getValue().getAsString());
                                    }
                                    students.add(new BaseStudentBean(name, allPredmets, id, id + name));
                                }
                                dManager.AddList(students, BaseStudentBean.class);
                                Intent i = new Intent(InputDataActivity.this, DetailInfoActivity.class);
                                i.putExtra("mark", String.valueOf(id));
                                startActivity(i);
                            }
                        });
                break;
        }
    }

    private class InputDataPagerAdapter extends FragmentPagerAdapter {
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public InputDataPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    InstituteListFragment instFragment = new InstituteListFragment();
                    registeredFragments.append(position, instFragment);
                    return instFragment;
                case 1:
                    GroupListFragment groupFragment = new GroupListFragment();
                    registeredFragments.append(position, groupFragment);
                    if (selectedFacultet != null)
                        groupFragment.setFackId(selectedFacultet.Id);
                    return groupFragment;
                case 2:
                    SemestrListFragment semestrFragment = new SemestrListFragment();
                    registeredFragments.append(position, semestrFragment);
                    if (selecteGroup != null)
                        semestrFragment.setGroup(selecteGroup);
                    return semestrFragment;
                case 3:
                    StudentListFragment studentFragment = new StudentListFragment();
                    registeredFragments.append(position, studentFragment);
                    if (selecteGroup != null)
                        studentFragment.setGroup(selecteGroup);
                    return studentFragment;
                default:
                    return new MainPageFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_title_inst);
                case 1:
                    return getString(R.string.tab_title_group);
                case 2:
                    return getString(R.string.tab_title_sem);
                case 3:
                default:
                    return getString(R.string.tab_title_number_z);

            }
        }

    }

}
