package com.example.amp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    boolean isGPSEnabled = false;
    String loc="Sorry.. Location is off!";
    Location location; // Location

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public void getLocation() {
        try {
            //Log.d("location1","inside getLocation");
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // Getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                //Log.d("location2","inside GPS");
                if (location == null) {
                    //Log.d("location3","inside Location");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        //Log.d("location4","inside LocationManager");
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            //Log.d("location5","inside Location2");
                            loc="Lattitude : "+location.getLatitude()+", Longitude : "+location.getLongitude()+"\nAccuracy : "+location.getAccuracy();
                        }
                    }
                }
                else if (location != null) {
                        //Log.d("location5","inside Location2");
                        loc="Lattitude : "+location.getLatitude()+", Longitude : "+location.getLongitude()+"\nAccuracy : "+location.getAccuracy();
                }
            }

            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

*/
    @Override
    public void onLocationChanged(Location location) {
    }


    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}