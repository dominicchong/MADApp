package com.example.madapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.madapp.databinding.FragmentMapBinding;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                // Move the camera to the clicked marker's position with a fixed zoom level
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),7.0f));

                // Return true to consume the click event
                return true;
            }
        });

        // Add a marker in Malaysia and move the camera
        LatLng malaysia = new LatLng(3.5, 102.5);
        mMap.addMarker(new MarkerOptions()
                .position(malaysia)
                .title("Malaysia")
                .snippet("Default reforestation location country"));

        LatLng site1 = new LatLng(5.55, 118.31);
        mMap.addMarker(new MarkerOptions()
                .position(site1)
                .title("APE Malaysia Volunteer Site 5")
                .snippet("Location: Kinabatangan,Sabah"));

        // Move camera to default location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(malaysia));
    }
}