package com.miyou.myfirstgame;

/**
 * Created by Tevainui on 25/10/2015.
 */
public class MovingObject extends GameObject{
    private int movementSpeed=5;
    private Vector2 heading;

    public MovingObject(int id, int x, int y, int width,int height){
        super(id,x, y, width, height);
        heading = new Vector2(0,1);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void moveTowardPosition(int x,int y){
        Vector2 tmp = new Vector2(x - getX(),y - getY());
        float len = tmp.len();
        if(len >= 5.f){
            heading.set(tmp).nor();
            setX(getX() + (int)(heading.x * movementSpeed));
            setY(getY() + (int)(heading.y * movementSpeed));
        }
    }


}
