package com.example.lab4.Service;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.lab4.API.APIRequests;
import com.example.lab4.API.URLS;
import com.example.lab4.LoginUI.LoginActivity;
import com.example.lab4.MainActivity;
import com.example.lab4.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.stomped.stomped.client.StompedClient;

public class LocationService extends Service {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final String TAG = "LocationService";
    private StompedClient client;

    private LocationRequest locationRequest;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        client = new StompedClient.StompedClientBuilder().build(URLS.WEBSOCKET_URL);
        Intent notificationIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, AppChannel.CHANNEL_ID)
                .setContentTitle("Location service")
                .setContentText("Location")
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        return START_NOT_STICKY;
    }


    @Override
    public void onCreate() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if(locationResult == null){
                return;
            }
            for( Location location : locationResult.getLocations()){

                client.send("/app/user-all", "{\"username\":\" " + URLS.username + " \", \"longitude\":\" " + location.getLongitude() + " \", \"latitude\": \" "+ location.getLatitude()+"\"}");
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        APIRequests.setAvailability(false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
