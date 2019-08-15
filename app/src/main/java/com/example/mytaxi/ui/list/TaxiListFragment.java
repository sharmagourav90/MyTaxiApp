package com.example.mytaxi.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mytaxi.R;
import com.example.mytaxi.data.model.PoiValues;
import com.example.mytaxi.databinding.FragmentTaxiListBinding;
import com.example.mytaxi.ui.MainActivity;
import com.example.mytaxi.ui.MainActivityViewModel;
import com.example.mytaxi.ui.MainActivityViewModelFactory;
import com.example.mytaxi.utilities.InjectorUtils;

public class TaxiListFragment extends Fragment implements TaxiListAdapter.TaxiAdapterOnItemClickHandler {
    private RecyclerView mRecyclerView;
    private TaxiListAdapter mAdapter;
    private MainActivityViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        FragmentTaxiListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_taxi_list, parent, false);
        MainActivityViewModelFactory detailFactory = InjectorUtils.provideMainActivityViewModelFactory(
                getActivity().getApplicationContext());
        mViewModel = ViewModelProviders.of(getActivity(), detailFactory).get(MainActivityViewModel.class);

        mRecyclerView = binding.recyclerView;

        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        if(savedInstanceState == null) {
            mViewModel.fetchTaxiData();
            mViewModel.setLoading(true);
        }

        mViewModel.getTaxiList().observe(getActivity(), mTaxiList -> {
            //Update the UI
            if (mTaxiList != null && mTaxiList.size()!=0) {
                mViewModel.setLoading(false);
                // Create an adapter and supply the data to be displayed.
                mAdapter = new TaxiListAdapter(mTaxiList, TaxiListFragment.this);
                // Connect the adapter with the recycler view.
                mRecyclerView.setAdapter(mAdapter);
                // Give the recycler view a default layout manager.
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onItemClick(PoiValues f) {
        mViewModel.setSelectedPoiValue(f);
        ((MainActivity)getActivity()).showMap();
    }
}
