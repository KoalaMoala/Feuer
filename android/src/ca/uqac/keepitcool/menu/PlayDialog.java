package ca.uqac.keepitcool.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import ca.uqac.keepitcool.AndroidLauncher;
import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.myfirstgame.Game;
import ca.uqac.keepitcool.quizz.BranchingStoryActivity;

public class PlayDialog extends FragmentDialog {

    LinearLayout level_01, level_02, level_03, placeholder_01, placeholder_02, placeholder_03;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        revealView = (CircularRevealView) view.findViewById(R.id.reveal);
        level_01 = (LinearLayout) view.findViewById(R.id.level_01);
        level_02 = (LinearLayout) view.findViewById(R.id.level_02);
        level_03 = (LinearLayout) view.findViewById(R.id.level_03);
        placeholder_01 = (LinearLayout) view.findViewById(R.id.placeholder_01);
        placeholder_02 = (LinearLayout) view.findViewById(R.id.placeholder_02);
        placeholder_03 = (LinearLayout) view.findViewById(R.id.placeholder_03);
        frame = (FrameLayout) view.findViewById(R.id.frame);

        level_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                startActivity(intent);
            }
        });

        level_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                startActivity(intent);
            }
        });

        level_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchingStoryActivity.class);
                startActivity(intent);
            }
        });

        placeholder_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Game.class);
                startActivity(intent);
            }
        });

        placeholder_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AndroidLauncher.class);
                startActivity(intent);
            }
        });

        placeholder_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AndroidLauncher.class);
                startActivity(intent);
            }
        });

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }
}
