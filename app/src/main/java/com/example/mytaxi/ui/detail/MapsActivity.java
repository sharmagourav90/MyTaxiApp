package com.example.mytaxi.ui.detail;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mytaxi.R;
import com.example.mytaxi.data.model.PoiValues;
import com.example.mytaxi.utilities.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static com.example.mytaxi.utilities.Constants.LOG_TAG;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,
        GoogleMap.OnCameraMoveStartedListener {

    private GoogleMap mMap;
    float lat, lon;
    List<PoiValues> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lat = Float.valueOf(getIntent().getStringExtra(Constants.LATITUDE));
        lon = Float.valueOf(getIntent().getStringExtra(Constants.LONGITUDE));
        markerList = ((List<PoiValues>) getIntent().getExtras().getSerializable("list"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void findMapBounds(){
        LatLng farLeft = mMap.getProjection().getVisibleRegion().farLeft;
        LatLng farRight = mMap.getProjection().getVisibleRegion().farRight;
        LatLng nearLeft = mMap.getProjection().getVisibleRegion().nearLeft;
        LatLng nearRight = mMap.getProjection().getVisibleRegion().nearRight;
        addMarkers(farLeft, farRight, nearLeft, nearRight);
    }

    private void addMarkers(LatLng farLeft,LatLng farRight,LatLng nearLeft,LatLng nearRight){
        int count = 0;
        for(int i =0; i < markerList.size(); i++){
            LatLng mLocation = new LatLng(Float.valueOf(markerList.get(i).getCoordinates().getLatitude()),
                    Float.valueOf(markerList.get(i).getCoordinates().getLongitude()));
            if(mLocation.latitude<farLeft.latitude &&
                    mLocation.longitude>farLeft.longitude &&
                    mLocation.latitude<farRight.latitude &&
                    mLocation.longitude<farRight.longitude &&
                    mLocation.latitude> nearLeft.latitude &&
                    mLocation.longitude>nearLeft.longitude &&
                    mLocation.latitude>nearRight.latitude &&
                    mLocation.longitude<nearRight.longitude){
                 System.out.println(LOG_TAG +" Marker count: "+ (++count));
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi_icon)).
                        position(mLocation).title
                        (markerList.get(i).getId().toString()+", " + markerList.get(i).getFleetType()));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(this);
        mMap.setOnCameraMoveStartedListener(this);
        for (int i = 0; i < markerList.size(); i++) {
            LatLng mLocation = new LatLng(Float.valueOf(markerList.get(i).getCoordinates().getLatitude()),
                    Float.valueOf(markerList.get(i).getCoordinates().getLongitude()));
            if ((lat == mLocation.latitude && (lon == mLocation.longitude))) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocation, Constants.ZOOM_LEVEL));
                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.taxi_icon)).
                        position(mLocation).title
                        (markerList.get(i).getId().toString()+", " + markerList.get(i).getFleetType()))
                        .showInfoWindow();
            }
        }
    }

    @Override
    public void onMapLoaded() {
        Log.d(LOG_TAG, "onMapLoaded");
        findMapBounds();
    }

    @Override
    public void onCameraMoveStarted(int reason) {
            mMap.setOnMapLoadedCallback(() -> {
                Log.d(LOG_TAG, "onCameraMoveStarted - onMapLoaded");
                findMapBounds();
            });
    }
}
