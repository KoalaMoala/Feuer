package ca.uqac.keepitcool.menu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;
import java.util.TreeMap;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.menu.revealview.CircularRevealView;
import ca.uqac.keepitcool.quizz.Preferences;

public class ScoreDialog extends FragmentDialog {

    LinearLayout score_01, score_02, score_03, score_04, score_05;
    TextView score_text_01, score_text_02, score_text_03, score_text_04,  score_text_05;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        frame = (FrameLayout) view.findViewById(R.id.frame);
        revealView = (CircularRevealView) view.findViewById(R.id.reveal);
        score_text_01 = (TextView) view.findViewById(R.id.score_01_text);
        score_text_02 = (TextView) view.findViewById(R.id.score_02_text);
        score_text_03 = (TextView) view.findViewById(R.id.score_03_text);
        score_text_04 = (TextView) view.findViewById(R.id.score_04_text);
        score_text_05 = (TextView) view.findViewById(R.id.score_05_text);

        score_01 = (LinearLayout) view.findViewById(R.id.score_01);
        score_02 = (LinearLayout) view.findViewById(R.id.score_02);
        score_03 = (LinearLayout) view.findViewById(R.id.score_03);
        score_04 = (LinearLayout) view.findViewById(R.id.score_04);
        score_05 = (LinearLayout) view.findViewById(R.id.score_05);

        // Retrieve all scores
        Map scores = new TreeMap<>();
        for (int i = 0; i < 99; i++) {
            Float score = Preferences.getLevelScore(i,getActivity().getApplicationContext());
            if(!Float.isNaN(score) && score != 0) {
                scores.put("Niveau " + i, score);
            }
        }
        this.updateScore(scores);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    // ============================================================
    //                          SCORE UPDATE
    // ============================================================

    private void updateScore(Map scores) {
        Object[] c = scores.keySet().toArray();
        TextView currentScoreView = null;
        LinearLayout currentScoreContainer = null;
        for(int i=0; i < c.length; i++) {
            currentScoreContainer = this.getLinearLayoutFromIndex(i);
            currentScoreContainer.setVisibility(View.VISIBLE);
            currentScoreView = this.getScoreViewFromIndex(i);
            currentScoreView.setText(c[i] + ": " + scores.get(c[i]) + " s");
        }
    }

    // ============================================================
    //                    ELEMENT BASED ON INDEX
    // ============================================================

    private TextView getScoreViewFromIndex(int index) {
        switch (index) {
            case 0:
                return score_text_01;
            case 1:
                return score_text_02;
            case 2:
                return score_text_03;
            case 3:
                return score_text_04;
            case 4:
                return score_text_05;
            default:
                return score_text_01;
        }
    }

    private LinearLayout getLinearLayoutFromIndex(int index) {
        switch (index) {
            case 0:
                return score_01;
            case 1:
                return score_02;
            case 2:
                return score_03;
            case 3:
                return score_04;
            case 4:
                return score_05;
            default:
                return score_01;
        }
    }
}
