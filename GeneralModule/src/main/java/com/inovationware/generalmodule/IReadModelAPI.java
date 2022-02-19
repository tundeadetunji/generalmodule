package com.inovationware.generalmodule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IReadModelAPI {
    @GET("{directory}/{code}?")
    Call<ReadModel> getContentFromServer(
            @Path("baseURL") String baseURL,
            @Path("directory") String directory,
            @Path("code") String code,
            @Query("username") String username,
            @Query("password") String password,
            @Query("file") String file,
            @Query("app_name") String app_name
    );

}
