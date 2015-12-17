package ca.uqac.keepitcool.quizz.dynamics;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.LinearLayout;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.quizz.BranchingStoryActivity;
import ca.uqac.keepitcool.quizz.Preferences;
import ca.uqac.keepitcool.quizz.scenario.Choice;
import ca.uqac.keepitcool.quizz.utils.FancyColor;
import ca.uqac.keepitcool.quizz.utils.UserDecision;
import mehdi.sakout.fancybuttons.FancyButton;

public class DynamicButton implements View.OnClickListener {
    private final BranchingStoryActivity parent;
    private final FancyButton control;
    private UserDecision userDecision;

    public DynamicButton(FancyButton control, BranchingStoryActivity parent) {
        this.control = control;
        this.parent = parent;
        this.control.setOnClickListener(this);
    }

    // ============================================================
    //                         DYNAMIC UPDATES
    // ============================================================

    public void update(Choice choice, FancyColor color, int choiceCount) {
        this.userDecision = choice.getUserDecision();
        this.control.setText(choice.getLabel());
        this.control.setIconResource(choice.getIcon());
        this.control.setBackgroundColor(Color.parseColor(color.getDefaultColor()));
        this.control.setFocusBackgroundColor(Color.parseColor(color.getFocusColor()));
        this.setWeight(choiceCount);
    }

    public void setVisibility(int visibility) {
        this.control.setVisibility(visibility);
    }

    private void setWeight(int choiceCount) {
        float weight = (float) 1/choiceCount;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                weight
        );
        control.setLayoutParams(params);
    }

    // ============================================================
    //                        BUTTON OVERRIDE
    // ============================================================

    @Override
    public void onClick(View v) {
        this.parent.handleUserChoice(this.userDecision);
    }
}
