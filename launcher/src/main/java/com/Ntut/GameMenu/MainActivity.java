package com.Ntut.GameMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.unity3d.player.UnityPlayerGameActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editUserName;
    private ActivityResultLauncher<Intent> unityLauncher;
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
        Button btnViewResult = findViewById(R.id.btnViewResult);

        btnViewResult.setOnClickListener(v -> {
            // 取得儲存的歷史分數 (如果沒有紀錄，預設為 0)
            SharedPreferences pref = getSharedPreferences("GameData", MODE_PRIVATE);
            int s1 = pref.getInt("BestScore_Balloon", 0);
            int s2 = pref.getInt("BestScore_Cube", 0);
            int s3 = pref.getInt("BestScore_Paddle", 0);

            Intent resIntent = new Intent(this, ResultActivity.class);
            resIntent.putExtra("BALLOON_SCORE", s1);
            resIntent.putExtra("CUBE_SCORE", s2);
            resIntent.putExtra("PADDLE_SCORE", s3);
            startActivity(resIntent);
        });

        editUserName = findViewById(R.id.editUserName);
        Button btnStart = findViewById(R.id.btnStart);
        Button btnExit = findViewById(R.id.btnExit);

        unityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == -1 && result.getData() != null) {
                        // 跳轉至第三畫面（分析畫面）
                        Intent resIntent = new Intent(this, ResultActivity.class);
                        resIntent.putExtras(result.getData()); // 傳遞分數
                        startActivity(resIntent);
                    }
                }
        );

        btnStart.setOnClickListener(v -> {
            String name = editUserName.getText().toString().trim();
            if (name.isEmpty()) {
                // 顯示 Toast 提示訊息
                Toast.makeText(MainActivity.this, "請輸入使用者名稱才能開始遊戲", Toast.LENGTH_SHORT).show();
            } else {
                getSharedPreferences("GameData", MODE_PRIVATE).edit().putString("CurrentPlayer", name).apply();

                Intent intent = new Intent(this, com.unity3d.player.UnityPlayerGameActivity.class);
                unityLauncher.launch(intent);
            }
        });

        btnExit.setOnClickListener(v -> finish());

    }
}
