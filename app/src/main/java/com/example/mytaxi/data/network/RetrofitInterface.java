package com.example.mytaxi.data.network;

import com.example.mytaxi.data.model.TaxiDetails;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RetrofitInterface {

    @GET("/")
    public Call<TaxiDetails> getAllTaxis(@QueryMap Map<String, String> options);

}
