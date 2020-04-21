package com.example.carppol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;
    ArrayList markerPoints=new ArrayList();
    public void onClick(View v)
    {
        TextInputEditText addressField=(TextInputEditText) findViewById(R.id.des);
        String address= addressField.getText().toString();
        List<Address> addressList=null;
        MarkerOptions mo=new MarkerOptions();
        if(!TextUtils.isEmpty(address))
        {
            Geocoder geocoder= new Geocoder(this);
            try {
                addressList=geocoder.getFromLocationName(address,6);
                if(addressList!=null)
                {
                    for(int i=0;i<addressList.size();i++)
                    {
                        Address userAddress=addressList.get(i);
                        LatLng latlng=new LatLng(userAddress.getLatitude(),userAddress.getLongitude());
                        markerPoints.add(latlng);
                        mo.position(latlng);
                        mo.title("User Current Locaation");
                        mo.icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        mMap.addMarker(mo);

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                    }
                }
                else
                {
                    Toast.makeText(this,"LOCATION NOT FOUND",Toast.LENGTH_SHORT).show();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(this,"Enter an address to continue",Toast.LENGTH_SHORT).show();
        }


    }
    public void onClick2(View v)
    {
        TextInputEditText addressField=(TextInputEditText) findViewById(R.id.sou);
        String address= addressField.getText().toString();
        List<Address> addressList=null;
        MarkerOptions mo=new MarkerOptions();
        if(!TextUtils.isEmpty(address))
        {
            Geocoder geocoder= new Geocoder(this);
            try {
                addressList=geocoder.getFromLocationName(address,6);
                if(addressList!=null)
                {
                    for(int i=0;i<addressList.size();i++)
                    {
                        Address userAddress=addressList.get(i);
                        LatLng latlng=new LatLng(userAddress.getLatitude(),userAddress.getLongitude());
                        markerPoints.add(latlng);
                        mo.position(latlng);
                        mo.title("User Current Locaation");
                        mo.icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        mMap.addMarker(mo);

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                    }
                }
                else
                {
                    Toast.makeText(this,"LOCATION NOT FOUND",Toast.LENGTH_SHORT).show();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(this,"Enter an address to continue",Toast.LENGTH_SHORT).show();
        }

    }
//////////////////////////////////

//



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},


                    REQUEST_LOCATION_PERMISSION);
        }
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
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        enableMyLocation();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    public void GiveRide(View view) {
        Intent intent = new Intent(this, GiveRide.class);
        startActivity(intent);
    }

    public void TakeRide(View view) {
        Intent intent = new Intent(this, TakeRide.class);
        startActivity(intent);
    }
}
