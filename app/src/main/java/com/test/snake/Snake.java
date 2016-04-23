package com.test.snake;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Ростислав on 03.04.2016.
 */
public class Snake {

    private float startX, startY, dx, dy;
    private float[] coordsX, coordsY;
    private int size, points;
    public int color;
    private static int cnt;
    public ControlBehavior controlBehavior;
    private static boolean isTurn;

    private final static int BLOCK_SIZE = 10;
    private final static int MAX_SNAKE_SIZE = 2000;
    private final static int SNAKE_WIDTH = 10;

    public Snake(float startX, float startY, float dx, float dy, int size, String control, int color) {
        this.startX = startX;
        this.startY = startY;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.color = color;
        if(control == "touch"){
            controlBehavior = new TouchControl(this);
        } else if(control == "buttons"){
            controlBehavior = new ButtonControl(this);
        }
        init();
    }

    private void init() {

        points = 0;
        cnt = 0;
        isTurn = false;

        coordsX = new float[MAX_SNAKE_SIZE];
        coordsY = new float[MAX_SNAKE_SIZE];
        for (int i = 0; i < size; i++) {
            coordsX[i] = startX + i * BLOCK_SIZE;
            coordsY[i] = startY;
        }
    }

    public int getPoints() {
        return points;
    }

    public float[] getCoordsX() {
        return coordsX;
    }

    public float[] getCoordsY() {
        return coordsY;
    }

    public int getSize() {
        return size;
    }

    public static int getBlockSize() {
        return BLOCK_SIZE;
    }

    public static boolean isTurn() {
        return isTurn;
    }

    public static int getSnakeWidth() {
        return SNAKE_WIDTH;
    }

    public void move() {
        for (int i = 0; i < size - 1; i++) {
            coordsX[i] = coordsX[i + 1];
            coordsY[i] = coordsY[i + 1];
        }
        coordsX[size - 1] = coordsX[size - 2] + dx * BLOCK_SIZE;
        coordsY[size - 1] = coordsY[size - 2] + dy * BLOCK_SIZE;
        processWalls();
        processTarget();
        isTurn = false;
    }

    private void processWalls() {
        if (coordsX[size - 1] > Game.GAME_WINDOW_RIGHT) {
            coordsX[size - 1] = Game.GAME_WINDOW_LEFT;
        }
        if (coordsX[size - 1] < Game.GAME_WINDOW_LEFT) {
            coordsX[size - 1] = Game.GAME_WINDOW_RIGHT;
        }
        if (coordsY[size - 1] > Game.GAME_WINDOW_BOT) {
            coordsY[size - 1] = Game.GAME_WINDOW_TOP + SNAKE_WIDTH / 2;
        }
        if (coordsY[size - 1] < Game.GAME_WINDOW_TOP) {
            coordsY[size - 1] = Game.GAME_WINDOW_BOT - SNAKE_WIDTH / 2;
        }
    }

    private void processTarget() {
        float tx = Game.target.getX();
        float ty = Game.target.getY();
        float r = Game.target.getRADIUS();
        if ((coordsX[size - 1] >= tx - r && coordsX[size - 1] <= tx + r) &&
                (coordsY[size - 1] >= ty - r && coordsY[size - 1] <= ty + r)) {
            Game.targetInit();
            increaseSize();
            points += 5;
        }
    }

    public void drawSnake() {
        float coordsX[] = getCoordsX();
        float coordsY[] = getCoordsY();

        if (!isTurn())
            drawHead();
        for (int i = 0; i < size - 2; i++) {
            if ((Math.abs(coordsX[i] - coordsX[i + 1]) == BLOCK_SIZE && coordsY[i] - coordsY[i + 1] == 0) ||
                    (Math.abs(coordsY[i] - coordsY[i + 1]) == BLOCK_SIZE && coordsX[i] - coordsX[i + 1] == 0)) {
                GameView.drawBehavior.drawLine(coordsX[i], coordsY[i], coordsX[i + 1], coordsY[i + 1]);
            }
        }
    }

    public void drawHead() {
        float r = SNAKE_WIDTH / 2;
        if (getDirection() == "TOP") {
            GameView.drawBehavior.drawCircle(coordsX[size - 1], coordsY[size - 1] - BLOCK_SIZE, r);
        } else if (getDirection() == "RIGHT") {
            GameView.drawBehavior.drawCircle(coordsX[size - 1] - BLOCK_SIZE, coordsY[size - 1], r);
        } else if (getDirection() == "BOTTOM") {
            GameView.drawBehavior.drawCircle(coordsX[size - 1], coordsY[size - 1] + BLOCK_SIZE, r);
        } else {
            GameView.drawBehavior.drawCircle(coordsX[size - 1] + BLOCK_SIZE, coordsY[size - 1], r);
        }
    }

    public String getDirection() {
        if (dx == 0 && dy == 1) {
            return "TOP";
        } else if (dx == 1 && dy == 0) {
            return "RIGHT";
        } else if (dx == 0 && dy == -1) {
            return "BOTTOM";
        }
        return "LEFT";
    }

    public void turnLeft() {
        dx = -1;
        dy = 0;
        isTurn = true;
    }

    public void turnRight() {
        dx = 1;
        dy = 0;
        isTurn = true;
    }

    public void turnTop() {
        dy = 1;
        dx = 0;
        isTurn = true;
    }

    public void turnBot() {
        dy = -1;
        dx = 0;
        isTurn = true;
    }

    private void increaseSize() {
        size++;
        coordsX[size - 1] = coordsX[size - 2] + dx * BLOCK_SIZE;
        coordsY[size - 1] = coordsY[size - 2] + dy * BLOCK_SIZE;
    }
}