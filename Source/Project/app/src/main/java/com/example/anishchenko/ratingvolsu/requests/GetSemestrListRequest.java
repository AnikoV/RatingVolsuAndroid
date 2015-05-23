package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.utils.IRequestManager;
import com.google.gson.JsonElement;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by Владимир on 19.05.2015.
 */
public class GetSemestrListRequest extends RetrofitSpiceRequest<JsonElement,IRequestManager> {
    private final String GroupId;

    public GetSemestrListRequest(String groupId)
    {
        super(JsonElement.class, IRequestManager.class);
        GroupId = groupId;
    }

    @Override
    public JsonElement loadDataFromNetwork() throws Exception {
        return getService().getSemestrs(GroupId);
    }
}
