package pl.com.turski.gps.listener;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import pl.com.turski.gps.App;

/**
 * User: Adam
 */
public class LocationListenerImpl implements LocationListener {

    public void onLocationChanged(Location location) {
        Context appCtx = App.getAppContext();

        double latitude, longitude;

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Intent filterRes = new Intent();
        filterRes.setAction("pl.com.turski.trak.gps.intent.action.LOCATION");
        filterRes.putExtra("latitude", latitude);
        filterRes.putExtra("longitude", longitude);
        appCtx.sendBroadcast(filterRes);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void onProviderEnabled(String provider) {

    }

    public void onProviderDisabled(String provider) {

    }
}