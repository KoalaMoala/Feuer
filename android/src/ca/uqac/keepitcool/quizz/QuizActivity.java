package ca.uqac.keepitcool.quizz;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.quizz.CountDownAnimation.CountDownListener;
import ca.uqac.keepitcool.quizz.scenario.Scenario;
import ca.uqac.keepitcool.quizz.scenario.ScenarioBuilder;

public class QuizActivity extends Activity implements CountDownListener {

	private TextView textView;
	private CountDownAnimation countDownAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		Scenario s = ScenarioBuilder.buildScenario(1);

		LinearLayout yesButton = (LinearLayout) findViewById(R.id.yes);
		yesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startCountDownAnimation("default");
			}
		});

		LinearLayout noButton = (LinearLayout) findViewById(R.id.no);
		noButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelCountDownAnimation();
			}
		});

		TextView question = (TextView) findViewById(R.id.question);
		Typeface quandoFont = Typeface.createFromAsset(getAssets(), "fonts/Quando.ttf");
		question.setTypeface(quandoFont);

		textView = (TextView) findViewById(R.id.textView);
		countDownAnimation = new CountDownAnimation(textView, getStartCount());
		countDownAnimation.setCountDownListener(this);
	}


	private void startCountDownAnimation(String animationStyle) {
		// Customizable animation
		Animation scaleAnimation = null;
		switch(animationStyle) {
			case "scale":
				scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				countDownAnimation.setAnimation(scaleAnimation);
				break;
			case "alpha":
				scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
				AnimationSet animationSet = new AnimationSet(false);
				animationSet.addAnimation(scaleAnimation);
				animationSet.addAnimation(alphaAnimation);
				countDownAnimation.setAnimation(animationSet);
				break;
			default:
				break;
		}

		// Customizable start count
		countDownAnimation.setStartCount(getStartCount());
		countDownAnimation.start();
	}

	private void cancelCountDownAnimation() {
		countDownAnimation.cancel();
	}

	private int getStartCount() {
		return 30;
	}

	@Override
	public void onCountDownEnd(CountDownAnimation animation) {
		Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
	}
}
