package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.beans.StudentBean;
import com.example.anishchenko.ratingvolsu.utils.IRequestManager;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class GetStudentListRequest extends RetrofitSpiceRequest<StudentBean[], IRequestManager> {

    private final String GroupId;

    public GetStudentListRequest(String groupId) {
        super(StudentBean[].class, IRequestManager.class);
        GroupId = groupId;
    }

    @Override
    public StudentBean[] loadDataFromNetwork() throws Exception {
        return getService().getStudentList(GroupId);
    }
}
