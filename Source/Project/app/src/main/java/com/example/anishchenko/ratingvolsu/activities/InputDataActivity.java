package com.example.anishchenko.ratingvolsu.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;

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
import com.melnykov.fab.FloatingActionButton;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class InputDataActivity extends BaseSpiceActivity implements BaseListFragment.IPageSelector, View.OnClickListener {

    private ViewPager viewPager;
    private InputDataPagerAdapter mAdapter;
    private FacultBean selectedFacultet = null;
    private GroupBean selecteGroup = null;
    private SemestrListFragment.SemestrBean selectedSemesr = null;
    private StudentBean selectedStudent = null;
    private FloatingActionButton fab;
    private ProgressDialog dialog;

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
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide(false);
        fab.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new InputDataPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
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
                fab.hide(true);
                break;
            case 1:
                selecteGroup = (GroupBean) value;
                selectedSemesr = null;
                selectedStudent = null;
                viewPager.setCurrentItem(nextPosition, true);
                if (mAdapter.registeredFragments.get(nextPosition) != null)
                    ((SemestrListFragment) mAdapter.registeredFragments.get(nextPosition)).setGroup(selecteGroup);
                fab.hide(true);
                break;
            case 2:
                selectedSemesr = (SemestrListFragment.SemestrBean) value;
                selectedStudent = null;
                viewPager.setCurrentItem(nextPosition, true);
                if (mAdapter.registeredFragments.get(nextPosition) != null)
                    ((StudentListFragment) mAdapter.registeredFragments.get(nextPosition)).setGroup(selecteGroup);
                fab.hide(true);
                break;
            case 3:
                selectedStudent = (StudentBean) value;
                fab.show(true);
                break;
        }
        invalidateTabs();
    }

    private void invalidateTabs() {
        if (selectedFacultet == null && mAdapter.registeredFragments.get(1) != null)
            ((GroupListFragment) mAdapter.registeredFragments.get(1)).setFackId("");
        if (selecteGroup == null && mAdapter.registeredFragments.get(2) != null)
            ((SemestrListFragment) mAdapter.registeredFragments.get(2)).setGroup(null);
        if (selectedSemesr == null && mAdapter.registeredFragments.get(3) != null)
            ((StudentListFragment) mAdapter.registeredFragments.get(3)).setGroup(null);
    }

    @Override
    public void onClick(View v) {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.data_loading_text));
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                getSpiceManager().cancelAllRequests();
            }
        });
        dialog.show();
        getSpiceManager().execute(new GetRatingOfGroupRequest(selectedFacultet.Id, selecteGroup.Id, String.valueOf(selectedSemesr.count)),
                new RequestListener<JsonElement>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onRequestSuccess(JsonElement jsonElement) {
                        DatabaseManager dManager = DatabaseManager.INSTANCE;
                        ArrayList<BasePredmetBean> data = new ArrayList<>();
                        if (jsonElement.getAsJsonObject().get("Predmet") == null)
                            return;
                        for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().get("Predmet").getAsJsonObject().entrySet()) {
                            JsonObject object = entry.getValue().getAsJsonObject();
                            data.add(new BasePredmetBean(object.get("Name").getAsString(), object.get("Type").getAsString(), entry.getKey()));
                        }
                        String gropPrefix = selecteGroup.Name.replaceAll("\\d","");
                        dManager.AddList(data, BasePredmetBean.class);
                        String id = selectedFacultet.Id + selecteGroup.Id + selectedSemesr.title;
                        MarkBean markBean = new MarkBean(id);
                        markBean.setSelectedFacultet(selectedFacultet.Id);
                        markBean.setSelectedGroup(selecteGroup.Id);
                        markBean.setSelectedSemestr(String.valueOf(selectedSemesr.count));
                        markBean.setSelectedStudent(selectedStudent.Id);
                        markBean.setTitile("Студент " + selectedStudent.Number);
                        markBean.setSubtitle(selecteGroup.Name + " " + selectedSemesr.title);
                        markBean.setSavedStudent(id + gropPrefix + selectedStudent.Number);
                        dManager.addObject(markBean, MarkBean.class);
                        ArrayList<BaseStudentBean> students = new ArrayList<>();
                        for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().get("Table").getAsJsonObject().entrySet()) {
                            JsonObject object = entry.getValue().getAsJsonObject();
                            String name = gropPrefix + object.get("Name").getAsString();
                            LinkedHashMap<String, String> allPredmets = new LinkedHashMap<>();
                            for (Map.Entry<String, JsonElement> p_entry : object.get("Predmet").getAsJsonObject().entrySet()) {
                                allPredmets.put(p_entry.getKey(), p_entry.getValue().getAsString());
                            }
                            students.add(new BaseStudentBean(name, allPredmets, id, id + name));
                        }
                        dManager.AddList(students, BaseStudentBean.class);
                        Intent i = new Intent(InputDataActivity.this, DetailInfoActivity.class);
                        i.putExtra("mark", String.valueOf(id));
                        i.putExtra("student", id + gropPrefix + selectedStudent.Number);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });
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
