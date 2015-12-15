package ca.uqac.keepitcool.quizz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
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
	private BackgroundPlayer backgroundPlayer;
	private AnimatedCountdown animatedCountdown;
	private List<DynamicButton> dynamicButtons;
	private LinearLayout endingContainer, firstRow, secondRow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_branching_story);

		this.situationView = (TextView) findViewById(R.id.question);
		this.endingContainer = (LinearLayout) findViewById(R.id.endingContainer);
		this.firstRow = (LinearLayout) findViewById(R.id.firstRow);
		this.secondRow = (LinearLayout) findViewById(R.id.secondRow);

		this.difficulty = Preferences.getDifficultySetting(getApplicationContext());
		this.backgroundPlayer = new BackgroundPlayer((VideoView)findViewById(R.id.videoView), getPackageName(), getApplicationContext());
		this.animatedCountdown = new AnimatedCountdown((TextView) findViewById(R.id.textView), this);

		this.dynamicButtons = new ArrayList<DynamicButton>();
		dynamicButtons.add(new DynamicButton((FancyButton) findViewById(R.id.firstButton), this));
		dynamicButtons.add(new DynamicButton((FancyButton) findViewById(R.id.secondButton), this));
		dynamicButtons.add(new DynamicButton((FancyButton) findViewById(R.id.thirdButton), this));
		dynamicButtons.add(new DynamicButton((FancyButton) findViewById(R.id.fourthButton), this));
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
		this.backgroundPlayer.playVideo("MAIN");
		this.displayEndingContainer(false);
		this.updateIntefaceFromSituation(s);
		this.startTime =  elapsedRealtime();
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
		this.situationView.setText(Html.fromHtml(s.getDescription()));
		int choicesCount = s.getChoicesCount();

		if(0 < choicesCount) {
			List<Choice> choices = s.getChoicesInRandomOrder();
			List<FancyColor> colors = FancyColor.getRandomColors(choicesCount);
			for(int count=0; count < choicesCount; count++) {
				DynamicButton current = this.dynamicButtons.get(count);
				current.update(choices.get(count), colors.get(count));
				current.setVisibility(View.VISIBLE);
			}
			for(int undefinedChoices=choicesCount; undefinedChoices < 4; undefinedChoices++) {
				this.dynamicButtons.get(undefinedChoices).setVisibility(View.GONE);
			}
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
		this.displayEndingContainer(true);
	}

	private void displayEndingContainer(boolean shouldBeDisplayed) {
		if(shouldBeDisplayed) {
			this.endingContainer.setVisibility(View.VISIBLE);
			this.firstRow.setVisibility(View.GONE);
			this.secondRow.setVisibility(View.GONE);
		} else {
			this.endingContainer.setVisibility(View.GONE);
			this.firstRow.setVisibility(View.VISIBLE);
			this.secondRow.setVisibility(View.VISIBLE);
		}
	}
}