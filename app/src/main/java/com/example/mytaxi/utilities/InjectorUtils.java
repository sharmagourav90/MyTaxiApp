package com.example.mytaxi.utilities;

import android.content.Context;

import com.example.mytaxi.ui.MainActivityViewModelFactory;
import com.example.mytaxi.data.network.RetroInstance;
import com.example.mytaxi.data.network.RetrofitInterface;
import com.example.mytaxi.data.MyTaxiRepository;
import com.example.mytaxi.data.network.TaxiNetworkDataSource;

/**
 * Provides static methods to inject the various classes needed for MyTaxi
 */
public class InjectorUtils {

    public static MyTaxiRepository provideRepository(Context context) {
        TaxiNetworkDataSource networkDataSource =
                TaxiNetworkDataSource.getInstance();
        return MyTaxiRepository.getInstance(networkDataSource,context);
    }

    public static TaxiNetworkDataSource provideNetworkDataSource(Context context) {
        return TaxiNetworkDataSource.getInstance();
    }

    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        MyTaxiRepository repository = provideRepository(context.getApplicationContext());
        return new MainActivityViewModelFactory(repository);
    }

    public static RetrofitInterface getRetroInstance() {
        return RetroInstance.getClient().create(RetrofitInterface.class);
    }
}