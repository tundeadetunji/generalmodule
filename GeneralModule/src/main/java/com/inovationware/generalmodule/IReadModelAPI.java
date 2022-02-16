package com.inovationware.generalmodule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IReadModelAPI {
    @GET("sync/read.ashx?")
    Call<ReadModel> getContentFromServer(
            @Query("username") String username,
            @Query("password") String password,
            @Query("file") String file,
            @Query("app_name") String app_name
    );

}
