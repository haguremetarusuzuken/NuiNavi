package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    /* デフォルト自車位置 */
    LatLng defaultCcpPosition;
    /* デフォルト目標位置 */
    LatLng defaultGoalPosition;
    /* GoogleMap操作コンテキスト */
    private MapController mapController;
    /* 選択自車位置マークID */
    private int selectedCcpId;
    /* 選択目的地マークID */
    private int selectedGoalId;

    /* 自車位置座標(GPS測位後) */
    /* ToDo:GPS機能のための仮実装 */
    LatLng gpsCcpPosition;
    /* 目的地座標(GPS測位後) */
    /* ToDo:GPS機能のための仮実装 */
    LatLng gpsGoalPosition;

    /* MainActivityコンストラクタ */
    public MainActivity(){
        /* デフォルト自車位置は名古屋駅 */
        this.defaultCcpPosition  = new LatLng(35.17098382507305, 136.88154061665807);
        /* デフォルト目的地はアニメイト名古屋店 */
        this.defaultGoalPosition = new LatLng(35.16815568496989, 136.88077921306302);
        /* デフォルト選択自車マークID */
        this.selectedCcpId = R.drawable.default_ccp;
        /* デフォルト選択目的地マークID */
        this.selectedGoalId = R.drawable.default_goalflag;
    }

    @Override
    /* Main画面Create */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* GoogleMap描画準備 */
        this.initMap();

        /* 設定ボタンリスナー */
        Button optionButton = findViewById(R.id.optionButton);

        optionButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(this, OptionActivity.class);

            startActivity(intent);
        });
    }

    /* GoogleMap描画準備初期化 */
    private void initMap() {

        /* xmlよりFragment取得 */
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        /* Fragment有効時に描画要求 */
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    /* GoogleMap準備完了(非同期) */
    public void onMapReady(@NonNull GoogleMap googleMap) {
        /* ToDo:本来は現在地測位してからccp移動させるのが本来のナビアプリ */
        /* ToDo:ユーザーへの権利許諾もとる必要あり。 */
        this.mapController = new MapController(this,googleMap);
        this.mapController.moveToDefaultPosition(defaultCcpPosition);

        /* 前回画像設定があれば読み出す */
        int ccpResId  = loadSelectedImage("ccp","default_ccp");
        int goalResId = loadSelectedImage("goal","default_goalflag");

        this.mapController.showCcpMarker(defaultCcpPosition,ccpResId);
        /* ToDo:デフォルトで目的地描画しているが、将来的に不要の認識 */
        this.mapController.showGoalMarker(defaultGoalPosition,goalResId);
    }

    @Override
    /* 画面レジューム処理 */
    protected void onResume() {
        super.onResume();
        /* onMapReadyコールされるまでは実施しない */
        if (mapController != null) {
            this.reloadSelectedImage();
        }
    }

    private void reloadSelectedImage() {
        /* 事前に設定ない場合デフォルトでマーク描画 */
        this.selectedCcpId  = loadSelectedImage("ccp","default_ccp");
        this.selectedGoalId = loadSelectedImage("goal","default_goalflag");
        if (mapController != null) {
            /* 現在地をとるべき */
            this.mapController.showCcpMarker(defaultCcpPosition,selectedCcpId);
            this.mapController.showGoalMarker(defaultGoalPosition,selectedGoalId);
        }
    }

    private int loadSelectedImage(String key, String defaultDrawableName){
        /* 簡易保存領域よりユーザ設定の設定画像を判別し、該当する画像のリソースIDを返却 */
        String drawableName = getSharedPreferences("app_settings", MODE_PRIVATE)
                .getString(key, defaultDrawableName);

        return getResources().getIdentifier(
                drawableName,
                "drawable",
                getPackageName()
        );
    }
}