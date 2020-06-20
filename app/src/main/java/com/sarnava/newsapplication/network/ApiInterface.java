package com.sarnava.newsapplication.network;

import com.sarnava.newsapplication.data.NewsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("v2/top-headlines")
    public Observable<NewsResponse> getNews(@Query("country") String country, @Query("apiKey") String apiKey);
}
