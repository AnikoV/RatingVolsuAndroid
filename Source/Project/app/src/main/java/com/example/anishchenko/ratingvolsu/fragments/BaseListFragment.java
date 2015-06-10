package com.example.anishchenko.ratingvolsu.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anishchenko.ratingvolsu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseListFragment extends Fragment {

    protected IPageSelector mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(getAdapter());
    }

    public abstract RecyclerView.Adapter getAdapter();

    public interface IPageSelector {
        void onItemSelected(int type, Object value);
    }

}
