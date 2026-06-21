package com.example.nuinavi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import android.location.Location;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationController {

    /* コンテキストオブジェクト */
    private final Context context;

    /* ロケーションクライアントオブジェクト */
    private final FusedLocationProviderClient fusedLocationClient;

    /* ロケーション情報要求オブジェクト(定期更新用) */
    private final LocationRequest locationRequest;

    /* 目標GPS更新期間(秒) */
    private static final int CURRENT_GPS_UPDATE_SECONDS = 5000;
    /* 最短GPS更新期間(秒) */
    private static final int CURRENT_GPS_UPDATE_SECONDS_MIN = 3000;

    public LocationController(Context context) {
        this.context = context;

        this.fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context);

        this.locationRequest = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                CURRENT_GPS_UPDATE_SECONDS
        ).setMinUpdateIntervalMillis(CURRENT_GPS_UPDATE_SECONDS_MIN).build();
    }

    /* 位置情報権限所持確認 */
    public boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(
                this.context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ||     ActivityCompat.checkSelfPermission(
                this.context,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    /* 現在地(GPS)取得要求処理 */
    @SuppressWarnings("MissingPermission")
    public void requestCurrentLocation(OnSuccessListener<Location> listener) {
        /* 事前にパーミッション確認をしている為、問題なし。 */
        if (!this.hasLocationPermission()) {
            return;
        }

        this.fusedLocationClient.getLastLocation()
                .addOnSuccessListener(listener);
    }

    @SuppressWarnings("MissingPermission")
    /* 現在地(GPS)更新開始処理 */
    public void startLocationUpdates(LocationCallback callback){
        if(!this.hasLocationPermission()){
            return;
        }

        this.fusedLocationClient.requestLocationUpdates(
                locationRequest,
                callback,
                Looper.getMainLooper()
        );
    }

    /* 現在地(GPS)更新終了処理 */
    public void stopLocationUpdates(LocationCallback callback){
        this.fusedLocationClient.removeLocationUpdates(
                callback
        );
    }
}
