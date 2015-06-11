package com.example.anishchenko.ratingvolsu.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.BaseStudentBean;
import com.example.anishchenko.ratingvolsu.db.DatabaseManager;
import com.example.anishchenko.ratingvolsu.utils.BaseRecyclerViewAdapter;
import com.example.anishchenko.ratingvolsu.utils.IListItemClick;

import java.util.ArrayList;

/**
 * Created by m00n on 11.06.2015.
 */
public class GroupRatingFragment extends BaseListFragment implements IListItemClick {

    private GroupAdapterRating mAdapter;
    private BaseStudentBean[] array;
    private String type;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (IPageSelector) activity;
        mAdapter = new GroupAdapterRating(activity, this);
        mAdapter.setData(array);
        mAdapter.setType(type);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public void setData(String type, String mark_set) {
        ArrayList<BaseStudentBean> students = DatabaseManager.INSTANCE.getStudentList(mark_set);
        array = students.toArray(new BaseStudentBean[students.size()]);
        this.type = type;
    }

    @Override
    public void onItemClick(View v, int position) {

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
            return new GroupViewHolder(v);
        }

        @Override
        public void onBindHolder(GroupViewHolder holder, int position) {
            BaseStudentBean bean = mData[position];
            holder.number.setText(String.format("#%d", position + 1));
            holder.student.setText(bean.Name);
            holder.mark.setText(bean.Predmet.get(type));
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
