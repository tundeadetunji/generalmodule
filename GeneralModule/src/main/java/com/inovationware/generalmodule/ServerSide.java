package com.inovationware.generalmodule;

import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerSide {

    private String username;
    private String password;
    private String app_name;

    private String baseUrl;
    private String directory;
    private String code;

    private String reply;

    public ServerSide(String baseUrl, String directory, String code, String username, String password, String app_name) {
        this.username = username;
        this.password = password;
        this.app_name = app_name;
        this.baseUrl = baseUrl;
        this.directory = directory;
        this.code = code;


    }

    private void readValue(String file, TextView textView) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        IReadModelAPI readmodelAPI = retrofit.create(IReadModelAPI.class);
        Call<ReadModel> call = readmodelAPI.getContentFromServer(toPath(baseUrl), toPath(directory), code, username, password, file, app_name);
        call.enqueue(new Callback<ReadModel>() {
            @Override
            public void onResponse(Call<ReadModel> call, Response<ReadModel> response) {
                if (response.isSuccessful()) {
                    ReadModel dataFromServer = response.body();
                    String status = dataFromServer.getStatus();
                    String data = dataFromServer.getData();
                    //reply = data;
                    textView.setText(data);
                } else {
                    //textDetails.setText("Failed with code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ReadModel> call, Throwable t) {
                //textDetails.setText(t.getMessage());
            }
        });
    }

    private void writeValue(String file, Boolean append, String store, String text) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        IWriteModelAPI writemodelAPI = retrofit.create(IWriteModelAPI.class);
        Call<WriteModel> call = writemodelAPI.getContentFromServer(toPath(baseUrl), toPath(directory), code, username, password, file, app_name, append, store, text);
        call.enqueue(new Callback<WriteModel>() {
            @Override
            public void onResponse(Call<WriteModel> call, Response<WriteModel> response) {
                if (response.isSuccessful()) {
                    WriteModel dataFromServer = response.body();
                    String status = dataFromServer.getStatus();
                    String data = dataFromServer.getData();

                } else {
                    //textDetails.setText("Failed with code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WriteModel> call, Throwable t) {
                //textDetails.setText(t.getMessage());
            }
        });
    }

    private String toPath(String string) {
        String result = "";
        if (string.endsWith("/")) {
            result = string;
        } else {
            result = string + "/";
        }
        return result;
    }
}