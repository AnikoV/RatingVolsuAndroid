package com.example.anishchenko.ratingvolsu.loaders;
import com.example.anishchenko.ratingvolsu.utils.Feed;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

public class RatingSpiceService extends RetrofitGsonSpiceService {
    public static String BASE_URL = "http://umka.volsu.ru/newumka3/viewdoc/service_selector";

    @Override
    public void onCreate () {
        super.onCreate();
        addRetrofitInterface(Feed.class);
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
