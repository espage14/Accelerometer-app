package com.example.espage14.accelerometer;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Observable;

/**
 * Created by espage14 on 9/12/2016.
 */
public class LocationHandler extends Observable implements LocationListener{

    LocationManager locManager = null;
    Location loc = null;



    double latitude;
    double longitude;

    public LocationHandler(Activity act){
        locManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);

        if (locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            loc = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        }


        latitude = loc.getLatitude();
        longitude = loc.getLongitude();


    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        double [] ll = {latitude, longitude};

        setChanged();
        notifyObservers(ll);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
