package com.example.anishchenko.ratingvolsu.utils;

import android.view.View;

/**
 * Created by m00n on 05.06.2015.
 */
public interface IListItemClick {
    void onItemClick(View v, int position);
    void onLongItemClick(View v,int position);
}
