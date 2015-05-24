package com.example.anishchenko.ratingvolsu.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.anishchenko.ratingvolsu.R;
import com.example.anishchenko.ratingvolsu.beans.FacultBean;
import com.example.anishchenko.ratingvolsu.beans.GroupBean;
import com.example.anishchenko.ratingvolsu.beans.GroupRatingBean;
import com.example.anishchenko.ratingvolsu.beans.StudentBean;
import com.example.anishchenko.ratingvolsu.beans.StudentRatingBean;
import com.example.anishchenko.ratingvolsu.requests.GetFacultsRequest;
import com.example.anishchenko.ratingvolsu.requests.GetGroupsRequest;
import com.example.anishchenko.ratingvolsu.requests.GetRatingOfGroupRequest;
import com.example.anishchenko.ratingvolsu.requests.GetRatingOfStudentRequest;
import com.example.anishchenko.ratingvolsu.requests.GetSemestrListRequest;
import com.example.anishchenko.ratingvolsu.requests.GetStudentListRequest;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends BaseSpiceActivity {

    private SpiceManager _spiceManager;
    private GetFacultsRequest _facultsRequest;
    private GetFacultsListener _facultsRequestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    //region OnClick
    public void OnGetFacultsClick(View view) {
        getSpiceManager().execute(new GetFacultsRequest(), new GetFacultsListener());
    }

    public void OnGetGroupsClick(View view) {
        getSpiceManager().execute(new GetGroupsRequest("3"), new GetGroupsListener());
    }

    public void OnGetSemestrClick(View view) {
        getSpiceManager().execute(new GetSemestrListRequest("4681"), new GetSemestrListener());
    }

    public void OnGetStudentClick(View view) {
        getSpiceManager().execute(new GetStudentListRequest("4681"), new GetStudentListener());
    }

    public void OnGetGroupRatingClick(View view) {
        getSpiceManager().execute(new GetRatingOfGroupRequest("3","4681","1"), new GetRatingOfGroupListener());
    }

    public void OnGetSRatingClick(View view) {
        getSpiceManager().execute(new GetRatingOfStudentRequest("3","4681","1", "66833"), new GetStudentRatingListener());
    }

    //endregion

    //region Listeners
    private class GetFacultsListener implements RequestListener<JsonElement> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            TextView textView = (TextView) findViewById(R.id.Result);
            textView.setText(spiceException.getLocalizedMessage());
        }

        @Override
        public void onRequestSuccess(JsonElement bean) {
            ArrayList<FacultBean> list = new GsonBuilder().create().fromJson(bean,
                    new TypeToken<ArrayList<FacultBean>>() {
                    }.getType());
            TextView textView = (TextView) findViewById(R.id.Result);
            String s ="";
            for(FacultBean item : list)
            {
                s += item.Id + "|"+ item.Name;
            }
            textView.setText(s);
        }
    }

    private class GetGroupsListener implements RequestListener<JsonElement> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(JsonElement jsonElement) {
            ArrayList<GroupBean> list = new GsonBuilder().create().fromJson(jsonElement,
                    new TypeToken<ArrayList<GroupBean>>() {
                    }.getType());

            TextView textView = (TextView) findViewById(R.id.Result);
            String s ="";
            for(GroupBean item : list)
            {
                s += String.format("|%s|%s|%s|%s|%d",item.Id,item.Name,item.Type, item.Year, item.SemestrCount);
            }
            textView.setText(s);
        }
    }

    private class GetSemestrListener implements RequestListener<JsonElement>{

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(JsonElement jsonElement) {
            ArrayList<String> list = new GsonBuilder().create().fromJson(jsonElement,
                    new TypeToken<ArrayList<String>>() {
                    }.getType());

            TextView textView = (TextView) findViewById(R.id.Result);
            String s ="";
            for(String item : list)
            {
                s += item +"|";
            }
            textView.setText(s);
        }
    }

    private class GetStudentListener implements RequestListener<JsonElement>{

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(JsonElement jsonElement) {
            ArrayList<StudentBean> list = new GsonBuilder().create().fromJson(jsonElement,
                    new TypeToken<ArrayList<StudentBean>>() {
                    }.getType());

            TextView textView = (TextView) findViewById(R.id.Result);
            String s ="";
            for(StudentBean item : list)
            {
                s += item.Id +"|" + item.Number;
            }
            textView.setText(s);
        }
    }

    private class GetStudentRatingListener implements  RequestListener<JsonElement>{

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(JsonElement jsonElement) {
            StudentRatingBean list = new GsonBuilder().create().fromJson(jsonElement,
                    StudentRatingBean.class);

            TextView textView = (TextView) findViewById(R.id.Result);
            String s ="SUCCES 2";
            textView.setText(s);

        }
    }

    private class GetRatingOfGroupListener implements RequestListener<JsonElement>{

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(JsonElement jsonElement) {
            GroupRatingBean list = new GsonBuilder().create().fromJson(jsonElement,
                    GroupRatingBean.class);

            TextView textView = (TextView) findViewById(R.id.Result);
            String s ="SUCCES";
            textView.setText(s);

        }
    }
    //endregion


}
