package ca.uqac.keepitcool.quizz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import mehdi.sakout.fancybuttons.FancyButton;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.menu.MainActivity;
import ca.uqac.keepitcool.quizz.scenario.Choice;
import ca.uqac.keepitcool.quizz.scenario.Scenario;
import ca.uqac.keepitcool.quizz.scenario.ScenarioBuilder;
import ca.uqac.keepitcool.quizz.scenario.Situation;
import ca.uqac.keepitcool.quizz.CountDownAnimation.CountDownListener;

public class BranchingStoryActivity extends Activity implements CountDownListener {

	private TextView countdownView;
	private TextView situationView;
	private FancyButton noButton;
	private FancyButton yesButton;
	private FancyButton confirmButton;
	private CountDownAnimation countDownAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		final Typeface quandoFont = Typeface.createFromAsset(getAssets(), "fonts/Quando.ttf");
		//final Scenario scenario = ScenarioBuilder.buildScenario(1);
		final Scenario scenario = ScenarioBuilder.buildFromFile("levels.json", getAssets());

		situationView = (TextView) findViewById(R.id.question);
		countdownView = (TextView) findViewById(R.id.textView);
		noButton = (FancyButton) findViewById(R.id.no);
		yesButton = (FancyButton) findViewById(R.id.yes);
		confirmButton = (FancyButton) findViewById(R.id.confirm);

		Situation s = scenario.getStartingSituation();
		situationView.setTypeface(quandoFont);
		this.updateTextFromSituation(s);

		noButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				triggerNextScreen(scenario.getNextSituation(UserDecision.FIRST));
			}
		});

		yesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				triggerNextScreen(scenario.getNextSituation(UserDecision.SECOND));
			}
		});

		confirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
	}

	private void updateTextFromSituation(Situation s) {
		situationView.setText(s.getDescription());

		Choice firstChoice = s.getFirstChoice();
		Choice secondChoice = s.getSecondChoice();
		if(null != firstChoice && null != secondChoice) {
			noButton.setText(firstChoice.getLabel());
			noButton.setIconResource(firstChoice.getIcon());
			noButton.setBackgroundColor(Color.parseColor(firstChoice.getDefaultColor()));
			noButton.setFocusBackgroundColor(Color.parseColor(firstChoice.getFocusColor()));

			yesButton.setText(secondChoice.getLabel());
			yesButton.setIconResource(secondChoice.getIcon());
			yesButton.setBackgroundColor(Color.parseColor(secondChoice.getDefaultColor()));
			yesButton.setFocusBackgroundColor(Color.parseColor(secondChoice.getFocusColor()));
		} else {
			noButton.setVisibility(View.GONE);
			yesButton.setVisibility(View.GONE);
			confirmButton.setVisibility(View.VISIBLE);
		}

		if(s.countdownRequired()) {
			countDownAnimation = new CountDownAnimation(countdownView, s.getDuration());
			countDownAnimation.setCountDownListener(this);
			startCountDownAnimation("default");
		}
	}

	private void triggerNextScreen(Situation s) {
		cancelCountDownAnimation();
		updateTextFromSituation(s);
		if(s.countdownRequired()) {
			startCountDownAnimation("default");
		}
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
		//countDownAnimation.setStartCount(getStartCount());
		countDownAnimation.start();
	}

	private void cancelCountDownAnimation() {
		countDownAnimation.cancel();
	}

	@Override
	public void onCountDownEnd(CountDownAnimation animation) {
		//Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
	}
}
