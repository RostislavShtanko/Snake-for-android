package com.test.snake;

import android.graphics.Canvas;

/**
 * Created by Ростислав on 09.04.2016.
 */
public interface DrawBehavior {

    public void drawCircle(float x, float y, float r);

    public void drawLine(float x1, float y1, float x2, float y2);

    public void drawText(String text, float x, float y);

    public void drawRect(float x1, float y1, float x2, float y2);
}
