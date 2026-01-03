package com.Ntut.GameMenu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String name = getSharedPreferences("GameData", MODE_PRIVATE).getString("CurrentPlayer", "玩家");
        TextView tvTitle = findViewById(R.id.tvResultTitle);
        tvTitle.setText(name + " 注意力年齡分析結果");

        // 取得 Unity 分數
        int s1 = getIntent().getIntExtra("BALLOON_SCORE", 0);
        int s2 = getIntent().getIntExtra("CUBE_SCORE", 0);
        int s3 = getIntent().getIntExtra("PADDLE_SCORE", 0);
        if (s1 > 0 || s2 > 0 || s3 > 0) {
            getSharedPreferences("GameData", MODE_PRIVATE).edit()
                    .putInt("BestScore_Balloon", s1)
                    .putInt("BestScore_Cube", s2)
                    .putInt("BestScore_Paddle", s3)
                    .apply();
        }
        // 更新圖表
        TriangleChartView chart = findViewById(R.id.triangleChart);
        chart.setScores(s1, s2, s3);

        // 簡單邏輯：年齡分析 (假設總分越高年齡越輕，這部分可自行調整公式)
        int avg = (s1 + s2 + s3) / 3;
        int age = Math.max(10, 80 - (avg / 2));
        TextView tvAge = findViewById(R.id.tvAgeResult);
        tvAge.setText("分析注意力年齡為：" + age + " 歲");

        findViewById(R.id.btnBackMain).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
