package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.utils.IRequestManager;
import com.google.gson.JsonElement;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class GetRatingOfGroupRequest extends RetrofitSpiceRequest<JsonElement,IRequestManager> {

    public String FacultId;
    public String GroupId;
    public String SemestrId;

    public GetRatingOfGroupRequest(String facultId, String groupId, String semestrId)
    {
        super(JsonElement.class,IRequestManager.class);

        FacultId = facultId;
        GroupId = groupId;
        SemestrId = semestrId;
    }

    @Override
    public JsonElement loadDataFromNetwork() throws Exception {
        return getService().getRatingOfGroup(FacultId,GroupId,SemestrId);
    }
}
