package com.Ntut.GameMenu;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public final class ScoreBridge {

    private static final String PREF_NAME = "scores";
    private static final String KEY_BALLOON = "BestScore_Balloon";
    private static final String KEY_CUBE = "BestScore_Cube";
    private static final String KEY_PADDLE = "BestScore_Paddle";

    private ScoreBridge() {
    }

    /**
     * Called from Unity via AndroidJavaClass to push latest scores into SharedPreferences.
     */
    public static void updateBestScores(int balloon, int cube, int paddle) {
        Context context = getAppContext();
        if (context == null) {
            Log.w("ScoreBridge", "updateBestScores: no application context yet, skip save");
            return;
        }
        Log.d("ScoreBridge", "updateBestScores -> balloon=" + balloon + " cube=" + cube + " paddle=" + paddle);
        SharedPreferences prefs = getPrefs(context);
        prefs.edit()
                .putInt(KEY_BALLOON, balloon)
                .putInt(KEY_CUBE, cube)
                .putInt(KEY_PADDLE, paddle)
                .apply();
    }

    public static int getBestScore(Context context, String key, int def) {
        return getPrefs(context).getInt(key, def);
    }

    public static int getBestBalloon(Context context) {
        return getPrefs(context).getInt(KEY_BALLOON, 0);
    }

    public static int getBestCube(Context context) {
        return getPrefs(context).getInt(KEY_CUBE, 0);
    }

    public static int getBestPaddle(Context context) {
        return getPrefs(context).getInt(KEY_PADDLE, 0);
    }

    public static ScoreData getScores(Context context) {
        return new ScoreData(
                getBestBalloon(context),
                getBestCube(context),
                getBestPaddle(context)
        );
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private static Context getAppContext() {
        try {
            Application app = (Application) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null);
            return app != null ? app.getApplicationContext() : null;
        } catch (Exception ignore) {
            return null;
        }
    }

    public static final class ScoreData {
        public final int balloon;
        public final int cube;
        public final int paddle;

        public ScoreData(int balloon, int cube, int paddle) {
            this.balloon = balloon;
            this.cube = cube;
            this.paddle = paddle;
        }
    }
}
