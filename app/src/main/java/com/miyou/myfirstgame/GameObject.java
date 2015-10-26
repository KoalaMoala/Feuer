package com.miyou.myfirstgame;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Tevainui on 25/10/2015.
 */
public class GameObject {
    protected  int id;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public GameObject(int id, int x, int y, int width, int height){
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height= height;
    }

    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }
    public Rect getRectangle()
    {
        return new Rect(x, y, x+width, y+height);
    }

    public void moveTowardPosition(int x, int y){

    }

    public  void update(){

    }
    public void draw(Canvas canvas){

    }
}
