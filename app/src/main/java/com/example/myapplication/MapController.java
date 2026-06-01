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

    private static final int MARKER_WIDTH_SIZE = 192;
    private static final int MARKER_HEIGHT_SIZE = 192;

    /* MapContollerコンストラクタ */
    public MapController(Context context, GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.context = context;
    }

    /* デフォルト自車位置遷移処理 */
    /* ToDo:マジックナンバー使わないようにしたい。 */
    public void moveToDefaultPosition(LatLng position) {
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }

    /* 現在地測位時自車位置遷移処理 */
    /* ToDo:マジックナンバー使わないようにしたい。 */
    public void moveToCurrentPosition(LatLng position) {
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }

    /* 自車位置描画処理 */
    public void showCcpMarker(LatLng position, int ccpResId) {
        if (this.ccpMarker == null) {
            createMarker(position, ccpResId, "ccp");
        } else {
            updateMarker(position, ccpResId, "ccp");
            updateMarkerIcon(position, ccpResId, "ccp");
        }
    }
    /* 目的地描画処理 */
    public void showGoalMarker(LatLng position, int goalResId) {
        if (this.goalMarker == null) {
            createMarker(position, goalResId, "goal");
        } else {
            updateMarker(position, goalResId, "goal");
            updateMarkerIcon(position, goalResId, "goal");
        }
    }

    /* 自車・目的地マーク生成 */
    private void createMarker(LatLng position, int resId, String markType) {
        if ("ccp".equals(markType)) {
            this.ccpMarker = this.googleMap.addMarker(new MarkerOptions()
                    .position(position)
                    .icon(createSmallMarkerIcon(resId, MARKER_WIDTH_SIZE, MARKER_HEIGHT_SIZE))
                    .anchor(0.5f, 0.5f)
                    .title("CCP"));
        } else if ("goal".equals(markType)) {
            this.goalMarker = this.googleMap.addMarker(new MarkerOptions()
                    .position(position)
                    .icon(createSmallMarkerIcon(resId,MARKER_WIDTH_SIZE, MARKER_HEIGHT_SIZE))
                    .anchor(0.5f, 1.0f)
                    .title("GOAL"));
        } else {
            /* 処理なし */
        }
    }

    /* 自車・目的地マーク更新 */
    /* API引数統一のため未使用引数含む */
    private void updateMarker(LatLng position, int resId, String markType) {
        if ("ccp".equals(markType)) {
            this.ccpMarker.setPosition(position);
        } else if ("goal".equals(markType)) {
            this.goalMarker.setPosition(position);
        } else {
            /* 処理なし */
        }
    }

    /* 自車・目的地マーク画像更新 */
    /* API引数統一のため未使用引数含む */
    private void updateMarkerIcon(LatLng position, int resId, String markType) {
        if ("ccp".equals(markType)) {
            this.ccpMarker.setIcon(createSmallMarkerIcon(resId, MARKER_WIDTH_SIZE, MARKER_HEIGHT_SIZE));
        } else if ("goal".equals(markType)) {
            this.goalMarker.setIcon(createSmallMarkerIcon(resId, MARKER_WIDTH_SIZE, MARKER_HEIGHT_SIZE));
        } else {
            /* 処理なし */
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
