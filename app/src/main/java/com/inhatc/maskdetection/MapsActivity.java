package com.inhatc.maskdetection;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private double latitude, longitude;
    private Intent intent;
    private LatLng objLocation;
    private Marker objMK1;
    private String name, addr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        intent = getIntent();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        latitude = intent.getDoubleExtra("위도",0);
        longitude = intent.getDoubleExtra("경도",0);
        name = intent.getStringExtra("이름");
        addr = intent.getStringExtra("주소");
        objLocation = new LatLng(latitude, longitude);
        objMK1 = mMap.addMarker(new MarkerOptions()
                .position(objLocation)
                .title(name)
                .snippet(addr));
        objMK1.showInfoWindow();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(objLocation));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}
