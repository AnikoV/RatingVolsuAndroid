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
import com.example.anishchenko.ratingvolsu.requests.GetGroupsRequest;
import com.example.anishchenko.ratingvolsu.utils.BaseRecyclerViewAdapter;
import com.example.anishchenko.ratingvolsu.utils.IListItemClick;
import com.example.anishchenko.ratingvolsu.utils.ToolBox;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by m00n on 10.06.2015.
 */
public class GroupListFragment extends BaseListFragment implements IListItemClick {

    private GroupListAdapter mAdapter;
    private String fakId;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAdapter = new GroupListAdapter(activity, this);
        mListener = (IPageSelector) activity;
    }

    public void setFackId(String id) {
        this.fakId = id;
        if (fakId != null && !fakId.equals(""))
            refreshList();
        else {
            errorText.setVisibility(View.VISIBLE);
            mAdapter.setData(new GroupBean[0]);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (fakId == null || fakId.equals("")) {
            errorText.setVisibility(View.VISIBLE);
            mAdapter.setData(new GroupBean[0]);
        }
    }

    private void refreshList() {
        errorText.setVisibility(View.INVISIBLE);
        mAdapter.setData(new GroupBean[0]);
        ((BaseSpiceActivity) getActivity()).getSpiceManager().execute(new GetGroupsRequest(fakId), new RequestListener<GroupBean[]>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(GroupBean[] facultBeans) {
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
        mListener.onItemSelected(1, mAdapter.getData()[position]);
        mAdapter.setSelectedPosition(position);
    }

    public static class GroupListAdapter extends BaseRecyclerViewAdapter<GroupBean, ItemViewHolder> {


        public GroupListAdapter(Context context, IListItemClick listener) {
            super(context, listener);
            mData = new GroupBean[0];
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
            GroupBean bean = mData[position];
            holder.title.setText(bean.Name);
            holder.subtitle.setText(bean.Type);
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
