package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class MapController {
    private final Context context;
    private final GoogleMap googleMap;
    private Marker ccpMarker;
    private Marker goalMarker;

    public MapController(Context context,GoogleMap googleMap){
        this.googleMap = googleMap;
        this.context = context;
    }

    public void moveToDefaultPosition(LatLng position){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }

    public void moveToCurrentPosition(LatLng position) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
    }

    public void showCcpMarker(LatLng position) {
        if (ccpMarker == null) {
            ccpMarker = googleMap.addMarker(new MarkerOptions()
                    .position(position)
                    .icon(createSmallMarkerIcon(R.drawable.default_ccp, 192, 192))
                    .anchor(0.5f, 0.5f)
                    .title("CCP"));
        } else {
            ccpMarker.setPosition(position);
            ccpMarker.setIcon(createSmallMarkerIcon(R.drawable.default_ccp, 192, 192));
        }
    }
    public void showGoalMarker(LatLng position) {
        if (goalMarker == null) {
            goalMarker = googleMap.addMarker(new MarkerOptions()
                    .position(position)
                    .icon(createSmallMarkerIcon(R.drawable.default_goalflag, 192, 192))
                    .anchor(0.5f, 1.0f)
                    .title("GOAL"));
        } else {
            goalMarker.setPosition(position);
            goalMarker.setIcon(createSmallMarkerIcon(R.drawable.default_goalflag, 192, 192));
        }
    }

    public void updateCcpMarkerIcon(int ccpResId) {
        if (ccpMarker != null) {
            ccpMarker.setIcon(createSmallMarkerIcon(ccpResId, 192, 192));
        }
    }

    public void updateGoalMarkerIcon(int goalResId) {
        if (goalMarker != null) {
            goalMarker.setIcon(createSmallMarkerIcon(goalResId, 192, 192));
        }
    }

    private BitmapDescriptor createSmallMarkerIcon(int resId, int width, int height) {
        Bitmap original = BitmapFactory.decodeResource(
                context.getResources(),
                resId
        );

        Bitmap resized = Bitmap.createScaledBitmap(
                original,
                width,
                height,
                false
        );
        return BitmapDescriptorFactory.fromBitmap(resized);
    }
}
