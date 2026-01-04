package com.Ntut.GameMenu;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class TriangleChartView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float s1 = 0, s2 = 0, s3 = 0; // 分數 (0-100)

    public TriangleChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScores(float balloon, float cube, float paddle) {
        // 限制分數範圍在 0-100 之間
        this.s1 = Math.min(100, Math.max(0, balloon));
        this.s2 = Math.min(100, Math.max(0, cube));
        this.s3 = Math.min(100, Math.max(0, paddle));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2 + 30; // 稍微下移給標題留空間
        float maxRadius = getWidth() * 0.35f;

        // --- 1. 繪製背景參考網格 (三個圈) ---
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);
        paint.setColor(Color.LTGRAY);

        for (int i = 1; i <= 4; i++) {
            float r = maxRadius * i / 4;
            drawTriangle(canvas, centerX, centerY, r, false);
        }

        // --- 2. 繪製中軸線 ---
        for (int i = 0; i < 3; i++) {
            float angle = (float) Math.toRadians(270 + i * 120);
            float startX = centerX;
            float startY = centerY;
            float endX = (float) (centerX + maxRadius * Math.cos(angle));
            float endY = (float) (centerY + maxRadius * Math.sin(angle));
            canvas.drawLine(startX, startY, endX, endY, paint);
        }

        // --- 3. 繪製分數面積圖 (半透明彩色) ---
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#804CAF50")); // 綠色且 50% 透明度

        Path scorePath = new Path();
        for (int i = 0; i < 3; i++) {
            float score = (i == 0) ? s1 : (i == 1 ? s2 : s3);
            float angle = (float) Math.toRadians(270 + i * 120);
            float r = maxRadius * score / 100f;
            float px = (float) (centerX + r * Math.cos(angle));
            float py = (float) (centerY + r * Math.sin(angle));
            if (i == 0) scorePath.moveTo(px, py);
            else scorePath.lineTo(px, py);
        }
        scorePath.close();
        canvas.drawPath(scorePath, paint);

        // --- 4. 繪製頂點文字標籤 ---
        paint.setColor(Color.BLACK);
        paint.setTextSize(40f);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextAlign(Paint.Align.CENTER);

        String[] labels = {"氣球", "方塊", "球拍"};
        for (int i = 0; i < 3; i++) {
            float angle = (float) Math.toRadians(270 + i * 120);
            // 文字位置比最大半徑再往外一點
            float tx = (float) (centerX + (maxRadius + 60) * Math.cos(angle));
            float ty = (float) (centerY + (maxRadius + 60) * Math.sin(angle));
            canvas.drawText(labels[i], tx, ty, paint);
        }
    }

    // 輔助方法：繪製正三角形
    private void drawTriangle(Canvas canvas, int cx, int cy, float r, boolean fill) {
        Path path = new Path();
        for (int i = 0; i < 3; i++) {
            float angle = (float) Math.toRadians(270 + i * 120);
            float px = (float) (cx + r * Math.cos(angle));
            float py = (float) (cy + r * Math.sin(angle));
            if (i == 0) path.moveTo(px, py);
            else path.lineTo(px, py);
        }
        path.close();
        canvas.drawPath(path, paint);
    }
}
