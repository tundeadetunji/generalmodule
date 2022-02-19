package com.inovationware.generalmodule;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IWriteModelAPI {
    @POST("{directory}/{code}?")
    Call<WriteModel> getContentFromServer(
            @Path("baseURL") String baseURL,
            @Path("directory") String directory,
            @Path("code") String code,
            @Query("username") String username,
            @Query("password") String password,
            @Query("file") String file,
            @Query("app_name") String app_name,
            @Query("append") Boolean append,
            @Query("store") String store,
            @Query("text") String text
    );

}

