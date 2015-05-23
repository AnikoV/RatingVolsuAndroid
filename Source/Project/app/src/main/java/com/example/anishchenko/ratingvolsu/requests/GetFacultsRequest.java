package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.utils.IRequestManager;
import com.google.gson.JsonElement;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


public class GetFacultsRequest extends RetrofitSpiceRequest<JsonElement,IRequestManager> {

    public GetFacultsRequest() {
        super(JsonElement.class, IRequestManager.class);
    }

    @Override
    public JsonElement loadDataFromNetwork() throws Exception {

        return getService().getFacults(0);
    }
}
