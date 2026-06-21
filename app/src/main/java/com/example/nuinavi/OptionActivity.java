package com.example.nuinavi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OptionActivity extends AppCompatActivity {

    @Override
    /* オプション画面Create */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_option);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.option), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* 戻るボタンリスナー */
        TextView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        /* 自車位置・目的地マーク変更ボタンリスナー */
        TextView ChangeButton = findViewById(R.id.changeCcpAndGoalMarkButton);
        ChangeButton.setOnClickListener(v -> {
            Intent intent =
                    new Intent(this, MarkChangeActivity.class);

            startActivity(intent);
        });
    }
}