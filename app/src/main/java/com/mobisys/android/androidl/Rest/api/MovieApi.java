package com.mobisys.android.androidl.Rest.api;

import com.mobisys.android.androidl.Rest.RestCallback;
import com.mobisys.android.androidl.data.Movie;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Query;

public interface MovieApi {
    @GET("/movie/upcoming")
    public void getMovieList(@Query("api_key") String api_key, RestCallback<Movie> restCallback);
}
