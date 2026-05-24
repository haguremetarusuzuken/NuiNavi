package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import android.location.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationController {

    private final Context context;
    private final FusedLocationProviderClient fusedLocationClient;

    public LocationController(Context context) {
        this.context = context;
        this.fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context);
    }

    /* 位置情報権限所持確認 */
    public boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ||     ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    /* 現在地(GPS)取得要求処理 */
    @SuppressWarnings("MissingPermission")
    public void requestCurrentLocation(OnSuccessListener<Location> listener) {
        /* 事前にパーミッション確認をしている為、問題なし。 */
        if (!hasLocationPermission()) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(listener);
    }
}
