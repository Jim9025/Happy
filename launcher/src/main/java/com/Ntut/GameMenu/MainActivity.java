package com.Ntut.GameMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.start_game_button);
        startButton.setOnClickListener(view -> launchUnity());

        Button scoreTriangleButton = findViewById(R.id.btn_score_triangle);
        scoreTriangleButton.setOnClickListener(view -> openTriangleScores());
    }

    private void launchUnity() {
        Intent intent = new Intent(this, com.unity3d.player.UnityPlayerGameActivity.class);
        startActivity(intent);
        finish();
    }

    private void openTriangleScores() {
        Intent intent = new Intent(this, ScoreTriangleActivity.class);
        startActivity(intent);
    }
}
