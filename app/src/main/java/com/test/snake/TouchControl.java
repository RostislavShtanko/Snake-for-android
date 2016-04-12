package com.test.snake;

import android.content.Intent;
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
        if (Math.abs(dx) < Animation.getSlideSize() || Math.abs(dy) < Animation.getSlideSize()) {
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
        if (dx >= Animation.getSlideSize() && snake.getDirection() != "LEFT") {
            snake.turnRight();
        }
        if (dx <= -Animation.getSlideSize() && snake.getDirection() != "RIGHT") {
            snake.turnLeft();
        }
    }

    private void turnY(float dx, float dy) {
        if (dy >= Animation.getSlideSize() && snake.getDirection() != "BOTTOM") {
            snake.turnTop();
        }
        if (dy <= -Animation.getSlideSize() && snake.getDirection() != "TOP") {
            snake.turnBot();
        }
    }

    public void control(){
        switch (Animation.event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = Animation.event.getX();
                initialY = Animation.event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float dx = Animation.event.getX() - initialX;
                float dy = Animation.event.getY() - initialY;
                System.out.println(Animation.event.getX() + " " + initialX + " " + Animation.event.getY() + " " + initialY);
                turnSnake(dx, dy);
                break;
        }
    }

}
