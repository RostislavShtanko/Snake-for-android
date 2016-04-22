package com.test.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ростислав on 22.04.2016.
 */
public class GameView extends View {

    public static DrawBehavior drawBehavior;
    public static Paint paint;
    public static Canvas canvas;
    private Snake[] snakes;

    public GameView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(26);
        paint.setStrokeWidth(Snake.getSnakeWidth());

        drawBehavior = new DrawOnCanvas();
    }

    @Override
    public void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
        Game.gameStep();
        Game.target.draw();
        for(int i = 0; i < Game.CNT_OF_SNAKES; i++) {
            paint.setColor(Game.snakes[i].color);
            Game.snakes[i].drawSnake();
        }
        if (!Snake.isGameOver())
            invalidate();
    }

    public void drawText(String text, float x, float y){
        drawBehavior.drawText(text, x, y);
    }

    public void drawRect(float x1, float y1, float x2, float y2){
        drawBehavior.drawRect(x1, y1, x2, y2);
    }
}
