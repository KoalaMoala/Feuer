package ca.uqac.keepitcool.menu;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.myfirstgame.Progress;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    public static final String PREFS_NAME = "MyPrefsFile";

    private CircularRevealView revealView;
    private View selectedView;
    private int backgroundColor;
    private ImageView playButton;

    RelativeLayout layout;
    LinearLayout settings, score, share;
    int maxX, maxY;
    android.os.Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast t = Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT);
        //t.show();
        setContentView(R.layout.activity_main);

        // Restore preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String progressStr = preferences.getString("progress", "");
        Progress progress = Progress.getInstance(progressStr);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("progress",progress.generateStringFromProgress());
        editor.apply();
        Toast t = Toast.makeText(getApplicationContext(), progress.getDescriptionFromCurrentLevel(), Toast.LENGTH_LONG);
        t.show();

        //boolean silent = settings.getBoolean("silentMode", false);
        //setSilent(silent);

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(this.getResources().getString(R.string.app_name));

        revealView = (CircularRevealView) findViewById(R.id.reveal);
        backgroundColor = Color.parseColor("#11303030");
        playButton = (ImageView) findViewById(R.id.button);
        layout = (RelativeLayout) findViewById(R.id.layout);
        settings = (LinearLayout) findViewById(R.id.source);
        score = (LinearLayout) findViewById(R.id.rate);
        share = (LinearLayout) findViewById(R.id.share);

        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxX = mdispSize.x;
        maxY = mdispSize.y;

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int color = Color.parseColor("#00bcd4");
                final Point p = getLocationInView(revealView, v);
                revealView.reveal(p.x, p.y, color, v.getHeight() / 2, 440, null);
                selectedView = v;
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showPlayDialog();
                    }
                }, 50);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int color = Color.parseColor("#6234E2");
                final Point p = getLocationInView(revealView, v);
                revealView.reveal(p.x / 2, p.y / 2, color, v.getHeight() / 2, 440, null);
                selectedView = v;
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showSettingsDialog();
                    }
                }, 50);
            }
        });

        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int color = Color.parseColor("#007F46");
                final Point p = getLocationInView(revealView, v);
                revealView.reveal(p.x / 2, p.y / 2, color, v.getHeight() / 2, 440, null);
                selectedView = v;
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showScoreDialog();
                    }
                }, 50);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String sAux = "\nCheck out this beautiful app to get a better understanding on how to handle fire incidents.\n\n";
                sAux = sAux + "<Insert cool URL here>\n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);

                //Toast t = Toast.makeText(getApplicationContext(), sAux, Toast.LENGTH_SHORT);
                //t.show();

                startActivity(Intent.createChooser(i, "Choose one"));
            }
        });
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

    public void revealFromTop() {
        final int color = Color.parseColor("#ffffff");
        final Point p = new Point(maxX / 2, maxY / 2);
        revealView.reveal(p.x, p.y, color, playButton.getHeight() / 2, 440, null);
    }

    private void showPlayDialog() {
        FragmentManager fm = getFragmentManager();
        PlayDialog playDialog = new PlayDialog();
        playDialog.show(fm, "fragment_play");
    }

    private void showSettingsDialog() {
        FragmentManager fm = getFragmentManager();
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.show(fm, "fragment_settings");
    }

    private void showScoreDialog() {
        FragmentManager fm = getFragmentManager();
        ScoreDialog scoreDialog = new ScoreDialog();
        scoreDialog.show(fm, "fragment_score");
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        View v = playButton;
        final Point p = getLocationInView(revealView, v);
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
                layout.setVisibility(View.VISIBLE);
            }
        }, 500);
    }
}