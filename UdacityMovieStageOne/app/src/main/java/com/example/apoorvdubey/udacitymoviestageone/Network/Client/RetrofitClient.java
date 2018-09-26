package com.example.apoorvdubey.udacitymoviestageone.Network.Client;

import com.example.apoorvdubey.udacitymoviestageone.Utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
