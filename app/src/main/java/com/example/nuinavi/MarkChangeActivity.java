package com.example.nuinavi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MarkChangeActivity extends AppCompatActivity {

    @Override
    /* マーク変更画面Create */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mark_change);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mark_change), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* 戻る2ボタンリスナー */
        TextView backButton = findViewById(R.id.backButton2);
        backButton.setOnClickListener(v -> finish());

        /* 自車位置マーク変更ボタンリスナー */
        TextView ccpChangeButton = findViewById(R.id.changeCcpMarkButton);
        ccpChangeButton.setOnClickListener(v -> {
            Intent intent =
                    new Intent(this, ImageSelectActivity.class);
            /* 画面遷移が分かるように種別設定(自車位置マーク変更) */
            intent.putExtra("mark_type", "ccp");
            startActivity(intent);
        });

        /* 目的地マーク変更ボタンリスナー */
        TextView goalMarkChangeButton = findViewById(R.id.changeGoalMarkButton);
        goalMarkChangeButton.setOnClickListener(v -> {
            Intent intent =
                    new Intent(this, ImageSelectActivity.class);
            /* 画面遷移が分かるように種別設定(目的地マーク変更) */
            intent.putExtra("mark_type", "goal");
            startActivity(intent);
        });
    }
}