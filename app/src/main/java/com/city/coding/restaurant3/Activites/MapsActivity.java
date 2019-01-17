package com.city.coding.restaurant3.Activites;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.city.coding.restaurant3.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private String TAG = "MapsActivity";
    private GoogleMap mMap;
    private Intent intent;
    private float lat ,longi ;
    private String sLat ,sLongi, storeName;
    private ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        intent = getIntent();
        sLat= intent.getStringExtra("lati");
        sLongi = intent.getStringExtra("longi");
        lat = Float.parseFloat(sLat);
        longi = Float.parseFloat(sLongi);
        Log.e(TAG, "onCreate: "+" lati is "+lat);
        Log.e(TAG, "onCreate: "+" longi is "+longi);
        Log.e(TAG, "onCreate: "+" lati + 1  is "+(lat + 1));
        Log.e(TAG, "onCreate: "+" longi + 1  is "+(longi+1));


        storeName =intent.getStringExtra("storeName");
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, longi);
        mMap.addMarker(new MarkerOptions().position(sydney).title(storeName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String url = "https://www.google.com/maps/search/?api=1&query="+sLat+","+sLongi+"";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return false;
            }
        });
    }
}
