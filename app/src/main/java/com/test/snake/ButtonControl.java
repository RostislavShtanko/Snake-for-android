package com.test.snake;

import android.view.MotionEvent;

/**
 * Created by Ростислав on 23.04.2016.
 */
public class ButtonControl implements ControlBehavior {
    private float initialX, initialY;
    Snake snake;

    public ButtonControl(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void control() {
        switch (GameActivity.event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = GameActivity.event.getX();
                initialY = GameActivity.event.getY();
                break;
            case MotionEvent.ACTION_UP:
                turnSnake(initialX, initialY);
                break;
        }
    }

    public void turnSnake(float x, float y){
        System.out.println(Game.controlButtons[0].x1 + " " + Game.controlButtons[0].y1 + " " + Game.controlButtons[0].x2 + " " + Game.controlButtons[0].y2 + " " + x + " " + y + " BUT");
        if(Game.controlButtons[0].isClickInside(x, y) && snake.getDirection() != "TOP"){
            snake.turnBot();
        }
        else if(Game.controlButtons[1].isClickInside(x, y) && snake.getDirection() != "LEFT"){
            snake.turnRight();
        }
        else if(Game.controlButtons[2].isClickInside(x, y) && snake.getDirection() != "BOTTOM"){
            snake.turnTop();
        }
        else if(Game.controlButtons[3].isClickInside(x, y) && snake.getDirection() != "RIGHT"){
            snake.turnLeft();
        }
    }
}
