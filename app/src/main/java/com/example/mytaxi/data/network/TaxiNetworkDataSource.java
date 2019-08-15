package com.example.mytaxi.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.mytaxi.utilities.Constants;
import com.example.mytaxi.utilities.InjectorUtils;
import com.example.mytaxi.data.model.PoiValues;
import com.example.mytaxi.data.model.TaxiDetails;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaxiNetworkDataSource {
    private static final Object LOCK = new Object();
    private static TaxiNetworkDataSource sInstance;

    private TaxiNetworkDataSource() {
    }

    public static TaxiNetworkDataSource getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new TaxiNetworkDataSource();
            }
        }
        return sInstance;
    }

    public LiveData<List<PoiValues>> fetchData() {
        final MutableLiveData<List<PoiValues>> data = new MutableLiveData<>();

        Map<String , String > values = new LinkedHashMap<>();
        values.put(Constants.p1Lat_key, Constants.p1Lat);
        values.put(Constants.p1Lon_key, Constants.p1Lon);
        values.put(Constants.p2Lat_key, Constants.p2Lat);
        values.put(Constants.p2Lon_key, Constants.p2Lon);

        InjectorUtils.getRetroInstance().getAllTaxis(values).enqueue(new Callback <TaxiDetails>() {
            @Override
            public void onResponse(Call<TaxiDetails> call, Response<TaxiDetails> response) {

                List<PoiValues> list = response.body().getPoiList();
                if(response.isSuccessful()) {
                    Log.d(Constants.LOG_TAG, "Response Successful");
                    data.setValue(list);
                }
            }
            @Override
            public void onFailure(Call<TaxiDetails> call, Throwable t) {
                Log.d(Constants.LOG_TAG, "Error loading from API: " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
