package com.miyou.myfirstgame;

import android.app.VoiceInteractor;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Tevainui on 18/10/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH=512;
    public static final int HEIGHT=512;

    private MainThread thread;
    private Background bg;
    private ArrayList<GameObject> gameObjects;

    public GamePanel(Context context){
        super(context);

        //add the callback to the surfaceHolder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make panel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;

        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
                retry = false;
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.tiles),32,32,64);
        bg.setVector(-5);

        gameObjects = new ArrayList<>();
        gameObjects.add(new Player(50, 50, 19, 19, 0,BitmapFactory.decodeResource(getResources(), R.drawable.square)));
        //we can safely start the gameloop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        final float scaleFactorX =getWidth()/(WIDTH*1.f);
        final float scaleFactorY =getHeight()/(HEIGHT*1.f);

        int action = event.getActionMasked();
        Player player = (Player)gameObjects.get(0);
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                player.setMoving(true);
                player.setDxy((int)(event.getX()/scaleFactorX),(int)(event.getY()/scaleFactorY));
                break;

            case MotionEvent.ACTION_MOVE:
                player.setDxy((int)(event.getX()/scaleFactorX),(int)(event.getY()/scaleFactorY));
                break;

            case MotionEvent.ACTION_UP:
                player.setMoving(false);
                break;

            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_OUTSIDE:
                break;
        }
        return true;
    }

    public void update(){
        bg.update();
        gameObjects.get(0).update();
    }

    @Override
    public void draw(Canvas canvas){
        final float scaleFactorX =getWidth()/(WIDTH*1.f);
        final float scaleFactorY =getHeight()/(HEIGHT*1.f);
        if(canvas!=null){
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            gameObjects.get(0).draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }
}
