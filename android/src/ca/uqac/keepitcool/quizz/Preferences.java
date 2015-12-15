package ca.uqac.keepitcool.quizz;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ca.uqac.keepitcool.quizz.utils.Difficulty;

public class Preferences {

    private final static String difficultyKey = "difficulty";
    private final static String soundKey = "sound";
    private final static String nameKey = "name";
    private final static String scoreKey = "scoreLevel";

    public static void updateDifficultySetting(Difficulty difficulty, Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(difficultyKey, difficulty.name()).apply();
    }

    public static Difficulty getDifficultySetting(Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String res = prefs.getString(difficultyKey, "MEDIUM");
        return Difficulty.valueOf(res);
    }

    public static String getDifficultySettingAsString(Context context) {
        Difficulty difficulty = getDifficultySetting(context);
        switch (difficulty) {
            case EASY:
                return "EASY";
            case HARD:
                return "HARD";
            default:
                return "MEDIUM";
        }
    }

    public static void updateSoundSetting(boolean soundActivated, Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean(soundKey, soundActivated).apply();
    }

    public static boolean getSoundSetting(Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(soundKey, true);
    }

    public static void updateLevelScore(int levelID, float score, Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String levelKey = scoreKey + levelID;
        if(score < getLevelScore(levelID, context)) {
            prefs.edit().putFloat(levelKey, score).apply();
        }
    }

    public static float getLevelScore(int levelID, Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String levelKey = scoreKey + levelID;
        return prefs.getFloat(levelKey, 0);
    }

    public static void updateNameSetting(String alias, Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(nameKey, alias).apply();
    }

    public static String getNameSetting(Context context) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(nameKey, "DefaultUsername");
    }
}
