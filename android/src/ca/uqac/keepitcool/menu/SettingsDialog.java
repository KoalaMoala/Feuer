package ca.uqac.keepitcool.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.quizz.scenario.Difficulty;

public class SettingsDialog extends FragmentDialog implements OnItemSelectedListener {

    boolean soundIsOn;
    LinearLayout toggleSound, reset, difficultyContainer;
    ImageView toggleSoundImage, difficultyImage;
    TextView toggleSoundLabel, toggleSoundDescription;
    Spinner difficultySpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Context context = getContextFromActivity();
        this.revealView = (CircularRevealView) view.findViewById(R.id.reveal);
        this.reset = (LinearLayout) view.findViewById(R.id.reset);
        this.frame = (FrameLayout) view.findViewById(R.id.frame);
        this.toggleSound = (LinearLayout) view.findViewById(R.id.toggleSound);
        this.toggleSoundImage = (ImageView) view.findViewById(R.id.toggleSoundImage);
        this.toggleSoundLabel = (TextView) view.findViewById(R.id.toggleSoundLabel);
        this.toggleSoundDescription = (TextView) view.findViewById(R.id.toggleSoundDescription);
        this.difficultyContainer = (LinearLayout) view.findViewById(R.id.difficulty);
        this.difficultySpinner = (Spinner) view.findViewById(R.id.difficulty_spinner);
        this.difficultyImage = (ImageView) view.findViewById(R.id.difficultyImage);
        this.soundIsOn = Preferences.getSoundSetting(context);

        setupSoundToggle();
        setupDifficultySpinner(context);

        this.toggleSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSound();
            }
        });

        this.difficultyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficultySpinner.performClick();
            }
        });

        this.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    private void toggleSound() {
        soundIsOn = !soundIsOn;
        if(soundIsOn) {
            Preferences.updateSoundSetting(true, getActivity().getApplicationContext());
        } else {
            Preferences.updateSoundSetting(false, getActivity().getApplicationContext());
        }
        setupSoundToggle();
    }

    private void setupSoundToggle() {
        if(soundIsOn) {
            toggleSoundImage.setImageResource(R.drawable.sound_on);
            toggleSoundDescription.setText(getResources().getString(R.string.menu_settings_sound_on));
        } else {
            toggleSoundImage.setImageResource(R.drawable.sound_off);
            toggleSoundDescription.setText(getResources().getString(R.string.menu_settings_sound_off));
        }
    }

    private void setupDifficultySpinner(Context context) {
        String difficulty = Preferences.getDifficultySettingAsString(context);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.difficulty_array, R.layout.spinner_custom);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
        this.difficultySpinner.setAdapter(adapter);

        int spinnerPosition = difficulty.equals("MEDIUM") ? Difficulty.MEDIUM.ordinal() : difficulty.equals("HARD") ? Difficulty.HARD.ordinal() : Difficulty.EASY.ordinal();
        this.difficultySpinner.setSelection(spinnerPosition);
        this.difficultySpinner.setOnItemSelectedListener(this);

        toggleDifficulty(difficulty, context);
    }

    private void toggleDifficulty(String difficulty, Context context) {
        switch (difficulty) {
            case "EASY":
                difficultyImage.setImageResource(R.drawable.difficulty_easy);
                Preferences.updateDifficultySetting(Difficulty.EASY, context);
                break;
            case "HARD":
                difficultyImage.setImageResource(R.drawable.difficulty_hard);
                Preferences.updateDifficultySetting(Difficulty.HARD, context);
                break;
            default:
                difficultyImage.setImageResource(R.drawable.difficulty_medium);
                Preferences.updateDifficultySetting(Difficulty.MEDIUM, context);
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selected = getResources().getStringArray(R.array.difficulty_array_values)[parent.getSelectedItemPosition()];
        Preferences.updateDifficultySetting(Difficulty.valueOf(selected.toUpperCase()), getContextFromActivity());
        toggleDifficulty(selected, getContextFromActivity());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Do nothing if nothing is selected
    }

    private Context getContextFromActivity() {
        return this.getActivity().getApplicationContext();
    }
}
