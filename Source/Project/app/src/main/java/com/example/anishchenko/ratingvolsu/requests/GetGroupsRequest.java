package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.utils.Feed;
import com.google.gson.JsonElement;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


public class GetGroupsRequest extends RetrofitSpiceRequest<JsonElement,Feed>{

    private final String FacultId;

    public GetGroupsRequest(String facultId)
    {
        super(JsonElement.class, Feed.class);
        FacultId = facultId;
    }

    @Override
    public JsonElement loadDataFromNetwork() throws Exception {
        return getService().getGroups(FacultId);
    }
}
