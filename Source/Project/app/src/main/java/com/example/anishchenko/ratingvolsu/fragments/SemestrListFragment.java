package com.example.anishchenko.ratingvolsu.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.activities.BaseSpiceActivity;
import com.example.anishchenko.ratingvolsu.beans.GroupBean;
import com.example.anishchenko.ratingvolsu.requests.GetGroupsRequest;
import com.example.anishchenko.ratingvolsu.requests.GetSemestrListRequest;
import com.example.anishchenko.ratingvolsu.utils.BaseRecyclerViewAdapter;
import com.example.anishchenko.ratingvolsu.utils.IListItemClick;
import com.example.anishchenko.ratingvolsu.utils.ToolBox;
import com.google.gson.JsonElement;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

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
        else {
            showSelectData();
            mAdapter.setData(new SemestrBean[0]);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGroup == null) {
            showSelectData();
            mAdapter.setData(new SemestrBean[0]);
        }
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onItemClick(View v, int position) {
        mListener.onItemSelected(2, mAdapter.getData()[position]);
        mAdapter.setSelectedPosition(position);
    }

    @Override
    public void onLongItemClick(View v, int position) {

    }

    private void refreshList() {
        errorText.setVisibility(View.INVISIBLE);
        ((BaseSpiceActivity) getActivity()).getSpiceManager().execute(new GetSemestrListRequest(mGroup.Id), new RequestListener<JsonElement>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Ошибка сервера", Toast.LENGTH_SHORT).show();
                showNoData();
            }

            @Override
            public void onRequestSuccess(JsonElement facultBeans) {
                progressBar.setVisibility(View.INVISIBLE);
                int count = facultBeans.getAsJsonArray().size();
                SemestrBean[] data = new SemestrBean[count];
                int year = Integer.parseInt(mGroup.Year);
                for (int i = 0; i < data.length; i++) {
                    data[i] = new SemestrBean(getRealSemestrNumber(i, mGroup.Type), String.format("%d - %d", year + i / 2, year + 1 + i / 2), i + 1);
                }
                mAdapter.setData(data);
            }
        });
    }

    private String getRealSemestrNumber(int number, String type) {
        int pos = number;
        if (type.equals("магистратура")) {
            pos += 9;
        } else {
            pos += 1;
        }
        return String.format(getString(R.string.semestr_format_title), pos);
    }

    public static class SemestrListAdapter extends BaseRecyclerViewAdapter<SemestrBean, ItemViewHolder> {
        public SemestrListAdapter(Context context, IListItemClick listener) {
            super(context, listener);
            mData = new SemestrBean[0];
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
            SemestrBean bean = mData[position];
            holder.title.setText(bean.title);
            holder.subtitle.setText(bean.subtitle);
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
