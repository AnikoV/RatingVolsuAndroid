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
import com.example.anishchenko.ratingvolsu.beans.FacultBean;
import com.example.anishchenko.ratingvolsu.requests.GetFacultsRequest;
import com.example.anishchenko.ratingvolsu.utils.BaseRecyclerViewAdapter;
import com.example.anishchenko.ratingvolsu.utils.IListItemClick;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by m00n on 10.06.2015.
 */
public class InstituteListFragment extends BaseListFragment implements IListItemClick {

    private InstituteListAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAdapter = new InstituteListAdapter(activity, this);
        mListener = (IPageSelector) activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((BaseSpiceActivity) getActivity()).getSpiceManager().execute(new GetFacultsRequest(), new RequestListener<FacultBean[]>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(FacultBean[] facultBeans) {
                mAdapter.setData(facultBeans);
            }
        });
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onItemClick(View v, int position) {
        mListener.onItemSelected(0, mAdapter.getData()[position]);
    }

    public static class InstituteListAdapter extends BaseRecyclerViewAdapter<FacultBean, ItemViewHolder> {
        private final Pattern pattern;

        public InstituteListAdapter(Context context, IListItemClick listener) {
            super(context, listener);
            pattern = Pattern.compile("(.+?)\\((.+?)\\)");
            mData = new FacultBean[0];
        }

        @Override
        public ItemViewHolder getHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.facultet_item, parent, false);
            return new ItemViewHolder(v);
        }

        @Override
        public void onBindHolder(ItemViewHolder holder, int position) {
            FacultBean bean = mData[position];
            Matcher m = pattern.matcher(bean.Name);
            if (m.find()) {
                holder.title.setText(m.group(1));
                holder.subtitle.setText(m.group(2));
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
