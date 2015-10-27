package com.miyou.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Tevainui on 18/10/2015.
 */
public class Background {
    //taille de la frame d'une tuile, voir l'image
    private int frameHeight, frameWidth;
    private int frameCount;
    private Bitmap image;
    private int x,y,dx;
    private Rect frameToDraw; //la tuile a dessiner
    private RectF whereToDraw; //où dessiner la tuile

    //constructeur du background
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

    }

    //fonction pour dessiner le niveau
    public void draw(Canvas canvas){
        setFrameToDraw(0);
        for(int j=0; j<3; ++j){
            for(int i=0; i<4; ++i){
                whereToDraw.set(i * frameWidth, j * frameHeight, (i + 1) * frameWidth,(j+1)*frameHeight);
                canvas.drawBitmap(image,frameToDraw,whereToDraw,null);
            }
        }
        setFrameToDraw(1);
        for(int j=0; j<10; ++j){
            for(int i=4; i<15; ++i){
                whereToDraw.set(i * frameWidth, j * frameHeight, (i + 1) * frameWidth,(j+1)*frameHeight);
                canvas.drawBitmap(image,frameToDraw,whereToDraw,null);
            }
        }
        setFrameToDraw(24);
        for(int j=3; j<10; ++j){
            for(int i=0; i<4; ++i){
                whereToDraw.set(i * frameWidth, j * frameHeight, (i + 1) * frameWidth,(j+1)*frameHeight);
                canvas.drawBitmap(image,frameToDraw,whereToDraw,null);
            }
        }
        setFrameToDraw(26);
        for(int j=10; j<20; ++j){
            for(int i=0; i<15; ++i){
                whereToDraw.set(i * frameWidth, j * frameHeight, (i + 1) * frameWidth,(j+1)*frameHeight);
                canvas.drawBitmap(image,frameToDraw,whereToDraw,null);
            }
        }
        setFrameToDraw(21);
        for(int j=0; j<20; ++j){
            for(int i=15; i<20; ++i){
                whereToDraw.set(i * frameWidth, j * frameHeight, (i + 1) * frameWidth,(j+1)*frameHeight);
                canvas.drawBitmap(image,frameToDraw,whereToDraw,null);
            }
        }
    }

    //fonction pour choisir la tuile à dessiner, par défaut 0
    public void setFrameToDraw(int number){
        if(number < frameCount) {
            frameToDraw.left = (number % 8) * frameWidth;
            frameToDraw.right = frameToDraw.left + frameWidth;
            frameToDraw.top = (number/8) * frameHeight;
            frameToDraw.bottom = frameToDraw.top + frameHeight;
        }
    }

    public void setVector(int dx){
        this.dx = dx;
    }
}
