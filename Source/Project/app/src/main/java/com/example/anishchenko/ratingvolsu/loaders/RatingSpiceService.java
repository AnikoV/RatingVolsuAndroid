package com.example.anishchenko.ratingvolsu.loaders;
import com.example.anishchenko.ratingvolsu.utils.IRequestManager;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class RatingSpiceService extends RetrofitGsonSpiceService {
    public static String BASE_URL = "http://umka.volsu.ru/newumka3/viewdoc/service_selector";

    @Override
    public void onCreate () {
        super.onCreate();
        addRetrofitInterface(IRequestManager.class);
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        RestAdapter.Builder builder = super.createRestAdapterBuilder();
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", null);
            }
        });
        return builder;
    }

    @Override
    protected String getServerUrl () {
        return BASE_URL;
    }

    @Override
    public int getThreadCount () {
        return 6;
    }
}
