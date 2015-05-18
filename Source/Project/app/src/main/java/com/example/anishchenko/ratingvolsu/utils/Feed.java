package com.example.anishchenko.ratingvolsu.utils;

import com.google.gson.JsonElement;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;


public interface Feed {

    @Headers({
            "Accept: */*",
            "Accept-Encoding: gzip, deflate",
            "Content-Length: 11",
            "Content-Type: application/x-www-form-urlencoded",
            "User-Agent: runscope/0.1"
    })
    @FormUrlEncoded
    @POST("/facult_req.php")
    JsonElement getFacults(@Field("get_lists") int param);
//    JsonElement getFacults(@QueryMap Map<String, Object> params);
}
