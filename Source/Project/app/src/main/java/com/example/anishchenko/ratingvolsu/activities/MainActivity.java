package com.example.anishchenko.ratingvolsu.activities;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.FacultBean;
import com.example.anishchenko.ratingvolsu.requests.GetFacultsRequest;
import com.google.gson.JsonElement;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;

import retrofit.android.AndroidLog;


public class MainActivity extends BaseSpiceActivity {

    private SpiceManager _spiceManager;
    private GetFacultsRequest _facultsRequest;
    private GetFacultsListener _facultsRequestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _facultsRequest = new GetFacultsRequest();
        _facultsRequestListener = new GetFacultsListener();
        _spiceManager = getSpiceManager();
        _spiceManager.execute(_facultsRequest,_facultsRequestListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetFacultsListener implements RequestListener<JsonElement>
    {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            AndroidLog log = new AndroidLog("MYLOG");
            log.log(spiceException.getCause().getLocalizedMessage());
            TextView textView =  (TextView)findViewById(R.id.Result);
            textView.setText(spiceException.getLocalizedMessage());
        }

        @Override
        public void onRequestSuccess(JsonElement bean) {
            TextView textView =  (TextView)findViewById(R.id.Result);


        }
    }


}
