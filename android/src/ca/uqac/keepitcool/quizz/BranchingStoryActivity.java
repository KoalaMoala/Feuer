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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import static android.os.SystemClock.elapsedRealtime;

import ca.uqac.keepitcool.quizz.utils.FancyColor;
import ca.uqac.keepitcool.quizz.utils.UserDecision;
import mehdi.sakout.fancybuttons.FancyButton;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.menu.MainActivity;
import ca.uqac.keepitcool.quizz.scenario.Choice;
import ca.uqac.keepitcool.quizz.utils.Difficulty;
import ca.uqac.keepitcool.quizz.scenario.Scenario;
import ca.uqac.keepitcool.quizz.scenario.ScenarioBuilder;
import ca.uqac.keepitcool.quizz.scenario.Situation;
import ca.uqac.keepitcool.quizz.CountDownAnimation.CountDownListener;

public class BranchingStoryActivity extends Activity implements CountDownListener {


	private int levelId;
	private long startTime;
	private LinearLayout endingContainer;
	private TextView countdownView, situationView;
	private FancyButton noButton, yesButton;
	private CountDownAnimation countDownAnimation;
	private Difficulty difficulty;
	private Scenario scenario;
	private BackgroundPlayer backgroundPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		final Typeface quandoFont = Typeface.createFromAsset(getAssets(), "fonts/Quando.ttf");

		this.difficulty = Preferences.getDifficultySetting(getApplicationContext());
		this.backgroundPlayer = new BackgroundPlayer((VideoView)findViewById(R.id.videoView), getPackageName(), getApplicationContext());
		this.situationView = (TextView) findViewById(R.id.question);
		this.countdownView = (TextView) findViewById(R.id.textView);
		this.noButton = (FancyButton) findViewById(R.id.no);
		this.yesButton = (FancyButton) findViewById(R.id.yes);
		this.endingContainer = (LinearLayout) findViewById(R.id.endingContainer);
		this.situationView.setTypeface(quandoFont);

		//Getting levelId from Menu fragment
		Bundle b = getIntent().getExtras();
		this.levelId = b.getInt("levelId");
		loadScenario(levelId);

		this.noButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				triggerNextScreen(scenario.getNextSituation(UserDecision.FIRST));
			}
		});

		this.yesButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				triggerNextScreen(scenario.getNextSituation(UserDecision.SECOND));
			}
		});

		final FancyButton restartButton = (FancyButton) findViewById(R.id.restart);
		restartButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadScenario(levelId);
			}
		});

		final FancyButton confirmButton = (FancyButton) findViewById(R.id.confirm);
		confirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
	}

	private void loadScenario(int levelId) {
		this.scenario = ScenarioBuilder.buildFromFile("level" + levelId + ".json", getAssets());
		Situation s = this.scenario.getStartingSituation();
		initializeControls();
		this.updateTextFromSituation(s);
		this.startTime =  elapsedRealtime();
	}

	private void initializeControls() {
		this.backgroundPlayer.playVideo("MAIN");
		this.noButton.setVisibility(View.VISIBLE);
		this.yesButton.setVisibility(View.VISIBLE);
		this.endingContainer.setVisibility(View.GONE);
	}

	private void updateEndingControls(String failureCause) {
		switch (failureCause) {
			case "RAN_OUT_OF_TIME":
				this.backgroundPlayer.playVideo("RAN_OUT_OF_TIME");
				break;
			case "":
				this.backgroundPlayer.playVideo("FAILURE");
				break;
			default:
				float score = ( (float) (elapsedRealtime() - this.startTime) ) /  (float) 1000;
				Preferences.updateLevelScore(levelId, score, getApplicationContext());
				Toast.makeText(getApplicationContext(), "score : " + score , Toast.LENGTH_SHORT).show();
				this.backgroundPlayer.playVideo("SUCCESS");
				break;
		}
		this.noButton.setVisibility(View.GONE);
		this.yesButton.setVisibility(View.GONE);
		this.endingContainer.setVisibility(View.VISIBLE);
	}

	private void updateTextFromSituation(Situation s) {
		this.situationView.setText(s.getDescription());

		FancyColor[] colors = FancyColor.getRandomColors(2);
		if(s.hasChoices()) {
			this.updateChoiceButton(this.noButton, s.getFirstChoice(), colors[0]);
			this.updateChoiceButton(this.yesButton, s.getSecondChoice(), colors[1]);
		} else {
			updateEndingControls(s.getEndingType());
		}

		if(s.countdownRequired()) {
			this.countDownAnimation = new CountDownAnimation(this.countdownView, s.getDuration(this.difficulty));
			this.countDownAnimation.setCountDownListener(this);
			startCountDownAnimation("default");
		}
	}

	private void updateChoiceButton(FancyButton control, Choice choice, FancyColor color) {
		control.setText(choice.getLabel());
		control.setIconResource(choice.getIcon());
		control.setBackgroundColor(Color.parseColor(color.getDefaultColor()));
		control.setFocusBackgroundColor(Color.parseColor(color.getFocusColor()));
	}

	private void triggerNextScreen(Situation s) {
		cancelCountDownAnimation();
		updateTextFromSituation(s);
		if(s.countdownRequired()) {
			startCountDownAnimation("default");
		}
	}

	private void startCountDownAnimation(String animationStyle) {
		Animation scaleAnimation = null;
		switch(animationStyle) {
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

	private void cancelCountDownAnimation() {
		this.countDownAnimation.cancel();
	}

	@Override
	public void onCountDownEnd(CountDownAnimation animation) {
		this.situationView.setText(getResources().getString(R.string.time_run_out));
		updateEndingControls("RAN_OUT_OF_TIME");
	}
}
