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
import com.example.anishchenko.ratingvolsu.beans.GroupBean;
import com.example.anishchenko.ratingvolsu.utils.BaseRecyclerViewAdapter;
import com.example.anishchenko.ratingvolsu.utils.IListItemClick;

/**
 * Created by m00n on 10.06.2015.
 */
public class SemestrListFragment extends BaseListFragment implements IListItemClick {

    private SemestrListAdapter mAdapter;
    private GroupBean mGroup;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAdapter = new SemestrListAdapter(activity, this);
        mListener = (IPageSelector) activity;
    }

    public void setGroup(GroupBean bean) {
        mGroup = bean;
        if (mGroup != null)
            refreshList();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGroup != null)
            refreshList();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onItemClick(View v, int position) {
        mListener.onItemSelected(2, mAdapter.getData()[position]);
    }

    private void refreshList() {
        SemestrBean[] data = new SemestrBean[mGroup.SemestrCount];
        int year = Integer.parseInt(mGroup.Year);
        for (int i = 0; i < data.length; i++) {
            data[i] = new SemestrBean(String.format(getString(R.string.semestr_format_title), i + 1), String.format("%d - %d", year + i / 2, year + 1 + i / 2), i + 1);
        }
        mAdapter.setData(data);
    }

    public static class SemestrListAdapter extends BaseRecyclerViewAdapter<SemestrBean, ItemViewHolder> {
        public SemestrListAdapter(Context context, IListItemClick listener) {
            super(context, listener);
            mData = new SemestrBean[0];
        }

        @Override
        public ItemViewHolder getHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.facultet_item, parent, false);
            return new ItemViewHolder(v);
        }

        @Override
        public void onBindHolder(ItemViewHolder holder, int position) {
            SemestrBean bean = mData[position];
            holder.title.setText(bean.title);
            holder.subtitle.setText(bean.subtitle);
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

    public static class SemestrBean {
        public String title;
        public String subtitle;
        public int count;

        public SemestrBean(String title, String subtitle, int count) {
            this.title = title;
            this.subtitle = subtitle;
            this.count = count;
        }
    }
}
