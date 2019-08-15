package com.example.mytaxi.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mytaxi.R;
import com.example.mytaxi.ui.detail.MapsFragment;
import com.example.mytaxi.ui.list.TaxiListFragment;

public class MainActivity extends AppCompatActivity {
    private final String LIST_TAG = "LIST_TAG";
    private final String MAPS_TAG = "MAPS_TAG";

    private TaxiListFragment mTaxiListFragment;
    private MapsFragment mMapsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaxiListFragment = (TaxiListFragment) getSupportFragmentManager().findFragmentByTag(LIST_TAG);

        if (mTaxiListFragment == null) {
            mTaxiListFragment = new TaxiListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container, mTaxiListFragment, LIST_TAG).commit();
        }
    }

    public void showMap() {
        mMapsFragment = (MapsFragment) getSupportFragmentManager().findFragmentByTag(MAPS_TAG);

        if(mMapsFragment == null) {
            mMapsFragment = new MapsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container, mMapsFragment, MAPS_TAG).commit();
        }

        getSupportFragmentManager().beginTransaction().hide(mTaxiListFragment).show(mMapsFragment).commit();
    }

    public void showList() {
        getSupportFragmentManager().beginTransaction().hide(mMapsFragment).show(mTaxiListFragment).commit();
    }
}
