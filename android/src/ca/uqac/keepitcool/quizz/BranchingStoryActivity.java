package ca.uqac.keepitcool.quizz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

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

public class BranchingStoryActivity extends Activity {

	private int levelId;
	private long startTime;
	private Scenario scenario;
	private Difficulty difficulty;
	private TextView situationView;
	private LinearLayout endingContainer;
	private BackgroundPlayer backgroundPlayer;
	private AnimatedCountdown animatedCountdown;
	private DynamicButton firstButton, secondButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_branching_story);

		this.situationView = (TextView) findViewById(R.id.question);
		this.endingContainer = (LinearLayout) findViewById(R.id.endingContainer);

		this.difficulty = Preferences.getDifficultySetting(getApplicationContext());
		this.backgroundPlayer = new BackgroundPlayer((VideoView)findViewById(R.id.videoView), getPackageName(), getApplicationContext());
		this.animatedCountdown = new AnimatedCountdown((TextView) findViewById(R.id.textView), this);
		this.firstButton = new DynamicButton((FancyButton) findViewById(R.id.firstButton), UserDecision.FIRST, this);
		this.secondButton = new DynamicButton((FancyButton) findViewById(R.id.secondButton), UserDecision.SECOND, this);
		this.situationView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quando.ttf"));

		//Getting levelId from Menu fragment
		Bundle b = getIntent().getExtras();
		this.levelId = b.getInt("levelId");
		loadScenario(levelId);

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

	// ============================================================
	//                        INITIALIZATION
	// ============================================================

	private void loadScenario(int levelId) {
		this.scenario = ScenarioBuilder.buildFromFile("level" + levelId + ".json", getAssets());
		Situation s = this.scenario.getStartingSituation();
		initializeControls();
		this.updateIntefaceFromSituation(s);
		this.startTime =  elapsedRealtime();
	}

	private void initializeControls() {
		this.backgroundPlayer.playVideo("MAIN");
		this.firstButton.setVisibility(View.VISIBLE);
		this.secondButton.setVisibility(View.VISIBLE);
		this.endingContainer.setVisibility(View.GONE);
	}

	// ============================================================
	//                       DYNAMIC UPDATES
	// ============================================================

	public void handleUserChoice(UserDecision userDecision) {
		Situation s = scenario.getNextSituation(userDecision);
		updateIntefaceFromSituation(s);
	}

	private void updateIntefaceFromSituation(Situation s) {
		this.animatedCountdown.cancelCountdown();
		this.situationView.setText(s.getDescription());
		int choicesCount = s.getChoicesCount();

		if(0 < choicesCount) {
			List<Choice> choices = s.getChoicesInRandomOrder();
			List<FancyColor> colors = FancyColor.getRandomColors(choicesCount);
			this.firstButton.update(choices.get(0), colors.get(0));
			this.secondButton.update(choices.get(1), colors.get(1));
		} else {
			updateEndingControls(s.getEndingType());
		}

		if(s.countdownRequired()) {
			this.animatedCountdown.startCountdown("default", s.getDuration(this.difficulty));
		}
	}

	void updateEndingControls(String failureCause) {
		switch (failureCause) {
			case "RAN_OUT_OF_TIME":
				this.situationView.setText(getResources().getString(R.string.time_run_out));
				this.backgroundPlayer.playVideo("RAN_OUT_OF_TIME");
				break;
			case "FAILURE":
				this.backgroundPlayer.playVideo("FAILURE");
				break;
			default:
				float score = ( (float) (elapsedRealtime() - this.startTime) ) /  (float) 1000;
				Preferences.updateLevelScore(levelId, score, getApplicationContext());
				Toast.makeText(getApplicationContext(), "score : " + score , Toast.LENGTH_SHORT).show();
				this.backgroundPlayer.playVideo("SUCCESS");
				break;
		}
		this.firstButton.setVisibility(View.GONE);
		this.secondButton.setVisibility(View.GONE);
		this.endingContainer.setVisibility(View.VISIBLE);
	}
}