package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.utils.IRequestManager;
import com.google.gson.JsonElement;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class GetStudentListRequest extends RetrofitSpiceRequest<JsonElement,IRequestManager>{

    private final String GroupId;

    public GetStudentListRequest(String groupId)
    {
        super(JsonElement.class,IRequestManager.class);
        GroupId = groupId;
    }
    @Override
    public JsonElement loadDataFromNetwork() throws Exception {
        return getService().getStudentList(GroupId);
    }
}
