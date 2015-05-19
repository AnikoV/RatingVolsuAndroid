package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.utils.Feed;
import com.google.gson.JsonElement;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by Владимир on 19.05.2015.
 */
public class GetStudentListRequest extends RetrofitSpiceRequest<JsonElement,Feed>{

    private final String GroupId;

    public GetStudentListRequest(String groupId)
    {
        super(JsonElement.class,Feed.class);
        GroupId = groupId;
    }
    @Override
    public JsonElement loadDataFromNetwork() throws Exception {
        return getService().getStudentList(GroupId);
    }
}
