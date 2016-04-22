package com.test.snake;

import android.graphics.Color;
import android.graphics.Rect;

/**
 * Created by Ростислав on 22.04.2016.
 */
public class Button {
    public float x1, y1, x2, y2;
    public String text;
    public int color, fontsize;

    public Button(float x1, float y1, float x2, float y2, String text, int color, int fontSize) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.text = text;
        this.color = color;
        this.fontsize = fontSize;
    }

    public void draw() {
        GameView.paint.setColor(color);
        GameView.paint.setTextSize(fontsize);
        GameView.drawBehavior.drawRect(x1, y1, x2, y2);
        Rect mTextBoundRect = new Rect();
        GameView.paint.getTextBounds(text, 0, text.length(), mTextBoundRect);
        float textWidth = GameView.paint.measureText(text);
        float textHeight = mTextBoundRect.height();

        GameView.paint.setColor(Color.BLACK);
        System.out.println("HEIGHT = " + textWidth);
        GameView.drawBehavior.drawText(text, x1 + (x2 - x1) / 2 - (textWidth / 2f), y1 + (y2 - y1) / 2 + (textHeight / 2f));
    }

    public boolean isClickInside(float x, float y) {
        if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
            return true;
        }
        return false;
    }
}
