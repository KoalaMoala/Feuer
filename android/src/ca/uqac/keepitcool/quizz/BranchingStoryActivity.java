package ca.uqac.keepitcool.quizz;

import static android.os.SystemClock.elapsedRealtime;
import android.app.Activity;
import android.content.DialogInterface;
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

import mehdi.sakout.fancybuttons.FancyButton;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.quizz.dynamics.AnimatedCountdown;
import ca.uqac.keepitcool.quizz.dynamics.BackgroundPlayer;
import ca.uqac.keepitcool.quizz.dynamics.DynamicButton;
import ca.uqac.keepitcool.quizz.dynamics.SoundPlayer;
import ca.uqac.keepitcool.quizz.utils.FancyColor;
import ca.uqac.keepitcool.quizz.utils.UserDecision;
import ca.uqac.keepitcool.menu.MainActivity;
import ca.uqac.keepitcool.quizz.scenario.Choice;
import ca.uqac.keepitcool.quizz.utils.Difficulty;
import ca.uqac.keepitcool.quizz.scenario.Scenario;
import ca.uqac.keepitcool.quizz.scenario.ScenarioBuilder;
import ca.uqac.keepitcool.quizz.scenario.Situation;

public class BranchingStoryActivity extends Activity implements DialogInterface.OnDismissListener {

	private int levelId;
	private long startTime;
    private boolean validScore;
	private Scenario scenario;
	private Difficulty difficulty;
	private TextView situationView;
	private BackgroundPlayer backgroundPlayer;
	private AnimatedCountdown animatedCountdown;
	private List<DynamicButton> dynamicButtons;
	private LinearLayout endingContainer, firstRow, secondRow;

	private boolean soundActivated;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_branching_story);

		this.situationView = (TextView) findViewById(R.id.question);
		this.endingContainer = (LinearLayout) findViewById(R.id.endingContainer);
		this.firstRow = (LinearLayout) findViewById(R.id.firstRow);
		this.secondRow = (LinearLayout) findViewById(R.id.secondRow);

		this.soundActivated = Preferences.getSoundSetting(getBaseContext());
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

	@Override
	public void onDismiss(final DialogInterface dialog) {
		this.animatedCountdown.cancelCountdown();
		SoundPlayer.reset();
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
        this.validScore = true;
	}

	// ============================================================
	//                       DYNAMIC UPDATES
	// ============================================================

	public void handleUserChoice(UserDecision userDecision) {
		SoundPlayer.playSound(R.raw.button, getBaseContext());
		Situation s = scenario.getNextSituation(userDecision);
		updateIntefaceFromSituation(s);
	}

	private void updateIntefaceFromSituation(Situation s) {
		this.animatedCountdown.cancelCountdown();
		this.situationView.setText(Html.fromHtml(s.getDescription()));
        if(s.getDescription().contains("#FF0000")){ validScore = false; }
		int choicesCount = s.getChoicesCount();

		if(0 < choicesCount) {
			List<Choice> choices = s.getChoicesInRandomOrder();
			List<FancyColor> colors = FancyColor.getRandomColors(choicesCount);
			for(int count=0; count < choicesCount; count++) {
				DynamicButton current = this.dynamicButtons.get(count);
				current.update(choices.get(count), colors.get(count), choicesCount);
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

	public void updateEndingControls(String failureCause) {
		switch (failureCause) {
			case "RAN_OUT_OF_TIME":
				this.situationView.setText(getResources().getString(R.string.time_run_out));
				this.backgroundPlayer.playVideo("RAN_OUT_OF_TIME");
				break;
			case "FAILURE":
				this.backgroundPlayer.playVideo("FAILURE");
				if(soundActivated) {
					SoundPlayer.playSound(R.raw.ending_fire, getBaseContext());
				}
				break;
			default:
				if(validScore) {
					float score = ( (float) (elapsedRealtime() - this.startTime) ) /  (float) 1000;
					Preferences.updateLevelScore(levelId, score, getApplicationContext());
					Toast.makeText(getApplicationContext(), "Score : " + score , Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(getApplicationContext(), "Vous avez pris de mauvaises décisions, vous n'aurez pas de score cette fois-ci !", Toast.LENGTH_LONG).show();
				}
				this.backgroundPlayer.playVideo("SUCCESS");
				if (soundActivated) {
					SoundPlayer.playSound(R.raw.ending_success, getBaseContext());
				}
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