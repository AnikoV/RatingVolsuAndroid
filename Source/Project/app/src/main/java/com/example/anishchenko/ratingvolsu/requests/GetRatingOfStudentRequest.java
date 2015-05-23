package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.utils.IRequestManager;
import com.google.gson.JsonElement;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class GetRatingOfStudentRequest extends RetrofitSpiceRequest<JsonElement,IRequestManager> {

    public String FacultId;
    public String GroupId;
    public String SemestrId;
    public String StudentId;

    public GetRatingOfStudentRequest(String facultId, String groupId, String semestrId, String studentId)
    {
        super(JsonElement.class,IRequestManager.class);

        FacultId = facultId;
        GroupId = groupId;
        SemestrId = semestrId;
        StudentId = studentId;
    }

    @Override
    public JsonElement loadDataFromNetwork() throws Exception {
        return getService().getRatingOfStudent(FacultId, GroupId, SemestrId, StudentId);
    }
}