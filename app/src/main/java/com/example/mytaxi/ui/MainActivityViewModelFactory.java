package com.example.mytaxi.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.mytaxi.data.MyTaxiRepository;

public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MyTaxiRepository mRepository;

    public MainActivityViewModelFactory(MyTaxiRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(mRepository);
    }
}
