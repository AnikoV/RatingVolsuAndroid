package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.utils.Feed;
import com.google.gson.JsonElement;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by Владимир on 19.05.2015.
 */
public class GetSemestrListRequest extends RetrofitSpiceRequest<JsonElement,Feed> {
    private final String GroupId;

    public GetSemestrListRequest(String groupId)
    {
        super(JsonElement.class, Feed.class);
        GroupId = groupId;
    }

    @Override
    public JsonElement loadDataFromNetwork() throws Exception {
        return getService().getSemestrs(GroupId);
    }
}
