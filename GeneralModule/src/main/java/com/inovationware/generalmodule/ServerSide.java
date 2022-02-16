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

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private String reply;

    public ServerSide(String username, String password, String app_name, String baseUrl) {
        this.username = username;
        this.password = password;
        this.app_name = app_name;
        this.baseUrl = baseUrl;
    }

    public void readValue(String file, TextView textView) {
        ReadValueFromServer(file, textView);
    }

    public void writeValue(String file, String text, Boolean append, String store){
        WriteValueToServer(file, append, store, text);
    }

    private void ReadValueFromServer(String file, TextView textView) {
        IReadModelAPI readmodelAPI = retrofit.create(IReadModelAPI.class);
        Call<ReadModel> call = readmodelAPI.getContentFromServer(username, password, file, app_name);
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

    private void WriteValueToServer(String file, Boolean append, String store, String text){
        IWriteModelAPI writemodelAPI = retrofit.create(IWriteModelAPI.class);
        Call<WriteModel> call = writemodelAPI.getContentFromServer(username, password, file, app_name, append, store, text);
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
}