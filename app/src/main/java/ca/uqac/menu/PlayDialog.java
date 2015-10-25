package ca.uqac.menu;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ca.uqac.myfirstgame.Game;
import ca.uqac.myfirstgame.R;

public class PlayDialog extends DialogFragment {

    public PlayDialog() {}

    LinearLayout level_01, level_02, level_03, placeholder_01, placeholder_02, placeholder_03;
    FrameLayout frame, frame2;
    private CircularRevealView revealView;
    private View selectedView;
    ProgressBar progress;
    TextView status, status_detail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        final int backgroundColor = Color.parseColor("#ffffff");

        revealView = (CircularRevealView) view.findViewById(R.id.reveal);
        level_01 = (LinearLayout) view.findViewById(R.id.level_01);
        level_02 = (LinearLayout) view.findViewById(R.id.level_02);
        level_03 = (LinearLayout) view.findViewById(R.id.level_03);
        placeholder_01 = (LinearLayout) view.findViewById(R.id.placeholder_01);
        placeholder_02 = (LinearLayout) view.findViewById(R.id.placeholder_02);
        placeholder_03 = (LinearLayout) view.findViewById(R.id.placeholder_03);
        frame = (FrameLayout) view.findViewById(R.id.frame);
        frame2 = (FrameLayout) view.findViewById(R.id.frame2);
        status = (TextView) view.findViewById(R.id.status);
        status_detail = (TextView) view.findViewById(R.id.status_detail);
        progress = (ProgressBar) view.findViewById(R.id.progress);

        progress.getIndeterminateDrawable().setColorFilter(
                Color.parseColor("#ffffff"),
                android.graphics.PorterDuff.Mode.SRC_IN);


        level_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Game.class);
                startActivity(intent);
            }
        });

        level_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Game.class);
                startActivity(intent);
            }
        });

        level_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Game.class);
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
                Intent intent = new Intent(getActivity(), Game.class);
                startActivity(intent);
            }
        });

        placeholder_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Game.class);
                startActivity(intent);
            }
        });

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;

        window.setAttributes(windowParams);
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity != null && activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

        return new Point(l1[0], l1[1]);
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }
}
