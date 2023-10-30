package com.android.geotrack;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.android.geotrack.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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

        // Retrieve latitude and longitude from the Intent
        double latitude = getIntent().getDoubleExtra("LATITUDE", 0.0); // 0.0 is the default value if not found
        double longitude = getIntent().getDoubleExtra("LONGITUDE", 0.0);
        String address = getIntent().getStringExtra("ADDRESS");

        // Create a LatLng for the specified location
        LatLng location = new LatLng(latitude, longitude);

        // Add a marker at the specified location
        mMap.addMarker(new MarkerOptions().position(location).title(address));

        // Create a camera update to zoom and center the map on the specified location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, 15); // Change the zoom level (15) as needed

        // Apply the camera update to the map
        mMap.moveCamera(cameraUpdate);

        mMap.addCircle(new CircleOptions().center(location).
                radius(100).
                fillColor(R.color.map_pointer_location)
                .strokeColor(Color.BLUE));
    }

}