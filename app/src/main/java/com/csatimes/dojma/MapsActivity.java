package com.csatimes.dojma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    LatLng leftBottomBound = new LatLng(15.385178, 73.868207);
    LatLng rightTopBound = new LatLng(15.394957, 73.885857);
    LatLng centre = new LatLng(15.389557, 73.876974);
    FloatingActionButton fab;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fab = (FloatingActionButton) findViewById(R.id.utilities_map_icon);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/d/u/0/viewer?mid=1oWEtH59EbMs82Z49uj00UnxQD2o"));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Snackbar.make(fab, "Google Maps is not installed!", Snackbar.LENGTH_LONG).show();
                }
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
        map = googleMap;

        LatLngBounds bounds = new LatLngBounds(leftBottomBound, rightTopBound);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(centre, 0));
        LatLng marker = new LatLng(15.39109, 73.88012);
        map.addMarker(new MarkerOptions().position(marker).title("Fruit Vendor"));
        marker = new LatLng(15.39244, 73.87893);
        map.addMarker(new MarkerOptions().position(marker).title("PostBox"));
        marker = new LatLng(15.39241, 73.87885);
        map.addMarker(new MarkerOptions().position(marker).title("Cobbler"));
        marker = new LatLng(15.3924, 73.8789);
        map.addMarker(new MarkerOptions().position(marker).title("Tailor"));
        marker = new LatLng(15.39171, 73.87601);
        map.addMarker(new MarkerOptions().position(marker).title("Pharmacy"));
        marker = new LatLng(15.38811, 73.87663);
        map.addMarker(new MarkerOptions().position(marker).title("Visitor's Guest House"));
        marker = new LatLng(15.3869, 73.8709);
        map.addMarker(new MarkerOptions().position(marker).title("Nursery"));
        marker = new LatLng(15.3917, 73.87605);
        map.addMarker(new MarkerOptions().position(marker).title("Medical Centre"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(centre)      // Sets the center of the map to Mountain View
                .zoom(16f)                   // Sets the zoom
                // Sets the orientation of the camera to east
                .tilt(50)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
