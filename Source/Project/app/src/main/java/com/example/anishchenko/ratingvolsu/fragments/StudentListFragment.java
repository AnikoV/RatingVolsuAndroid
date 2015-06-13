package com.example.anishchenko.ratingvolsu.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.activities.BaseSpiceActivity;
import com.example.anishchenko.ratingvolsu.beans.GroupBean;
import com.example.anishchenko.ratingvolsu.beans.StudentBean;
import com.example.anishchenko.ratingvolsu.requests.GetStudentListRequest;
import com.example.anishchenko.ratingvolsu.utils.BaseRecyclerViewAdapter;
import com.example.anishchenko.ratingvolsu.utils.IListItemClick;
import com.example.anishchenko.ratingvolsu.utils.ToolBox;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by m00n on 10.06.2015.
 */
public class StudentListFragment extends BaseListFragment implements IListItemClick {
    private StudentListAdapter mAdapter;
    private GroupBean mGroup;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (IPageSelector) activity;
        mAdapter = new StudentListAdapter(activity, this);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onItemClick(View v, int position) {
        mListener.onItemSelected(3, mAdapter.getData()[position]);
        mAdapter.setSelectedPosition(position);
    }

    @Override
    public void onLongItemClick(View v, int position) {

    }

    public void setGroup(GroupBean bean) {
        this.mGroup = bean;
        if (mGroup != null)
            refreshList();
        else {
            errorText.setVisibility(View.VISIBLE);
            mAdapter.setData(new StudentBean[0]);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGroup == null) {
            errorText.setVisibility(View.VISIBLE);
            mAdapter.setData(new StudentBean[0]);
        }
    }

    private void refreshList() {
        if (mAdapter != null) {
            errorText.setVisibility(View.INVISIBLE);
            mAdapter.setData(new StudentBean[0]);
            ((BaseSpiceActivity) getActivity()).getSpiceManager().execute(new GetStudentListRequest(mGroup.Id), new RequestListener<StudentBean[]>() {
                @Override
                public void onRequestFailure(SpiceException spiceException) {

                }

                @Override
                public void onRequestSuccess(StudentBean[] facultBeans) {
                    mAdapter.setData(facultBeans);
                }
            });
        }
    }

    public static class StudentListAdapter extends BaseRecyclerViewAdapter<StudentBean, ItemViewHolder> {
        public StudentListAdapter(Context context, IListItemClick listener) {
            super(context, listener);
            mData = new StudentBean[0];
        }

        @Override
        public ItemViewHolder getHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.facultet_item, parent, false);
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
            return new ItemViewHolder(v);
        }

        @Override
        public void onBindHolder(ItemViewHolder holder, int position) {
            StudentBean bean = mData[position];
            holder.title.setText(bean.Number.replaceAll("\\D+", ""));
            holder.subtitle.setVisibility(View.GONE);
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

    public static class ItemViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder {

        public TextView title;
        public TextView subtitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.facultet_item_title);
            subtitle = (TextView) itemView.findViewById(R.id.facultet_item_subtitle);
        }
    }
}
