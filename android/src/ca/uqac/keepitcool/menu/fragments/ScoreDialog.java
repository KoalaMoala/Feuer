package ca.uqac.keepitcool.menu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.menu.revealview.CircularRevealView;
import ca.uqac.keepitcool.quizz.Preferences;

public class ScoreDialog extends FragmentDialog {

    LinearLayout score_01, score_02, score_03, score_04, score_05;
    TextView score_text_01, score_text_02, score_text_03, score_text_04,  score_text_05;
    LinkedHashMap<String, Float> scores;
    TreeMap<String, Float> scoresSorted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

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

        frame = (FrameLayout) view.findViewById(R.id.frame);
        scores = new LinkedHashMap<>();
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

        score_02.setVisibility(View.GONE);
        score_03.setVisibility(View.GONE);
        score_04.setVisibility(View.GONE);
        score_05.setVisibility(View.GONE);

        //
        Object[] c = scores.keySet().toArray();
        if(c.length > 0 )
            score_text_01.setText(c[0] + " : " + scores.get(c[0]) + " s");
        if(c.length > 1 )
        {
            score_text_02.setText(c[1] + " : " + scores.get(c[1]) + " s");
            score_02.setVisibility(View.VISIBLE);
        }
        if(c.length > 2 )
        {
            score_text_03.setText(c[2] + " : " + scores.get(c[2]) + " s");
            score_03.setVisibility(View.VISIBLE);
        }
        if(c.length > 3 )
        {
            score_text_04.setText(c[3] + " : " + scores.get(c[3]) + " s");
            score_04.setVisibility(View.VISIBLE);
        }
        if(c.length > 4 )
        {
            score_text_05.setText(c[4] + " : " + scores.get(c[4]) + " s");
            score_05.setVisibility(View.VISIBLE);
        }

        score_text_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        score_text_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        score_text_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }
}
