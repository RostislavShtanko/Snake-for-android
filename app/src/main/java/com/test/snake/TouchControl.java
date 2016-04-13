package com.test.snake;

import android.view.MotionEvent;

/**
 * Created by Ростислав on 09.04.2016.
 */
public class TouchControl implements ControlBehavior {

    Snake snake;
    private float initialX, initialY;
    public TouchControl(Snake snake) {
        this.snake = snake;
    }

    private void turnSnake(float dx, float dy) {
        if (Math.abs(dx) < Game.getSlideSize() || Math.abs(dy) < Game.getSlideSize()) {
            turnX(dx, dy);
            turnY(dx, dy);
        } else {
            if (Math.abs(dx) > Math.abs(dy)) {
                turnX(dx, dy);
            } else {
                turnY(dx, dy);
            }
        }
    }

    private void turnX(float dx, float dy) {
        if (dx >= Game.getSlideSize() && snake.getDirection() != "LEFT") {
            snake.turnRight();
        }
        if (dx <= -Game.getSlideSize() && snake.getDirection() != "RIGHT") {
            snake.turnLeft();
        }
    }

    private void turnY(float dx, float dy) {
        if (dy >= Game.getSlideSize() && snake.getDirection() != "BOTTOM") {
            snake.turnTop();
        }
        if (dy <= -Game.getSlideSize() && snake.getDirection() != "TOP") {
            snake.turnBot();
        }
    }

    public void control(){
        switch (Game.event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = Game.event.getX();
                initialY = Game.event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float dx = Game.event.getX() - initialX;
                float dy = Game.event.getY() - initialY;
                System.out.println(Game.event.getX() + " " + initialX + " " + Game.event.getY() + " " + initialY);
                turnSnake(dx, dy);
                break;
        }
    }

}
