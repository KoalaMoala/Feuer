package ca.uqac.keepitcool.menu.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.menu.revealview.CircularRevealView;
import ca.uqac.keepitcool.quizz.Preferences;

public class ScoreDialog extends FragmentDialog {

    TextView score_01, score_02, score_03, score_04,  score_05;
    HashMap<String, Float> scores;
    TreeMap<String, Float> scoresSorted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        revealView = (CircularRevealView) view.findViewById(R.id.reveal);
        score_01 = (TextView) view.findViewById(R.id.score_01_text);
        score_02 = (TextView) view.findViewById(R.id.score_02_text);
        score_03 = (TextView) view.findViewById(R.id.score_03_text);
        score_04 = (TextView) view.findViewById(R.id.score_04_text);
        score_05 = (TextView) view.findViewById(R.id.score_05_text);

        frame = (FrameLayout) view.findViewById(R.id.frame);
        scores = new HashMap<>();
        scoresSorted = new TreeMap<>();

        //get all scores
        for (int i = 0; i < 99; i++)
        {
            Float score = Preferences.getLevelScore(i,getActivity().getApplicationContext());
            if(!Float.isNaN(score) && score != 0) { scores.put("Niveau " + i, score); }
        }

        //sort scores
        if(!scores.isEmpty())
            scoresSorted.putAll(scores);

        //
        Object[] c = scores.keySet().toArray();
        if(c.length > 0 ) score_01.setText(c[0] + " : " + scores.get(c[0]) + " s");
        if(c.length > 1 ) score_02.setText(c[1] + " : " + scores.get(c[1]) + " s");
        if(c.length > 2 ) score_03.setText(c[2] + " : " + scores.get(c[2]) + " s");
        if(c.length > 3 ) score_04.setText(c[3] + " : " + scores.get(c[3]) + " s");
        if(c.length > 4 ) score_05.setText(c[4] + " : " + scores.get(c[4]) + " s");

        score_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        score_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        score_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }
}
