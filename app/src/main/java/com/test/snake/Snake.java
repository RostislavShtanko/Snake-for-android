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
    private static int cnt;
    private Paint paint;
    private DrawBehavior drawBehavior;
    private static boolean isTurn, gameOver;

    private final static int BLOCK_SIZE = 10;
    private final static int MAX_SNAKE_SIZE = 2000;
    private final static int SNAKE_WIDTH = 10;

    public Snake(float startX, float startY, float dx, float dy, int size) {
        this.startX = startX;
        this.startY = startY;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.rgb(255, 20, 147));
        paint.setStrokeWidth(SNAKE_WIDTH);

        drawBehavior = new DrawOnCanvas(this);

        points = 0;
        cnt = 0;
        isTurn = false;
        gameOver = false;

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

    public static boolean isGameOver() {
        return gameOver;
    }

    public static boolean isTurn() {
        return isTurn;
    }

    public Paint getPaint() {
        return paint;
    }

    public static int getSnakeWidth() {
        return SNAKE_WIDTH;
    }

    public void move() {
        drawSnake();
        setGameOver();
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
        if (coordsX[size - 1] > GameActivity.clientWidth) {
            coordsX[size - 1] = 0;
        }
        if (coordsX[size - 1] < 0) {
            coordsX[size - 1] = GameActivity.clientWidth;
        }
        if (coordsY[size - 1] > GameActivity.clientHeight - 50) {
            coordsY[size - 1] = 0;
        }
        if (coordsY[size - 1] < 0) {
            coordsY[size - 1] = GameActivity.clientHeight - 50;
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
                drawBehavior.drawLine(coordsX[i], coordsY[i], coordsX[i + 1], coordsY[i + 1]);
            }
        }
    }

    public void drawHead() {
        float r = SNAKE_WIDTH / 2;
        if (getDirection() == "TOP") {
            drawBehavior.drawCircle(coordsX[size - 1], coordsY[size - 1] - BLOCK_SIZE, r);
        } else if (getDirection() == "RIGHT") {
            drawBehavior.drawCircle(coordsX[size - 1] - BLOCK_SIZE, coordsY[size - 1], r);
        } else if (getDirection() == "BOTTOM") {
            drawBehavior.drawCircle(coordsX[size - 1], coordsY[size - 1] + BLOCK_SIZE, r);
        } else {
            drawBehavior.drawCircle(coordsX[size - 1] + BLOCK_SIZE, coordsY[size - 1], r);
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

    private void setGameOver() {
        float hx = coordsX[size - 1];
        float hy = coordsY[size - 1];
        for (int i = 1; i < size - 15; i++) {
            if ((((hx >= coordsX[i - 1] && hx <= coordsX[i]) || (hx <= coordsX[i - 1] && hx >= coordsX[i]))
                    && Math.abs(coordsX[i - 1] - coordsX[i]) == BLOCK_SIZE && hy > coordsY[i - 1] - BLOCK_SIZE && hy < coordsY[i - 1] + BLOCK_SIZE) ||
                    (((hy >= coordsY[i - 1] && hy <= coordsY[i]) || (hy <= coordsY[i - 1] && hy >= coordsY[i]))
                            && Math.abs(coordsY[i - 1] - coordsY[i]) == BLOCK_SIZE && hx > coordsX[i - 1] - BLOCK_SIZE && hx < coordsX[i - 1] + BLOCK_SIZE)) {
                gameOver = true;
                System.out.println(i + " " + coordsX[i - 1] + " " + coordsX[i] + " " + hx + " " + coordsY[i - 1] + " " + coordsY[i] + " " + hy);
            }
        }

    }

    public void turnLeft() {
        System.out.println(coordsX[size - 2] + " " + coordsY[size - 2] + " " + coordsX[size - 1] + " " + coordsY[size - 1]);
        dx = -1;
        dy = 0;
        isTurn = true;
    }

    public void turnRight() {
        System.out.println(coordsX[size - 2] + " " + coordsY[size - 2] + " " + coordsX[size - 1] + " " + coordsY[size - 1]);
        dx = 1;
        dy = 0;
        isTurn = true;
    }

    public void turnTop() {
        System.out.println(coordsX[size - 2] + " " + coordsY[size - 2] + " " + coordsX[size - 1] + " " + coordsY[size - 1]);
        dy = 1;
        dx = 0;
        isTurn = true;
    }

    public void turnBot() {
        System.out.println(coordsX[size - 2] + " " + coordsY[size - 2] + " " + coordsX[size - 1] + " " + coordsY[size - 1]);
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