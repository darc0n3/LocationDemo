package com.example.darce.locationdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    LocationListener locationListener;
    LocationManager locationManager;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //if we do have permission to use location then we will provide the location
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                //The 0 0 setting constantly checks for the users location. This is draining on the battery
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  locationListener);

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code to get the users location
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //The listener will look out for changes in the users location
        locationListener = new LocationListener() {
            //when the user moves
            @Override
            public void onLocationChanged(Location location) {

                Log.i("Location", location.toString());
            }

            //when the the service is  forceable changed
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            //method for when the service is enabled
            @Override
            public void onProviderEnabled(String s) {

            }

            //method for when the service is enabled
            @Override
            public void onProviderDisabled(String s) {

            }
        };

        //If device is running SDK < 23
        if (Build.VERSION.SDK_INT < 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //if dont have permission then ask for it

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {

                //we have permissions
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

        }
    }
}
