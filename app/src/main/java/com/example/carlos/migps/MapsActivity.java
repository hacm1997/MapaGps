package com.example.carlos.migps;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Marker mark;
    double latitud = 0.0;
    double longitud = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ubicacion();

    }

    private void añadir(double lati, double longit) {
        LatLng cordena = new LatLng(lati, longit);
        CameraUpdate ubica = CameraUpdateFactory.newLatLngZoom(cordena, 16);
        if (mark != null) mark.remove();
        mark = mMap.addMarker(new MarkerOptions().position(cordena).title("\"¡Estas Aquí!\"")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.aqui)));
        mMap.animateCamera(ubica);
    }

    private void Actualizar(Location location) {
        if (location != null) {
            latitud = location.getLatitude();
            longitud = location.getLongitude();
            añadir(latitud, longitud);
        }
    }

    LocationListener locationListener=new LocationListener(){
        @Override
        public void onLocationChanged(Location location)
        {
            Actualizar(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void ubicacion() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Actualizar(location);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locationListener);

    }

}
