package com.example.anishchenko.ratingvolsu.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.BasePredmetBean;
import com.example.anishchenko.ratingvolsu.beans.BaseStudentBean;
import com.example.anishchenko.ratingvolsu.beans.MarkBean;
import com.example.anishchenko.ratingvolsu.db.DatabaseManager;
import com.example.anishchenko.ratingvolsu.requests.GetRatingOfStudentRequest;
import com.example.anishchenko.ratingvolsu.utils.BaseRecyclerViewAdapter;
import com.example.anishchenko.ratingvolsu.utils.IListItemClick;
import com.example.anishchenko.ratingvolsu.utils.ToolBox;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DetailedStudentRatingActivity extends BaseSpiceActivity implements IListItemClick {

    private StudentAdapterRating mAdapter;
    private BaseStudentBean student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_student_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        Toast.makeText(this, getString(R.string.tip_rotate_device), Toast.LENGTH_SHORT).show();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Рейтинг студента");
        RecyclerView recView = (RecyclerView) findViewById(R.id.recyclerview);
        recView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StudentAdapterRating(this, this, getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
        recView.setAdapter(mAdapter);
        student = DatabaseManager.INSTANCE.getObject(getIntent().getExtras().getString("student_id"), BaseStudentBean.class);
        ArrayList<BasePredmetBean> allPredmets = DatabaseManager.INSTANCE.getList(BasePredmetBean.class);
        LocalMarkBean[] marks = new LocalMarkBean[student.Predmet.size()];
        int all = 0;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || student.all_marks == null) {
            for (Map.Entry<String, String> entry : student.Predmet.entrySet()) {
                String predmetName = "";
                for (int i = 0; i < allPredmets.size(); i++) {
                    if (entry.getKey().equals(allPredmets.get(i).id)) {
                        predmetName = allPredmets.get(i).Name;
                        break;
                    }
                }
                String[] m = new String[6];
                m[5] = entry.getValue().replaceAll("\\(.+?\\)", "");
                marks[all] = new LocalMarkBean(predmetName, m);
                all++;
            }
        } else {
            for (Map.Entry<String, String[]> entry : student.all_marks.entrySet()) {
                String predmetName = "";
                for (int i = 0; i < allPredmets.size(); i++) {
                    if (entry.getKey().equals(allPredmets.get(i).id)) {
                        predmetName = allPredmets.get(i).Name;
                        break;
                    }
                }
                String[] m = new String[6];
                for (int i = 0; i < 6; i++)
                    m[i] = entry.getValue()[i].replaceAll("\\(.+?\\)", "");
                marks[all] = new LocalMarkBean(predmetName, m);
                all++;
            }
        }
        mAdapter.setData(marks);
        MarkBean mBean = DatabaseManager.INSTANCE.getObject(student.markId, MarkBean.class);
        getSpiceManager().execute(new GetRatingOfStudentRequest(mBean.getSelectedFacultet(), mBean.getSelectedGroup(), mBean.getSelectedSemestr(), mBean.getSelectedStudent()), new RequestListener<JsonElement>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(JsonElement jsonElement) {
                student.all_marks = new LinkedHashMap<>();
                for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().get("Table").getAsJsonObject().entrySet()) {
                    JsonArray object = entry.getValue().getAsJsonArray();
                    String[] m = new String[6];
                    for (int i = 0; i < 6; i++) {
                        m[i] = object.get(i).getAsString();
                    }
                    student.all_marks.put(entry.getKey(), m);
                }
                DatabaseManager.INSTANCE.addObject(student, BaseStudentBean.class);
            }
        });
    }

    @Override
    public void onItemClick(View v, int position) {

    }

    @Override
    public void onLongItemClick(View v, int position) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }


    public static class LocalMarkBean {
        private String predmetName;
        private String[] marks;

        public LocalMarkBean(String name, String[] marks) {
            this.predmetName = name;
            this.marks = marks;
        }

        public String getPredmetName() {
            return predmetName;
        }

        public String[] getMarks() {
            return marks;
        }

        public void setMarks(String[] marks) {
            this.marks = marks;
        }
    }

    public static class StudentAdapterRating extends BaseRecyclerViewAdapter<LocalMarkBean, StudentViewHolder> {

        private boolean needPadding;

        public StudentAdapterRating(Context context, IListItemClick listener, boolean needPadding) {
            super(context, listener);
            mData = new LocalMarkBean[0];
            this.needPadding = needPadding;
        }

        @Override
        public StudentViewHolder getHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.item_student_rating, parent, false);
            if (needPadding) {
                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) v.getLayoutParams();
                int margin = ToolBox.convertDpToPixel(15, inflater.getContext());
                if (viewType == 0) {
                    p.setMargins(margin, margin, margin, 0);
                } else if (viewType == 1) {
                    p.setMargins(margin, 0, margin, margin);
                } else {
                    p.setMargins(margin, 0, margin, 0);
                }
                v.setLayoutParams(p);
            }
            return new StudentViewHolder(v);
        }

        @Override
        public void onBindHolder(StudentViewHolder holder, int position) {
            LocalMarkBean bean = mData[position];
            holder.predmetName.setText(bean.getPredmetName());
            holder.mark6.setText(bean.getMarks()[5]);
            if (!needPadding) {
                holder.mark1.setText(bean.getMarks()[0]);
                holder.mark2.setText(bean.getMarks()[1]);
                holder.mark3.setText(bean.getMarks()[2]);
                holder.mark4.setText(bean.getMarks()[3]);
                holder.mark5.setText(bean.getMarks()[4]);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else if (position == mData.length - 1) {
                return 1;
            } else {
                return 2;
            }
        }

    }

    public static class StudentViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder {

        public TextView predmetName;
        public TextView mark1;
        public TextView mark2;
        public TextView mark3;
        public TextView mark4;
        public TextView mark5;
        public TextView mark6;

        public StudentViewHolder(View itemView) {
            super(itemView);
            predmetName = (TextView) itemView.findViewById(R.id.predmet_name);
            mark1 = (TextView) itemView.findViewById(R.id.predmet_mark_1);
            mark2 = (TextView) itemView.findViewById(R.id.predmet_mark_2);
            mark3 = (TextView) itemView.findViewById(R.id.predmet_mark_3);
            mark4 = (TextView) itemView.findViewById(R.id.predmet_mark_4);
            mark5 = (TextView) itemView.findViewById(R.id.predmet_mark_5);
            mark6 = (TextView) itemView.findViewById(R.id.predmet_mark);
        }
    }

}
