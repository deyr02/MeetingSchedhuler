package com.example.meetingschedhuler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ContactLocation extends AppCompatActivity {
    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private Integer REQUEST_CODE = 111;
    private ConnectivityManager connectivityManager;
    private String  lookupAddress;
    private String firstName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_location);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.g_maps);
        client = LocationServices.getFusedLocationProviderClient(ContactLocation.this);
        if(getIntent().hasExtra("Address")){
            lookupAddress =  getIntent().getStringExtra("Address");
            firstName = getIntent().getStringExtra("Name");
        }
        ActionBar ab = getSupportActionBar();
        if(ab!= null){
            ab.setTitle(firstName+"'s location");
        }


        if (ActivityCompat.checkSelfPermission(ContactLocation.this,  Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
        else {
            ActivityCompat.requestPermissions(ContactLocation.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION,}, REQUEST_CODE);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                    if(location != null){
                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                try{
                                   if( checkConnection()) {
                                       LatLng _latLng = getLocationFromAddress(lookupAddress);
                                       // LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                       MarkerOptions markerOptions = new MarkerOptions().position(_latLng).title(firstName + "'s location");
                                       googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_latLng, 14));
                                       googleMap.addMarker(markerOptions).isInfoWindowShown();
                                   }
                                   else {
                                       Toast.makeText(ContactLocation.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                                   }


                                   }
                                catch (Exception ex){
                                    Toast.makeText(ContactLocation.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                                }


                            }
                        });
                    }
            }
        });
    }

    ///https://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            }
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkConnection() throws Exception {
        connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(ContactLocation.this);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}