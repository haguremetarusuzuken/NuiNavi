package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    LatLng defaultPosition;
    private MapController mapController;
    private int selectedNuiResId = R.drawable.koto_nui;

    public MainActivity(){
        this.defaultPosition = new LatLng((double)35.68111,(double)139.76667);
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
        mapController.moveToDefaultPosition();
        String drawableName = getSharedPreferences("app_settings", MODE_PRIVATE)
                .getString("current_nui", "koto_nui");

        reloadSelectedNui();

        int nuiResId = getResources().getIdentifier(
                drawableName,
                "drawable",
                getPackageName()
        );
        mapController.showCcpMarker(this.defaultPosition.latitude, this.defaultPosition.longitude, nuiResId);
    }
    private int loadSelectedNuiImage() {
        String drawableName = getSharedPreferences("app_settings", MODE_PRIVATE)
                .getString("current_nui", "koto_nui");

        return getResources().getIdentifier(
                drawableName,
                "drawable",
                getPackageName()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("NUI_DEBUG", "MainActivity onResume");
        reloadSelectedNui();
    }

    private void reloadSelectedNui() {
        Log.d("NUI_DEBUG", "reloadSelectedNui called");
        selectedNuiResId = loadSelectedNuiImage();

        Log.d("NUI_DEBUG", "selectedNuiResId = " + selectedNuiResId);
        Log.d("NUI_DEBUG", "mapController = " + mapController);

        if (mapController != null) {
            mapController.updateCcpMarkerIcon(selectedNuiResId);
        }
    }
}