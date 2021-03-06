package com.example.anishchenko.ratingvolsu.utils;

import com.google.gson.JsonElement;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface IRequestManager {

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

    @FormUrlEncoded
    @POST("/group_rat.php")
    JsonElement getRatingOfGroup(@Field("Fak") String FacultId, @Field("Group") String GroupId,
                                 @Field("Semestr") String SemestrId);

    @FormUrlEncoded
    @POST("/stud_rat.php")
    JsonElement getRatingOfStudent(@Field("Fak") String FacultId, @Field("Group") String GroupId,
                                   @Field("Semestr") String SemestrId, @Field("Zach") String StudentId);
}
