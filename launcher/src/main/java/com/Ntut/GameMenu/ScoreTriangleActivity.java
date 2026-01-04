package com.Ntut.GameMenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ScoreTriangleActivity extends Activity {

    private TextView balloonScore;
    private TextView cubeScore;
    private TextView paddleScore;
    private TriangleScoreView triangleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_triangle);

        balloonScore = findViewById(R.id.score_balloon);
        cubeScore = findViewById(R.id.score_cube);
        paddleScore = findViewById(R.id.score_paddle);
        triangleView = findViewById(R.id.triangle_score_view);

        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());

        loadScores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadScores();
    }

    private void loadScores() {
        ScoreBridge.ScoreData scores = ScoreBridge.getScores(this);
        balloonScore.setText(getString(R.string.score_format, scores.balloon));
        cubeScore.setText(getString(R.string.score_format, scores.cube));
        paddleScore.setText(getString(R.string.score_format, scores.paddle));
        triangleView.setScores(scores.balloon, scores.cube, scores.paddle);
    }
}
