package com.csatimes.dojma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
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
        marker = new LatLng(15.391248, 73.880643);
        map.addMarker(new MarkerOptions().position(marker).title("FoodKing"));
        marker = new LatLng(15.39295, 73.87635);
        map.addMarker(new MarkerOptions().position(marker).title("Cricket Ground"));
        marker = new LatLng(15.39208, 73.8756);
        map.addMarker(new MarkerOptions().position(marker).title("Student Activity Centre"));
        marker = new LatLng(15.39266, 73.87725);
        map.addMarker(new MarkerOptions().position(marker).title("Volleyball Courts"));
        marker = new LatLng(15.39336, 73.87732);
        map.addMarker(new MarkerOptions().position(marker).title("Football Field"));
        marker = new LatLng(15.39408, 73.8768);
        map.addMarker(new MarkerOptions().position(marker).title("Tennis Courts"));
        marker = new LatLng(15.39293, 73.87557);
        map.addMarker(new MarkerOptions().position(marker).title("Cricket Nets"));
        marker = new LatLng(15.39258, 73.87533);
        map.addMarker(new MarkerOptions().position(marker).title("Basketball Courts"));
        marker = new LatLng(15.39209, 73.87531);
        map.addMarker(new MarkerOptions().position(marker).title("Badminton Court"));
        marker = new LatLng(15.39233, 73.87557);
        map.addMarker(new MarkerOptions().position(marker).title("Squash Court"));
        marker = new LatLng(15.392558, 73.878976);
        map.addMarker(new MarkerOptions().position(marker).title("Ice n Spice"));
        marker = new LatLng(15.391435, 73.879045);
        map.addMarker(new MarkerOptions().position(marker).title("Monginis"));
        marker = new LatLng(15.39191, 73.8764);
        map.addMarker(new MarkerOptions().position(marker).title("Borkars Super Store"));
        marker = new LatLng(15.39199, 73.87626);
        map.addMarker(new MarkerOptions().position(marker).title("SBI Branch"));
        marker = new LatLng(15.39202, 73.87619);
        map.addMarker(new MarkerOptions().position(marker).title("SBI ATM"));
        marker = new LatLng(15.39218, 73.87621);
        map.addMarker(new MarkerOptions().position(marker).title("Persian Court"));
        marker = new LatLng(15.39225, 73.87627);
        map.addMarker(new MarkerOptions().position(marker).title("Men's Salon"));
        marker = new LatLng(15.39223, 73.87637);
        map.addMarker(new MarkerOptions().position(marker).title("Laxmi Laundry"));
        marker = new LatLng(15.39221, 73.8764);
        map.addMarker(new MarkerOptions().position(marker).title("Beauty Parlour"));
        marker = new LatLng(15.39219, 73.87644);
        map.addMarker(new MarkerOptions().position(marker).title("Vegetable Shop"));
        marker = new LatLng(15.39216, 73.87647);
        map.addMarker(new MarkerOptions().position(marker).title("Power Laundry"));
        marker = new LatLng(15.39214, 73.87651);
        map.addMarker(new MarkerOptions().position(marker).title("Book Shop"));
        marker = new LatLng(15.39211, 73.87655);
        map.addMarker(new MarkerOptions().position(marker).title("Vegetable Shop"));
        marker = new LatLng(15.38701, 73.87329);
        map.addMarker(new MarkerOptions().position(marker).title("Children's Park"));
        marker = new LatLng(15.38743, 73.87574);
        map.addMarker(new MarkerOptions().position(marker).title("Main gate"));
        marker = new LatLng(15.39024, 73.87511);
        map.addMarker(new MarkerOptions().position(marker).title("Children's Park"));
        marker = new LatLng(15.38899, 73.87638);
        map.addMarker(new MarkerOptions().position(marker).title("Crossroads"));
        marker = new LatLng(15.3921, 73.87963);
        map.addMarker(new MarkerOptions().position(marker).title("Flag Lawns"));
        marker = new LatLng(15.39362, 73.87911);
        map.addMarker(new MarkerOptions().position(marker).title("Workshop"));
        marker = new LatLng(15.39304, 73.88051);
        map.addMarker(new MarkerOptions().position(marker).title("Auditorium"));


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(centre)      // Sets the center of the map to Mountain View
                .zoom(16f)                   // Sets the zoom
                // Sets the orientation of the camera to east
                .tilt(50)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
