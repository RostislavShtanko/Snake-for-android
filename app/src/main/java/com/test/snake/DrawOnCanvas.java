package com.test.snake;

/**
 * Created by Ростислав on 09.04.2016.
 */
public class DrawOnCanvas implements DrawBehavior {

    public void drawLine(float x1, float y1, float x2, float y2) {
        GameView.canvas.drawLine(x1, y1, x2, y2, GameView.paint);
    }

    public void drawCircle(float x, float y, float r) {
        GameView.canvas.drawCircle(x, y, r, GameView.paint);
    }

    public void drawText(String text, float x, float y){
        GameView.canvas.drawText(text, x, y, GameView.paint);
    }

    public void drawRect(float x1, float y1, float x2, float y2){
        GameView.canvas.drawRect(x1, y1, x2, y2, GameView.paint);
    }
}
