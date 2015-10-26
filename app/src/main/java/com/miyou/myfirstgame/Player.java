package com.miyou.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Tevainui on 25/10/2015.
 */
public class Player extends MovingObject{
    private boolean isMoving=false;
    private Bitmap image;

    public Player(int x, int y, int width,int height, int id, Bitmap res){
        super(id, x, y, width, height);
        image = res;
    }

    @Override
    public void update(){
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, getX(), getY(), null);
    }

    @Override
    public void moveTowardPosition(int x, int y) {
        super.moveTowardPosition(x, y);
    }
}