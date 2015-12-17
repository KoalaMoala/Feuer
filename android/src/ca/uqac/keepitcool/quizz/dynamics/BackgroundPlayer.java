package ca.uqac.keepitcool.quizz.dynamics;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;

import java.util.Random;

import ca.uqac.keepitcool.R;

public class BackgroundPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private final Resources resources;
    private final VideoView videoView;
    private final String packageName;
    private boolean failureLoop;

    public BackgroundPlayer(VideoView videoView, String packageName, Context applicationContext) {
        this.videoView = videoView;
        this.packageName = packageName;
        this.resources = applicationContext.getResources();
        this.videoView.setOnPreparedListener(this);
        this.videoView.setOnCompletionListener(this);
        this.failureLoop = false;
    }

    // ============================================================
    //                         LOADING VIDEO
    // ============================================================

    public void playVideo(String type) {
        int assetID = this.getRandomVideoFromType(type);
        this.loadVideo(assetID);
    }

    private Uri getUriFromAsset(int asset) {
        String path = "android.resource://" + this.packageName + "/" + asset;
        return Uri.parse(path);
    }

    private void loadVideo(int asset) {
        this.videoView.stopPlayback();
        this.videoView.setVideoURI(this.getUriFromAsset(asset));
        this.videoView.start();
    }

    // ============================================================
    //                  RANDOM VIDEO FROM CATEGORY
    // ============================================================

    private int getRandomVideoFromType(String type) {
        TypedArray videos = null;
        switch(type) {
            case "SUCCESS":
                videos = resources.obtainTypedArray(R.array.VIDEO_SUCCESS);
                break;
            case "FAILURE":
                this.failureLoop = true;
                videos = resources.obtainTypedArray(R.array.VIDEO_FAILURE);
                break;
            case "FAILURE_LOOP":
                this.failureLoop = false;
                videos = resources.obtainTypedArray(R.array.VIDEO_FAILURE_LOOP);
                break;
            case "RAN_OUT_OF_TIME":
                videos = resources.obtainTypedArray(R.array.VIDEO_RAN_OUT_OF_TIME);
                break;
            default:
                videos = resources.obtainTypedArray(R.array.VIDEO_MAIN);
                break;
        }

        final Random rand = new Random();
        final int rndInt = rand.nextInt(videos.length());
        return videos.getResourceId(rndInt, 0);
    }

    // ============================================================
    //                     MEDIAPLAYER OVERRIDES
    // ============================================================

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(failureLoop) {
            playVideo("FAILURE_LOOP");
        } else {
            mp.setLooping(true);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setLooping(true);
    }
}
