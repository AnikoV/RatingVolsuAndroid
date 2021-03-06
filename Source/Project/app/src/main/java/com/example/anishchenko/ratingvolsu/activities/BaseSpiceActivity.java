package com.example.anishchenko.ratingvolsu.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;

import com.example.anishchenko.ratingvolsu.loaders.RatingSpiceService;
import com.octo.android.robospice.SpiceManager;

public class BaseSpiceActivity extends ActionBarActivity {

    private SpiceManager spiceManager = new SpiceManager(RatingSpiceService.class);

    @Override
    public void onStart () {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    public void onStop () {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    public SpiceManager getSpiceManager () {
        return spiceManager;
    }
}
