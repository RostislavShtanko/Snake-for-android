package com.test.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Ростислав on 03.04.2016.
 */
public class Target {
    private float x, y;
    private Paint paint;
    private int color;
    private final static int RADIUS = 20;

    public Target(float x, float y) {
        this.x = x;
        this.y = y;

        color = getRandomColor();
        paint = new Paint();
    }

    public static int getRADIUS() {
        return RADIUS;
    }

    public void draw(Canvas canvas){
        paint.setColor(color);
        canvas.drawCircle(x, y, RADIUS, paint);
    }

    private int getRandomColor(){
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        return Color.rgb(r, g, b);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
