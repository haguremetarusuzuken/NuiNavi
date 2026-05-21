package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ImageSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_select);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.image_select), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView backButton = findViewById(R.id.backButton3);
        backButton.setOnClickListener(v -> finish());

        findViewById(R.id.saki_nui).setOnClickListener(v -> {
            saveNuiImage("saki_nui");
            finish();
        });
        findViewById(R.id.tema_nui).setOnClickListener(v -> {
            saveNuiImage("tema_nui");
            finish();
        });
        findViewById(R.id.koto_nui).setOnClickListener(v -> {
            saveNuiImage("koto_nui");
            finish();
        });
        findViewById(R.id.tina_nui).setOnClickListener(v -> {
            saveNuiImage("tina_nui");
            finish();
        });
        findViewById(R.id.hiro_nui).setOnClickListener(v -> {
            saveNuiImage("hiro_nui");
            finish();
        });
        findViewById(R.id.misu_nui).setOnClickListener(v -> {
            saveNuiImage("misu_nui");
            finish();
        });
    }

    private void saveNuiImage(String drawableName) {

        String markType = getIntent().getStringExtra("mark_type");
        String saveKey;

        if("ccp".equals(markType)) {
            saveKey = "ccp";
        } else {
            saveKey = "goal";
        }
        Log.d("NUI_DEBUG","markType is " + markType );
        Log.d("NUI_DEBUG","saveKey is " + saveKey );
        Log.d("NUI_DEBUG","drawableName is " + drawableName);
        getSharedPreferences("app_settings", MODE_PRIVATE)
                .edit()
                .putString(saveKey, drawableName)
                .apply();
    }
}