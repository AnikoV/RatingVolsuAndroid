package com.example.anishchenko.ratingvolsu.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by m00n on 05.06.2015.
 */
public abstract class BaseRecyclerViewAdapter<T, H extends BaseRecyclerViewAdapter.BaseViewHolder> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {

    protected T[] mData;
    private LayoutInflater mInflater;
    private IListItemClick mListener;

    public BaseRecyclerViewAdapter(Context context, IListItemClick listener) {
        mListener = listener;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = getHolder(mInflater, parent, viewType);
        holder.setListener(mListener);
        return holder;
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setPosition(position);
        onBindHolder((H) holder, position);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public T[] getData() {
        return mData;
    }

    public void setData(T[] data) {
        mData = data.clone();
        notifyDataSetChanged();
    }

    public abstract H getHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    public abstract void onBindHolder(H holder, int position);

    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected IListItemClick mListener;
        private int position;

        public BaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void setPosition(int pos) {
            position = pos;
        }

        public void setListener(IListItemClick listener) {
            mListener = listener;
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(view, position);
        }
    }
}
