package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.beans.FacultBean;
import com.example.anishchenko.ratingvolsu.utils.IRequestManager;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


public class GetFacultsRequest extends RetrofitSpiceRequest<FacultBean[], IRequestManager> {

    public GetFacultsRequest() {
        super(FacultBean[].class, IRequestManager.class);
    }

    @Override
    public FacultBean[] loadDataFromNetwork() throws Exception {

        return getService().getFacults(0);
    }
}
