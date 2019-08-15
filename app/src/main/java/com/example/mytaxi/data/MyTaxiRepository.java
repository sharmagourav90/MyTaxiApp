package com.example.mytaxi.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.mytaxi.utilities.InjectorUtils;
import com.example.mytaxi.data.model.PoiValues;
import com.example.mytaxi.data.network.TaxiNetworkDataSource;

import java.util.List;

public class MyTaxiRepository {
    private static final Object LOCK = new Object();
    private static MyTaxiRepository sInstance;
    Context mContext;
    private TaxiNetworkDataSource mTaxiNetworkDataSource;

    public MyTaxiRepository(TaxiNetworkDataSource taxiNetworkDataSource, Context context) {
        mTaxiNetworkDataSource = taxiNetworkDataSource;
        this.mContext = context;
    }

    public synchronized static MyTaxiRepository getInstance(TaxiNetworkDataSource weatherNetworkDataSource, Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MyTaxiRepository (weatherNetworkDataSource, context);
            }
        }
        return sInstance;
    }

    public LiveData<List<PoiValues>> getTaxiData(){
        mTaxiNetworkDataSource = InjectorUtils.provideNetworkDataSource(mContext);
        return mTaxiNetworkDataSource.fetchData();
    }
}
