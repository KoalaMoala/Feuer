package com.miyou.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Tevainui on 18/10/2015.
 */
public class Background {
    private int frameHeight, frameWidth;
    private int frameCount;
    private Bitmap image;
    private int x,y,dx;
    private Rect frameToDraw;
    private RectF whereToDraw;


    public Background(Bitmap res, int frameHeight, int frameWidth, int frameCount){
        image = Bitmap.createScaledBitmap(res,
                frameWidth * 8,
                frameHeight * 8,
                false);
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        this.frameCount = frameCount;
        frameToDraw = new Rect(
                0,
                0,
                frameWidth,
                frameHeight);
        whereToDraw = new RectF(0,0,frameWidth,frameHeight);
    }

    public void update(){
        //x+=dx;
        if(x<-GamePanel.WIDTH){
            x=0;
        }
    }

    public void draw(Canvas canvas){
        for(int j=0; j<20; ++j){
            for(int i=0; i<20; ++i){
                whereToDraw.set(i * frameWidth, j * frameHeight, (i + 1) * frameWidth,(j+1)*frameHeight);
                canvas.drawBitmap(image,frameToDraw,whereToDraw,null);
            }
        }

    }

    public void setFrameToDraw(int number){
        if(number < frameCount) {
            frameToDraw.left = (number % 7) * frameWidth;
            frameToDraw.right = frameToDraw.left + frameWidth;
            frameToDraw.top = (number/7) * frameHeight;
            frameToDraw.bottom = frameToDraw.top + frameHeight;
        }
    }

    public void setVector(int dx){
        this.dx = dx;
    }
}
