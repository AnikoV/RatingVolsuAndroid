package com.example.anishchenko.ratingvolsu.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.BaseStudentBean;
import com.example.anishchenko.ratingvolsu.db.DatabaseManager;
import com.example.anishchenko.ratingvolsu.utils.BaseRecyclerViewAdapter;
import com.example.anishchenko.ratingvolsu.utils.IListItemClick;
import com.example.anishchenko.ratingvolsu.utils.ToolBox;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by m00n on 11.06.2015.
 */
public class GroupRatingFragment extends BaseListFragment implements IListItemClick {

    private GroupAdapterRating mAdapter;
    private BaseStudentBean[] array;
    private String type;
    private FloatingActionButton fab;
    private String title = "";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (IPageSelector) activity;
        mAdapter = new GroupAdapterRating(activity, this);
        mAdapter.setData(array);
        mAdapter.setType(type);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab.attachToRecyclerView(recView);
        if (!ToolBox.isEmpty(title)) {
            headerText.setText(title);
            headerText.setVisibility(View.VISIBLE);
        }
    }

    public void setFAB(FloatingActionButton fab) {
        this.fab = fab;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void setData(final String type, String mark_set, String title) {
        this.title = title;
        ArrayList<BaseStudentBean> students = DatabaseManager.INSTANCE.getStudentList(mark_set);
        Collections.sort(students, new Comparator<BaseStudentBean>() {
            @Override
            public int compare(BaseStudentBean lhs, BaseStudentBean rhs) {
                int f, s;
                if (type.equals("all")) {
                    f = 0;
                    s = 0;
                    for (Map.Entry<String, String> enty : lhs.Predmet.entrySet()) {
                        f += Integer.parseInt(enty.getValue().replaceAll("\\(.+?\\)", ""));
                    }
                    for (Map.Entry<String, String> enty : rhs.Predmet.entrySet()) {
                        s += Integer.parseInt(enty.getValue().replaceAll("\\(.+?\\)", ""));
                    }
                } else {
                    f = Integer.parseInt(lhs.Predmet.get(type).replaceAll("\\(.+?\\)", ""));
                    s = Integer.parseInt(rhs.Predmet.get(type).replaceAll("\\(.+?\\)", ""));
                }
                return s - f;
            }
        });
        array = students.toArray(new BaseStudentBean[students.size()]);
        this.type = type;
    }

    @Override
    public void onItemClick(View v, int position) {

    }

    @Override
    public void onLongItemClick(View v, int position) {

    }

    public static class GroupAdapterRating extends BaseRecyclerViewAdapter<BaseStudentBean, GroupViewHolder> {

        private String type;

        public GroupAdapterRating(Context context, IListItemClick listener) {
            super(context, listener);
            mData = new BaseStudentBean[0];
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public GroupViewHolder getHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.item_group_rating_layout, parent, false);
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
            return new GroupViewHolder(v);
        }

        @Override
        public void onBindHolder(GroupViewHolder holder, int position) {
            BaseStudentBean bean = mData[position];
            holder.number.setText(String.format("#%d", position + 1));
            holder.student.setText(bean.Name);
            if (type.equals("all")) {
                int sum = 0;
                for (Map.Entry<String, String> enty : bean.Predmet.entrySet()) {
                    sum += Integer.parseInt(enty.getValue().replaceAll("\\(.+?\\)", ""));
                }
                holder.mark.setText(String.valueOf(sum));
                return;
            }
            holder.mark.setText(bean.Predmet.get(type).replaceAll("\\(.+?\\)", ""));
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

    public static class GroupViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder {
        public TextView number;
        public TextView student;
        public TextView mark;

        public GroupViewHolder(View itemView) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.item_group_number);
            student = (TextView) itemView.findViewById(R.id.item_group_student);
            mark = (TextView) itemView.findViewById(R.id.item_group_mark);

        }
    }
}
