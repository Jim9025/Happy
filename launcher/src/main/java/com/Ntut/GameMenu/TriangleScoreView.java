package com.Ntut.GameMenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class TriangleScoreView extends View {

    private final Paint axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int balloon;
    private int cube;
    private int paddle;

    public TriangleScoreView(Context context) {
        super(context);
        init();
    }

    public TriangleScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setStrokeWidth(3f);
        axisPaint.setColor(Color.parseColor("#1E88E5"));

        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.parseColor("#331E88E5"));

        textPaint.setColor(Color.parseColor("#1E1E1E"));
        textPaint.setTextSize(36f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setScores(int balloonScore, int cubeScore, int paddleScore) {
        this.balloon = balloonScore;
        this.cube = cubeScore;
        this.paddle = paddleScore;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();
        float centerX = width / 2f;
        float centerY = height / 2f;
        float radius = Math.min(width, height) * 0.35f;

        float topX = centerX;
        float topY = centerY - radius;
        float leftX = centerX - (float) (radius * Math.sin(Math.toRadians(60)));
        float leftY = centerY + (float) (radius * Math.cos(Math.toRadians(60)));
        float rightX = centerX + (float) (radius * Math.sin(Math.toRadians(60)));
        float rightY = leftY;

        canvas.drawLine(topX, topY, leftX, leftY, axisPaint);
        canvas.drawLine(leftX, leftY, rightX, rightY, axisPaint);
        canvas.drawLine(rightX, rightY, topX, topY, axisPaint);

        float maxScore = Math.max(1f, Math.max(balloon, Math.max(cube, paddle)));
        float balloonRadius = radius * (balloon / maxScore);
        float cubeRadius = radius * (cube / maxScore);
        float paddleRadius = radius * (paddle / maxScore);

        float bX = centerX;
        float bY = centerY - balloonRadius;
        float cX = centerX - (float) (cubeRadius * Math.sin(Math.toRadians(60)));
        float cY = centerY + (float) (cubeRadius * Math.cos(Math.toRadians(60)));
        float pX = centerX + (float) (paddleRadius * Math.sin(Math.toRadians(60)));
        float pY = centerY + (float) (paddleRadius * Math.cos(Math.toRadians(60)));

        fillPaint.setAlpha(120);
        Path path = new Path();
        path.moveTo(bX, bY);
        path.lineTo(cX, cY);
        path.lineTo(pX, pY);
        path.close();
        canvas.drawPath(path, fillPaint);

        canvas.drawCircle(topX, topY, 6f, axisPaint);
        canvas.drawCircle(leftX, leftY, 6f, axisPaint);
        canvas.drawCircle(rightX, rightY, 6f, axisPaint);

        canvas.drawCircle(bX, bY, 8f, axisPaint);
        canvas.drawCircle(cX, cY, 8f, axisPaint);
        canvas.drawCircle(pX, pY, 8f, axisPaint);

        canvas.drawText(getResources().getString(R.string.label_balloon), topX, topY - 12f, textPaint);
        canvas.drawText(getResources().getString(R.string.label_cube), leftX - 24f, leftY + 32f, textPaint);
        canvas.drawText(getResources().getString(R.string.label_paddle), rightX + 24f, rightY + 32f, textPaint);
    }
}
