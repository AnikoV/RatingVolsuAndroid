package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.utils.Feed;
import com.google.gson.JsonElement;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import java.util.HashMap;
import java.util.Map;


public class GetFacultsRequest extends RetrofitSpiceRequest<JsonElement,Feed> {

    public GetFacultsRequest() {
        super(JsonElement.class, Feed.class);
    }

    @Override
    public JsonElement loadDataFromNetwork() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("get_lists", 0);

        return getService().getFacults(0);
//        return getService().getFacults(params);
    }
}
