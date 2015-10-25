package ca.uqac.menu;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;

import ca.uqac.menu.CircularRevealView;
import ca.uqac.menu.PlayDialog;
import ca.uqac.myfirstgame.R;

public class XposedMainActivity extends Activity implements DialogInterface.OnDismissListener {

    private CircularRevealView revealView;
    private int backgroundColor;
    android.os.Handler handler;
    int maxX, maxY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_xposed);
        revealView = (CircularRevealView) findViewById(R.id.reveal);

        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxX = mdispSize.x;
        maxY = mdispSize.y;

        final int color = Color.parseColor("#00bcd4");
        final Point p = new Point(maxX / 2, maxY / 2);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                revealView.reveal(p.x, p.y, color, 2, 440, null);
            }
        }, 500);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showPowerDialog();
            }
        }, 800);
    }

    private void showPowerDialog() {
        FragmentManager fm = getFragmentManager();
        PlayDialog playDialog = new PlayDialog();
        playDialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppThemeDialog);
        playDialog.show(fm, "fragment_play");
    }

    public void revealFromTop() {
        final int color = Color.parseColor("#ffffff");
        final Point p = new Point(maxX / 2, maxY / 2);
        revealView.reveal(p.x, p.y, color, 2, 440, null);
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        final Point p = new Point(maxX / 2, maxY / 2);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                revealView.hide(p.x, p.y, backgroundColor, 0, 330, null);
            }
        }, 300);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        }, 500);
    }
}
