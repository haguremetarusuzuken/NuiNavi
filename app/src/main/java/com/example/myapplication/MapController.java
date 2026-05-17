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

    public MapController(Context context,GoogleMap googleMap){
        this.googleMap = googleMap;
        this.context = context;
    }

    public void moveToDefaultPosition(){
        LatLng position = new LatLng(35.681236, 139.767125);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }
    public void moveToCurrentPosition(double lat, double lng) {
        LatLng current = new LatLng(lat, lng);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 17));
    }
    public void showCcpMarker(double lat, double lng, int nuiResId) {
        LatLng position = new LatLng(lat, lng);

        if (ccpMarker == null) {
            ccpMarker = googleMap.addMarker(new MarkerOptions()
                    .position(position)
                    .icon(createSmallMarkerIcon(nuiResId,456,256))
                    .anchor(0.5f, 0.5f)
                    .title("CCP"));
        } else {
            ccpMarker.setPosition(position);
            ccpMarker.setIcon(createSmallMarkerIcon(nuiResId, 456, 256));
        }
    }

    public void updateCcpMarkerIcon(int nuiResId) {
        if (ccpMarker != null) {
            ccpMarker.setIcon(createSmallMarkerIcon(nuiResId, 456, 256));
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
