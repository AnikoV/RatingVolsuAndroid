package com.example.anishchenko.ratingvolsu.requests;

import com.example.anishchenko.ratingvolsu.beans.GroupBean;
import com.example.anishchenko.ratingvolsu.utils.IRequestManager;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


public class GetGroupsRequest extends RetrofitSpiceRequest<GroupBean[], IRequestManager> {

    private final String FacultId;

    public GetGroupsRequest(String facultId) {
        super(GroupBean[].class, IRequestManager.class);
        FacultId = facultId;
    }

    @Override
    public GroupBean[] loadDataFromNetwork() throws Exception {
        return getService().getGroups(FacultId);
    }
}
