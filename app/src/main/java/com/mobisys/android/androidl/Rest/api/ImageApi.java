package com.mobisys.android.androidl.Rest.api;

import com.mobisys.android.androidl.Rest.RestCallback;
import com.mobisys.android.androidl.data.Movie;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.MultipartTypedOutput;

public interface ImageApi {
    @POST("/api/image/upload")
    public void uploadImage(@Body MultipartTypedOutput multipartTypedOutput, RestCallback<String> restCallback);

}
