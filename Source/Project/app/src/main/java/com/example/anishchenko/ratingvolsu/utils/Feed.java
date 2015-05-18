package com.example.anishchenko.ratingvolsu.utils;
import com.google.gson.JsonElement;
import java.util.Map;

import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.QueryMap;


public interface Feed {

    @Headers({
            "Accept: */*",
            "Accept-Encoding: gzip, deflate",
            "Content-Length: 11",
            "Content-Type: application/x-www-form-urlencoded",
            "User-Agent: runscope/0.1"
    })
    @POST("/facult_req.php")
    JsonElement getFacults(@QueryMap Map<String,String> params);
}
