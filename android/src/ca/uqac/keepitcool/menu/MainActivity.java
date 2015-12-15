package ca.uqac.keepitcool.menu;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ca.uqac.keepitcool.R;
import ca.uqac.keepitcool.menu.fragments.PlayDialog;
import ca.uqac.keepitcool.menu.fragments.ScoreDialog;
import ca.uqac.keepitcool.menu.fragments.SettingsDialog;
import ca.uqac.keepitcool.menu.revealview.CircularRevealView;
import ca.uqac.keepitcool.quizz.Preferences;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private CircularRevealView revealView;
    private View selectedView;
    private int backgroundColor;
    private ImageView playButton;
    private boolean soundActivated;

    private MediaPlayer menuTheme;
    private MediaPlayer button1;
    private MediaPlayer button2;

    RelativeLayout layout;
    LinearLayout settings, score, share;
    int maxX, maxY;
    android.os.Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        soundActivated = Preferences.getSoundSetting(getBaseContext());
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
                        if(soundActivated) {
                            button1.start();
                        }
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
                        if(soundActivated) {
                            button2.start();
                        }
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
                        if(soundActivated) {
                            button2.start();
                        }
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

                Toast t = Toast.makeText(getApplicationContext(), sAux, Toast.LENGTH_SHORT);
                t.show();

                if(soundActivated) {
                    button2.start();
                }
                startActivity(Intent.createChooser(i, "Choose one"));

            }
        });

        menuTheme = MediaPlayer.create(MainActivity.this, R.raw.menu2);
        menuTheme.setLooping(true);
        button1 = MediaPlayer.create(MainActivity.this, R.raw.bouton1);
        button2 = MediaPlayer.create(MainActivity.this, R.raw.button6);
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
        if(soundActivated  && !menuTheme.isPlaying()) {
            menuTheme.start();
        } else if(!soundActivated && menuTheme.isPlaying()) {
            menuTheme.pause();
        }

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

    @Override
    public void onStart(){
        if(soundActivated) {
            menuTheme.start();
        }
        super.onStart();
    }

    @Override
    public void onPause(){
        super.onPause();
        menuTheme.pause();
    }

    @Override
     public void onResume(){
        if(soundActivated) {
            menuTheme.start();
        }
        super.onResume();
    }
}