package ca.uqac.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ca.uqac.myfirstgame.R;

public class SettingsDialog extends FragmentDialog {

    boolean soundActivated;
    LinearLayout toggleSound, reset;
    ImageView toggleSoundImage;
    TextView toggleSoundLabel;

    public SettingsDialog() {
        this.soundActivated = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        revealView = (CircularRevealView) view.findViewById(R.id.reveal);
        reset = (LinearLayout) view.findViewById(R.id.reset);
        frame = (FrameLayout) view.findViewById(R.id.frame);
        toggleSound = (LinearLayout) view.findViewById(R.id.toggleSound);
        toggleSoundImage = (ImageView) view.findViewById(R.id.toggleSoundImage);
        toggleSoundLabel = (TextView) view.findViewById(R.id.toggleSoundLabel);

        toggleSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label = null;
                if(soundActivated) {
                    toggleSoundImage.setImageResource(R.drawable.sound_off);
                    toggleSoundLabel.setText(getResources().getString(R.string.menu_settings_toggle_sound_on));
                } else {
                    toggleSoundImage.setImageResource(R.drawable.sound_on);
                    toggleSoundLabel.setText(getResources().getString(R.string.menu_settings_toggle_sound_off));
                }
                soundActivated = !soundActivated;
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }
}
