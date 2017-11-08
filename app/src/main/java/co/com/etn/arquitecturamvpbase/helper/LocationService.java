package co.com.etn.arquitecturamvpbase.helper;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Erika on 24/10/2017.
 */

public class LocationService extends Service implements LocationListener {

    private static final long MIN_DISTANCE_FOR_UPDATE = 15;
    private static final long MIN_TIME_FOR_UPDATES = 1000 * 60;
    private static final String TAG = "LocationService";
    private final Context context;
    private LocationManager locationManager;
    private boolean checkGPS = false;
    private boolean checkNetwork = false;
    private boolean canGetLocation = false;
    private Location location;
    private double latitude;
    private double longitude;


    public LocationService(Context context) {
        this.context = context;
        getLocation();
    }

    private Location getLocation(){
        try {

            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            // Obtener el estado del GPS y network

            checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!checkGPS && !checkNetwork) {
                Toast.makeText(context, "PROBLEMAS PARA OBTENER LA UBICACION", Toast.LENGTH_SHORT).show();
                this.canGetLocation = false;
            } else {
                this.canGetLocation = true;

                //check GPS Location
                if (checkGPS) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        //showSettingsAlert();
                    }else{
                        Log.e(TAG,"Error");
                    }

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATES, MIN_DISTANCE_FOR_UPDATE, this );

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    }
                }

                // Check Network
                if (checkNetwork) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        showSettingsAlert();
                    }

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATES, MIN_DISTANCE_FOR_UPDATE, this );

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }

        return location;

    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("GPS no está habilitado");
        alertDialog.setMessage("¿Quieres prenderlo?");
        alertDialog.setPositiveButton("De una", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("No quiero", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

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