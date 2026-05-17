package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MarkChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mark_change);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mark_change), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView backButton = findViewById(R.id.backButton2);
        backButton.setOnClickListener(v -> finish());

        TextView ccpChangeButton = findViewById(R.id.changeCcpMarkButton);
        ccpChangeButton.setOnClickListener(v -> {
            Intent intent =
                    new Intent(this, ImageSelectActivity.class);
            intent.putExtra("mark_type", "ccp");
            startActivity(intent);
        });

        TextView goalMarkChangeButton = findViewById(R.id.changeGoalMarkButton);
        goalMarkChangeButton.setOnClickListener(v -> {
            Intent intent =
                    new Intent(this, ImageSelectActivity.class);
            intent.putExtra("mark_type", "goal");
            startActivity(intent);
        });

        TextView returnTopButton = findViewById(R.id.returnTopButton);
        returnTopButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}