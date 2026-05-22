package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class ImageSelectActivity extends AppCompatActivity {

    private Map<Integer,String> imageMap = new HashMap<>();

    @Override
    /* イメージ選択画面Create */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_select);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.image_select), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String markType = getIntent().getStringExtra("mark_type");

        /* 画像ID/リソースID紐づけ */
        this.setAllImageMap();

        /* 戻るボタン3リスナー */
        TextView backButton = findViewById(R.id.backButton3);
        backButton.setOnClickListener(v -> finish());

        /* デフォルト画像変更リスナー(自車位置) */
        findViewById(R.id.default_ccp).setVisibility(
                "ccp".equals(markType) ? View.VISIBLE : View.GONE
        );

        /* デフォルト画像変更リスナー(目的地) */
        findViewById(R.id.default_goalflag).setVisibility(
                "goal".equals(markType) ? View.VISIBLE : View.GONE
        );

        /* 各種画像選択ボタンリスナー */
        for( Map.Entry<Integer,String> entry: imageMap.entrySet() ){
            findViewById(entry.getKey()).setOnClickListener(v -> {
                this.saveMarkImage(entry.getValue(),markType);
                finish();
            });
        }
    }

    private void setAllImageMap(){

        imageMap.put(R.id.saki_nui,"saki_nui");
        imageMap.put(R.id.tema_nui,"tema_nui");
        imageMap.put(R.id.koto_nui,"koto_nui");
        imageMap.put(R.id.tina_nui,"tina_nui");
        imageMap.put(R.id.hiro_nui,"hiro_nui");
        imageMap.put(R.id.misu_nui,"misu_nui");
        imageMap.put(R.id.default_ccp,"default_ccp");
        imageMap.put(R.id.default_goalflag,"default_goalflag");
    }

    /* マーク画像イメージ保存 */
    private void saveMarkImage(String drawableName, String markType) {

        String saveKey;

        /* 遷移元画面が自車位置変更 or 目的地変更かを記憶しKeyとして登録 */
        if("ccp".equals(markType)) {
            saveKey = "ccp";
        } else {
            saveKey = "goal";
        }

        getSharedPreferences("app_settings", MODE_PRIVATE)
                .edit()
                .putString(saveKey, drawableName)
                .apply();
    }
}