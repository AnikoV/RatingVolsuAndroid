package com.example.anishchenko.ratingvolsu.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.BaseStudentBean;
import com.example.anishchenko.ratingvolsu.beans.GroupBean;
import com.example.anishchenko.ratingvolsu.beans.MarkBean;
import com.example.anishchenko.ratingvolsu.db.DatabaseManager;
import com.example.anishchenko.ratingvolsu.utils.BaseRecyclerViewAdapter;
import com.example.anishchenko.ratingvolsu.utils.IListItemClick;
import com.example.anishchenko.ratingvolsu.utils.ToolBox;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends BaseListFragment implements IListItemClick {

    private SavedListAdapter mAdapter;
    private boolean isFavorite = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (IPageSelector) activity;
        mAdapter = new SavedListAdapter(activity, this);
    }

    public void setIsFavorite(boolean isFav) {
        this.isFavorite = isFav;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateData();
    }

    public void updateData() {
        if (mAdapter != null) {
            ArrayList<MarkBean> data = DatabaseManager.INSTANCE.getMarkList(isFavorite);
            mAdapter.setData(data.toArray(new MarkBean[data.size()]));
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        mListener.onItemSelected(0, mAdapter.getData()[position]);
    }

    public static class SavedListAdapter extends BaseRecyclerViewAdapter<MarkBean, ItemViewHolder> {


        public SavedListAdapter(Context context, IListItemClick listener) {
            super(context, listener);
            mData = new MarkBean[0];
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
            MarkBean bean = mData[position];
            holder.title.setText(bean.getTitile());
            holder.subtitle.setText(bean.getSubtitle());
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
