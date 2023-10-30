package com.android.geotrack;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.geotrack.databinding.ActivityMapsDirectionBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsDirectionActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsDirectionBinding binding;
    private List<Polyline> routeLines; // To store the route polylines
    private TextInputEditText startingPointEditText;
    private TextInputEditText endPointEditText;
    private List<LatLng> routeStarts;
    private List<LatLng> routeEnds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsDirectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize UI components
        startingPointEditText = binding.startingPoint;
        endPointEditText = binding.endPoint;

        routeStarts = new ArrayList<>();
        routeEnds = new ArrayList<>();
        routeLines = new ArrayList<>();

        // TODO: Add your route coordinates to routeStarts and routeEnds lists

        // Set click listener for the "Get Direction" button
        binding.getDirectionBtn.setOnClickListener(v -> {
            // Check if start and end locations are not empty, and then call a method to mark them
            String startLocation = startingPointEditText.getText().toString().trim();
            String endLocation = endPointEditText.getText().toString().trim();
            if (!startLocation.isEmpty() && !endLocation.isEmpty()) {
                markStartAndEndLocations(startLocation, endLocation);
            }

            if (startLocation.equals("")){
                Toast.makeText(this, "Please enter the start location", Toast.LENGTH_SHORT).show();
            }

            if(endLocation.equals("")){
                Toast.makeText(this, "Please enter your destination", Toast.LENGTH_SHORT).show();
            }

            if (startLocation.equals("") && endLocation.equals("")){
                Toast.makeText(this, "Please enter the Start location & destination for results", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for the "Go Reverse" button
        binding.swapButton.setOnClickListener(v -> {
            // Swap the text in the input fields
            String temp = startingPointEditText.getText().toString();
            startingPointEditText.setText(endPointEditText.getText().toString());
            endPointEditText.setText(temp);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void markStartAndEndLocations(String startLocation, String endLocation) {
        // Clear previous routes and markers
        clearPreviousRoutes();

        // Use Geocoding to convert location names to latitude and longitude coordinates
        LatLng startLatLng = getLocationFromName(startLocation);
        LatLng endLatLng = getLocationFromName(endLocation);

        if (startLatLng != null && endLatLng != null) {
            routeStarts.add(startLatLng);
            routeEnds.add(endLatLng);

            // Add markers for start and end locations
            mMap.addMarker(new MarkerOptions().position(startLatLng).title("Start Location"));
            mMap.addMarker(new MarkerOptions().position(endLatLng).title("End Location"));

            // Move the camera to the center of the route
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 10));

            // Call a method to draw the route between start and end locations
            drawRoutes();

        }
    }

    // Method to clear previous routes and markers
    private void clearPreviousRoutes() {
        // Clear markers
        mMap.clear();

        // Clear polylines
        for (Polyline routeLine : routeLines) {
            routeLine.remove();
        }

        // Clear route lists
        routeStarts.clear();
        routeEnds.clear();
    }

    private LatLng getLocationFromName(String locationName) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void drawDrivingRoute(List<LatLng> routePoints) {
        // Define the polyline options
        PolylineOptions options = new PolylineOptions();
        options.addAll(routePoints);
        options.width(10); // Line width
        options.color(getResources().getColor(R.color.primary_Dark)); // Line color

        // Add the polyline to the map
        routeLines.add(mMap.addPolyline(options));
    }


    private void drawRoutes() {
        // Iterate through the routes and draw them on the map
        for (int i = 0; i < routeStarts.size(); i++) {
            LatLng start = routeStarts.get(i);
            LatLng end = routeEnds.get(i);

            // Define the polyline options
            PolylineOptions options = new PolylineOptions();
            options.add(start);
            options.add(end);
            options.width(10); // Line width
            options.color(getResources().getColor(R.color.primary_Dark)); // Line color

            // Add the polyline to the map and store it in the routeLines list
            routeLines.add(mMap.addPolyline(options));
        }
    }
}

