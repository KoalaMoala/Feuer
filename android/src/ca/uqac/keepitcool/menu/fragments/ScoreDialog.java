package ca.uqac.keepitcool.menu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.menu.revealview.CircularRevealView;

public class ScoreDialog extends FragmentDialog {

    LinearLayout score_01, score_02, score_03;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        revealView = (CircularRevealView) view.findViewById(R.id.reveal);
        score_01 = (LinearLayout) view.findViewById(R.id.score_01);
        score_02 = (LinearLayout) view.findViewById(R.id.score_02);
        score_03 = (LinearLayout) view.findViewById(R.id.score_03);
        frame = (FrameLayout) view.findViewById(R.id.frame);

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
