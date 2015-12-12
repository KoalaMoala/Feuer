package ca.uqac.keepitcool.quizz;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import ca.uqac.keepitcool.quizz.CountDownAnimation.CountDownListener;

public class AnimatedCountdown implements CountDownListener {

    private final TextView countdownView;
    private final BranchingStoryActivity parent;
    private CountDownAnimation countDownAnimation;

    public AnimatedCountdown(TextView countdownView, BranchingStoryActivity parent) {
        this.countdownView = countdownView;
        this.parent = parent;
    }

    public void startCountdown(String style, int duration) {
        this.countDownAnimation = new CountDownAnimation(this.countdownView, duration);
        this.countDownAnimation.setCountDownListener(this);
        startAnimation(style);
    }

    private void startAnimation(String style) {
        Animation scaleAnimation = null;
        switch(style) {
            case "scale":
                scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                this.countDownAnimation.setAnimation(scaleAnimation);
                break;
            case "alpha":
                scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                AnimationSet animationSet = new AnimationSet(false);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(alphaAnimation);
                this.countDownAnimation.setAnimation(animationSet);
                break;
            default:
                break;
        }
        this.countDownAnimation.start();
    }

    public void cancelCountdown() {
        this.countDownAnimation.cancel();
    }

    @Override
    public void onCountDownEnd(CountDownAnimation animation) {
        this.parent.updateEndingControls("RAN_OUT_OF_TIME");
    }
}
