package com.mobisys.android.androidl.rest.api;

import com.mobisys.android.androidl.data.Movie;
import com.mobisys.android.androidl.data.MovieWrapper;
import com.mobisys.android.androidl.rest.RestCallback;

import retrofit.http.GET;
import retrofit.http.Query;

public interface MovieApi {
    @GET("/movie/upcoming")
    public void getMovieList(@Query("api_key") String api_key, RestCallback<MovieWrapper> restCallback);

    @GET("/movie/upcoming")
    public Movie getMovieList(@Query("api_key") String api_key);

}
