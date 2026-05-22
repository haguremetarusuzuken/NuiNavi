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
    /* 画像加工用コンテキスト */
    private final Context context;
    /* GoogleMap操作用オブジェクト */
    private final GoogleMap googleMap;
    /* 自車位置マーク描画座標 */
    private Marker ccpMarker;
    /* 目的地マーク描画座標 */
    private Marker goalMarker;

    /* MapContollerコンストラクタ */
    public MapController(Context context,GoogleMap googleMap){
        this.googleMap = googleMap;
        this.context = context;
    }

    /* デフォルト自車位置遷移処理 */
    /* ToDo:マジックナンバー使わないようにしたい。 */
    public void moveToDefaultPosition(LatLng position){
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }

    /* 現在地測位時自車位置遷移処理 */
    /* ToDo:マジックナンバー使わないようにしたい。 */
    public void moveToCurrentPosition(LatLng position) {
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
    }

    /* 自車位置描画処理 */
    /* ToDo:マジックナンバー使わないようにしたい。 */
    public void showCcpMarker(LatLng position) {
        if (this.ccpMarker == null) {
            this.ccpMarker = this.googleMap.addMarker(new MarkerOptions()
                       .position(position)
                        .icon(createSmallMarkerIcon(R.drawable.default_ccp, 192, 192))
                        .anchor(0.5f, 0.5f)
                        .title("CCP"));
        } else {
            this.ccpMarker.setPosition(position);
            this.ccpMarker.setIcon(createSmallMarkerIcon(R.drawable.default_ccp, 192, 192));
        }
    }
    /* 目的地描画処理 */
    /* ToDo:マジックナンバー使わないようにしたい。 */
    public void showGoalMarker(LatLng position) {
        if (this.goalMarker == null) {
            this.goalMarker = this.googleMap.addMarker(new MarkerOptions()
                        .position(position)
                        .icon(createSmallMarkerIcon(R.drawable.default_goalflag, 192, 192))
                        .anchor(0.5f, 1.0f)
                        .title("GOAL"));
        } else {
            this.goalMarker.setPosition(position);
            this.goalMarker.setIcon(createSmallMarkerIcon(R.drawable.default_goalflag, 192, 192));
        }
    }

    /* 自車位置描画更新処理 */
    /* ToDo:マジックナンバー使わないようにしたい。 */
    public void updateCcpMarkerIcon(int ccpResId) {
        if (this.ccpMarker != null) {
            this.ccpMarker.setIcon(createSmallMarkerIcon(ccpResId, 192, 192));
        }
    }

    /* 目的地描画更新処理 */
    /* ToDo:マジックナンバー使わないようにしたい。 */
    public void updateGoalMarkerIcon(int goalResId) {
        if (this.goalMarker != null) {
            this.goalMarker.setIcon(createSmallMarkerIcon(goalResId, 192, 192));
        }
    }

    /* マーク描画時サイズ変更処理 */
    private BitmapDescriptor createSmallMarkerIcon(int resId, int width, int height) {
        Bitmap original = BitmapFactory.decodeResource(
                this.context.getResources(),
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
