package com.example.madapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    Spinner SPCategory;
    Button BtnFind;
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FloatingActionButton FABReforest;
    Marker marker;
    List<Marker> reforestationMarkersList = new ArrayList<>();
    List<LatLng> locationList = new ArrayList<>();
    boolean isMethodAddAllMarkersCalled = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapsFragment newInstance(String param1, String param2) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Connect with the widget in the frontend UI file
        SPCategory = view.findViewById(R.id.SPCategory);
        BtnFind = view.findViewById(R.id.BtnFind);
        FABReforest = view.findViewById(R.id.FABReforest);

        //Prepare the place category list
        String[] placeCategory = {
                "Kuala Lumpur", "APE Volunteer Site 5", "Urban Reforestation @ Sg Putat",
                "Kinabatangan Wildlife Sanctuary", "Bukit Piton Wildlife Safari", "Tabin Wildlife Reserve",
                "Lok Kawi Wildlife Park"};
        SPCategory.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, placeCategory));


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    // Method to add location LatLng to locationList
    public void addLocationPosition() {
        LatLng KL = new LatLng(3.1597182895318707, 101.70883414154353);
        LatLng APEVolunteerSite5 = new LatLng(5.55, 118.31);
        LatLng UrbanReforestationSgPutat = new LatLng(2.256187755440792, 102.27169677901126);
        LatLng KinabatanganWildlifeSanctuary = new LatLng(5.615706881211299, 118.358824454556);
        LatLng BukitPitonWildlifeSafari = new LatLng(5.024940906606486, 118.34722354122756);
        LatLng TabinWildlifeReserve = new LatLng(5.231477679367326, 118.72625183634673);
        LatLng LokKawiWildlifePark = new LatLng(5.860375044713165, 116.07159309341546);

        locationList.add(KL);
        locationList.add(APEVolunteerSite5);
        locationList.add(UrbanReforestationSgPutat);
        locationList.add(KinabatanganWildlifeSanctuary);
        locationList.add(BukitPitonWildlifeSafari);
        locationList.add(TabinWildlifeReserve);
        locationList.add(LokKawiWildlifePark);
    }

    private Marker setMarker(LatLng location, String title, String snippet){
        // Only clear markers when map has markers and when addAllMarkers method are not called
        if(mMap != null && !isMethodAddAllMarkersCalled) {
            mMap.clear();
        }

        Marker locate = mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        return locate;
    }

    public void addAllMarkers() {
        isMethodAddAllMarkersCalled = true;     // Change boolean to true so that all markers remain and not cleared

        Marker marker1 = setMarker(locationList.get(0), "Malaysia", "Kuala Lumpur");
        Marker marker2 = setMarker(locationList.get(1), "APE Volunteer Site 5", "Kinabatangan, Sabah");
        Marker marker3 = setMarker(locationList.get(2), "Urban Reforestation @ Sg Putat", "Ayer Keroh, Melaka");
        Marker marker4 = setMarker(locationList.get(3), "Kinabatangan Wildlife Sanctuary", "Kinabatangan, Sabah");
        Marker marker5 = setMarker(locationList.get(4), "Bukit Piton Wildlife Safari", "Lahad Datu, Sabah");
        Marker marker6 = setMarker(locationList.get(5), "Tabin Wildlife Reserve", "Lahad Datu, Sabah");
        Marker marker7 = setMarker(locationList.get(6), "Lok Kawi Wildlife Park", "Kota Kinabalu, Sabah");

        reforestationMarkersList.add(marker1);
        reforestationMarkersList.add(marker2);
        reforestationMarkersList.add(marker3);
        reforestationMarkersList.add(marker4);
        reforestationMarkersList.add(marker5);
        reforestationMarkersList.add(marker6);
        reforestationMarkersList.add(marker7);

        isMethodAddAllMarkersCalled = false;    // Change boolean back to false before exiting method
    }


    // Method to get the target location based on the selected item
    private LatLng getTargetLocationForItem(int selectedItem) {
        // Implement your logic to determine the target location for each item
        // For example, use a switch statement or if-else conditions
        // Return the corresponding LatLng for the selected item

        switch (selectedItem) {
            case 0: // Kuala Lumpur
                marker = setMarker(locationList.get(0), "Malaysia", "Kuala Lumpur");
                return locationList.get(0);
            case 1: // APE Malaysia Volunteer Site
                marker = setMarker(locationList.get(1), "APE Volunteer Site 5", "Kinabatangan, Sabah");
                return locationList.get(1);
            case 2:
                marker = setMarker(locationList.get(2), "Urban Reforestation @ Sg Putat", "Ayer Keroh, Melaka");
                return locationList.get(2);
            case 3:
                marker = setMarker(locationList.get(3), "Kinabatangan Wildlife Sanctuary", "Kinabatangan, Sabah");
                return locationList.get(3);
            case 4:
                marker = setMarker(locationList.get(4), "Bukit Piton Wildlife Safari", "Lahad Datu, Sabah");
                return locationList.get(4);
            case 5:
                marker = setMarker(locationList.get(5), "Tabin Wildlife Reserve", "Lahad Datu, Sabah");
                return locationList.get(5);
            case 6:
                marker = setMarker(locationList.get(6), "Lok Kawi Wildlife Park", "Kota Kinabalu, Sabah");
                return locationList.get(6);

            default:
                return null;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addLocationPosition();

        // Setting basic features of Google Map
//        mMap.getUiSettings().setZoomControlsEnabled(true);      // show zoom in and out button at bottom right corner


        // when button find is clicked, it will show marker of location
        BtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = SPCategory.getSelectedItemPosition();

                // Determine the location(s) based on the selected item
                LatLng targetLocation = getTargetLocationForItem(selectedPosition);

                // Animate camera so that map will focus on the marker when find button is clicked
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),9.0f));
            }
        });


        // show all markers on map when floating button is clicked
        FABReforest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a LatLngBounds.Builder to include all markers
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                addAllMarkers();

                // Iterate through your list of markers and include their positions
                for (Marker marker : reforestationMarkersList) {
                    builder.include(marker.getPosition());
                }

                // Build the LatLngBounds
                LatLngBounds bounds = builder.build();

                // Adjust camera position to fit all markers
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
            }
        });


        // show custom info window when marker is clicked
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


        // Show info window and animate camera to marker when clicked
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();    // show info window only when marker is clicked

                // Animate the camera to the clicked marker's position with a fixed zoom level
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),9.0f));

                // Return true to consume the click event
                return true;
            }
        });


        // Move camera to default location Malaysia
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        // Add left and right boundary
        builder.include(locationList.get(0));
        builder.include(locationList.get(1));
        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
    }
}