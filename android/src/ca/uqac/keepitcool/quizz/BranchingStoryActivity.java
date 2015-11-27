package ca.uqac.keepitcool.quizz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import ca.uqac.keepitcool.menu.Preferences;
import ca.uqac.keepitcool.quizz.scenario.Difficulty;
import mehdi.sakout.fancybuttons.FancyButton;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.menu.MainActivity;
import ca.uqac.keepitcool.quizz.scenario.Choice;
import ca.uqac.keepitcool.quizz.scenario.Scenario;
import ca.uqac.keepitcool.quizz.scenario.ScenarioBuilder;
import ca.uqac.keepitcool.quizz.scenario.Situation;
import ca.uqac.keepitcool.quizz.CountDownAnimation.CountDownListener;

public class BranchingStoryActivity extends Activity implements CountDownListener, OnPreparedListener, OnCompletionListener {

	private int currentSource;
	private LinearLayout endingContainer;
	private TextView countdownView, situationView;
	private FancyButton noButton, yesButton, confirmButton, restartButton;
	private CountDownAnimation countDownAnimation;
	private Difficulty difficulty;
	private Scenario scenario;
	private VideoView videoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		final Typeface quandoFont = Typeface.createFromAsset(getAssets(), "fonts/Quando.ttf");

		this.difficulty = Preferences.getDifficultySetting(getApplicationContext());
		this.situationView = (TextView) findViewById(R.id.question);
		this.countdownView = (TextView) findViewById(R.id.textView);
		this.noButton = (FancyButton) findViewById(R.id.no);
		this.yesButton = (FancyButton) findViewById(R.id.yes);
		this.restartButton = (FancyButton) findViewById(R.id.restart);
		this.confirmButton = (FancyButton) findViewById(R.id.confirm);
		this.endingContainer = (LinearLayout) findViewById(R.id.endingContainer);
		this.situationView.setTypeface(quandoFont);
		this.videoView = (VideoView)findViewById(R.id.videoView);

		this.videoView.setOnPreparedListener(this);
		this.videoView.setOnCompletionListener(this);

		loadScenario();

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

		this.restartButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadScenario();
			}
		});

		this.confirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
	}

	private Uri getUriFromAsset(int asset) {
		String path = "android.resource://" + getPackageName() + "/" + asset;
		return Uri.parse(path);
	}

	private void playVideo(int asset) {
		this.currentSource = asset;
		this.videoView.setVideoURI(this.getUriFromAsset(this.currentSource));
		this.videoView.start();
	}

	private void loadScenario() {
		this.scenario = ScenarioBuilder.buildFromFile("levels.json", getAssets());
		Situation s = this.scenario.getStartingSituation();
		initializeControls();
		this.updateTextFromSituation(s);
	}

	private void initializeControls() {
		playVideo(R.raw.embers_23);
		this.noButton.setVisibility(View.VISIBLE);
		this.yesButton.setVisibility(View.VISIBLE);
		this.endingContainer.setVisibility(View.GONE);
	}

	private void updateEndingControls(String failureCause) {
		switch (failureCause) {
			case "RAN_OUT_OF_TIME":
				playVideo(R.raw.out_of_time_15);
				break;
			case "FAILURE":
				playVideo(R.raw.firespread_06);
				break;
			default:
				playVideo(R.raw.clouds_13);
				break;
		}
		this.noButton.setVisibility(View.GONE);
		this.yesButton.setVisibility(View.GONE);
		this.endingContainer.setVisibility(View.VISIBLE);
	}

	private void updateTextFromSituation(Situation s) {
		this.situationView.setText(s.getDescription());

		Choice firstChoice = s.getFirstChoice();
		Choice secondChoice = s.getSecondChoice();
		if(null != firstChoice && null != secondChoice) {
			this.noButton.setText(firstChoice.getLabel());
			this.noButton.setIconResource(firstChoice.getIcon());
			this.noButton.setBackgroundColor(Color.parseColor(firstChoice.getDefaultColor()));
			this.noButton.setFocusBackgroundColor(Color.parseColor(firstChoice.getFocusColor()));

			this.yesButton.setText(secondChoice.getLabel());
			this.yesButton.setIconResource(secondChoice.getIcon());
			this.yesButton.setBackgroundColor(Color.parseColor(secondChoice.getDefaultColor()));
			this.yesButton.setFocusBackgroundColor(Color.parseColor(secondChoice.getFocusColor()));
		} else {
			updateEndingControls(s.getEndingType());
		}

		if(s.countdownRequired()) {
			this.countDownAnimation = new CountDownAnimation(this.countdownView, s.getDuration(this.difficulty));
			this.countDownAnimation.setCountDownListener(this);
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

	@Override
	public void onCompletion(MediaPlayer mp) {
		//TODO: figure out a better way to handle this
		if(this.currentSource == R.raw.firespread_06) {
			playVideo(R.raw.fire_08);
		} else {
			mp.setLooping(true);
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		//TODO: figure out a better way to handle this
		if(this.currentSource == R.raw.firespread_06) {
			mp.setLooping(true);
		} else {
			mp.setLooping(true);
		}
	}
}
