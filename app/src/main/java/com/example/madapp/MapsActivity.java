package com.example.madapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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


        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous destination
                onBackPressed();
            }
        });

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

        // Add a marker in Malaysia and move the camera
        LatLng malaysia = new LatLng(3.5, 102.5);
        Marker markerMalaysia = mMap.addMarker(new MarkerOptions()
                .position(malaysia)
                .title("Malaysia")
                .snippet("Default Location Reforestation"));

        LatLng site1 = new LatLng(5.55, 118.31);
        Marker markerSite1 = mMap.addMarker(new MarkerOptions()
                .position(site1)
                .title("APE Malaysia Volunteer Site 5")
                .snippet("Location: Kinabatangan,Sabah"));

        // Move camera to default location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(malaysia));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;  // Use the default InfoWindow frame
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the custom layout
                View view = getLayoutInflater().inflate(R.layout.custom_info_window, null);

                // Set the content of the custom layout
                TextView titleTextView = view.findViewById(R.id.infoWindowTitle);
                TextView snippetTextView = view.findViewById(R.id.infoWindowSnippet);

                titleTextView.setText(marker.getTitle());
                snippetTextView.setText(marker.getSnippet());

                return view;
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();    // show info window only when marker is clicked

                // Move the camera to the clicked marker's position with a fixed zoom level
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),8.0f));

                // Return true to consume the click event
                return true;
            }
        });

        // Move the camera to a position that shows all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(malaysia);
        builder.include(site1);
        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
    }
}