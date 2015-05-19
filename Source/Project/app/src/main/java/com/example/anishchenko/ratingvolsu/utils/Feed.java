package com.example.anishchenko.ratingvolsu.utils;

import com.google.gson.JsonElement;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface Feed {

    @FormUrlEncoded
    @POST("/facult_req.php")
    JsonElement getFacults(@Field("get_lists") int param);

    @FormUrlEncoded
    @POST("/group_req.php")
    JsonElement getGroups(@Field("fak_id") String facultId);

    @FormUrlEncoded
    @POST("/sem_req.php")
    JsonElement getSemestrs(@Field("gr_id") String groupId);

    @FormUrlEncoded
    @POST("/stud_req.php")
    JsonElement getStudentList(@Field("group_id") String groupId);
}
