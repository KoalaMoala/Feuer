package ca.uqac.keepitcool.quizz.dynamics;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class SoundPlayer {

    private static MediaPlayer mp;

    public static void playSound(int asset, Context context) {
        mp = MediaPlayer.create(context, asset);

        mp.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp = null;
            }
        });
        mp.start();
    }

    public static void reset() {
        mp.reset();
        mp.release();
        mp = null;
    }
}
