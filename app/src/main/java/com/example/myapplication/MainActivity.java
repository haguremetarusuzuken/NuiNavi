package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    LatLng defaultCcpPosition;
    LatLng defaultGoalPosition;
    private MapController mapController;
    private int selectedCcpId;
    private int selectedGoalId;

    public MainActivity(){
        this.defaultCcpPosition  = new LatLng(35.17098382507305, 136.88154061665807);
        this.defaultGoalPosition = new LatLng(35.16815568496989, 136.88077921306302);
        this.selectedCcpId = R.drawable.default_ccp;
        this.selectedGoalId = R.drawable.default_goalflag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initMap();
        Button optionButton = findViewById(R.id.optionButton);

        optionButton.setOnClickListener(v -> {

            Intent intent =
                    new Intent(this, OptionActivity.class);

            startActivity(intent);
        });
    }
    private void initMap() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapController = new MapController(this,googleMap);
        mapController.moveToDefaultPosition(defaultCcpPosition);

        mapController.showCcpMarker(defaultCcpPosition);
        mapController.showGoalMarker(defaultGoalPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* onMapReadyコールされるまでは実施しない */
        if (mapController != null) {
            reloadSelectedImage();
        }
    }

    private void reloadSelectedImage() {
        selectedCcpId  = loadSelectedImage("ccp","default_ccp");
        selectedGoalId = loadSelectedImage("goal","default_goalflag");
        if (mapController != null) {
            mapController.updateCcpMarkerIcon(selectedCcpId);
            mapController.updateGoalMarkerIcon(selectedGoalId);
        }
    }

    private int loadSelectedImage(String key, String defaultDrawableName){
        String drawableName = getSharedPreferences("app_settings", MODE_PRIVATE)
                .getString(key, defaultDrawableName);

        return getResources().getIdentifier(
                drawableName,
                "drawable",
                getPackageName()
        );
    }
}