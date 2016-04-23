package com.test.snake;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.content.Intent;

import java.util.Random;

/**
 * Created by Ростислав on 02.04.2016.
 */

public class Game{

    public static Snake[] snakes;
    public static int CNT_OF_SNAKES = 2;
    public static Target target;
    public static Context context;
    private static int snakeSpeed;
    public static GameView gameView;
    private static CanvasButton finishButton;
    public static CanvasButton[] controlButtons;

    public final static int GAME_WINDOW_TOP = 30;
    public final static int GAME_WINDOW_RIGHT = GameActivity.clientWidth;
    public final static int GAME_WINDOW_LEFT = 0;
    public final static int CONTROL_BLOCK_SIZE = 227;
    public static int GAME_WINDOW_BOT = GameActivity.clientHeight;
    private final static float SLIDE_SIZE = 30;

    public static boolean gameOver;

    public Game(Context context, int snakeSpeed, GameView gameView, int mode) {
        this.snakeSpeed = snakeSpeed;
        this.gameView = gameView;
        this.context = context;
        gameOver = false;
        initGame(mode);
        snakes = new Snake[CNT_OF_SNAKES];
        snakesInit();
        targetInit();
        if(checkButtonControl()) {
            GAME_WINDOW_BOT = GameActivity.clientHeight - CONTROL_BLOCK_SIZE;
        } else {
            GAME_WINDOW_BOT = GameActivity.clientHeight - 50;
        }
        buttonsInit();
    }

    private void initGame(int mode){
        if(mode == 1)
            CNT_OF_SNAKES = 1;
        else
            CNT_OF_SNAKES = 2;
    }

    private boolean checkButtonControl() {
        for(int i = 0; i < CNT_OF_SNAKES; i++){
            if(snakes[i].controlBehavior instanceof ButtonControl)
                return true;
        }
        return false;
    }

    private void buttonsInit() {
        controlButtons = new CanvasButton[4];
        finishButton = new CanvasButton(GameActivity.clientWidth / 2 - 200, GameActivity.clientHeight / 2 - 50, GameActivity.clientWidth / 2 + 200, GameActivity.clientHeight / 2 + 50,
                "Начать заново",Color.rgb(124, 232, 108), 46);
        for(int i = 0; i < 4; i++){
            String text = "";
            int x1 = 0, x2 = 0, y1 = 0, y2 = 0, fontSize = 30, color = Color.rgb(124, 232, 108);
            if(i == 0) {
                text = "TOP";
                x1 = GameActivity.clientWidth / 2 - 50;
                y1 = GameActivity.clientHeight - 225;
                x2 = GameActivity.clientWidth / 2 + 50;
                y2 = GameActivity.clientHeight - 175;
            }
            else if(i == 1) {
                text = "RIGHT";
                x1 = GameActivity.clientWidth / 2 + 20;
                y1 = GameActivity.clientHeight - 170;
                x2 = GameActivity.clientWidth / 2 + 120;
                y2 = GameActivity.clientHeight - 120;
            }
            else if(i == 2) {
                text = "DOWN";
                x1 = GameActivity.clientWidth / 2 - 50;
                y1 = GameActivity.clientHeight - 115;
                x2 = GameActivity.clientWidth / 2 + 50;
                y2 = GameActivity.clientHeight - 65;
            }
            else {
                text = "LEFT";
                x1 = GameActivity.clientWidth / 2 - 120;
                y1 = GameActivity.clientHeight - 170;
                x2 = GameActivity.clientWidth / 2 - 20;
                y2 = GameActivity.clientHeight - 120;
            }
            controlButtons[i] = new CanvasButton(x1, y1, x2, y2, text, color, fontSize);
        }
    }

    private void snakesInit(){
        for(int i = 0; i < CNT_OF_SNAKES; i++) {
            int color = Color.rgb(255, 20, 147);
            String control = "touch";
            if(i != 0) {
                control = "buttons";
                color = Color.rgb(30, 30, 30);
            }
            snakes[i] = new Snake(i * 20 + 10, GAME_WINDOW_TOP + i*GAME_WINDOW_BOT/CNT_OF_SNAKES + 10, 0, 1, 50, control, color);
        }
    }

    public static void targetInit() {
        Random rand = new Random();
        int r = Target.getRADIUS();
        int tx = rand.nextInt(GAME_WINDOW_RIGHT - 2 * r) + 2*r;
        int ty = rand.nextInt(GAME_WINDOW_BOT - 2 * r) + 2*r;
        tx = Math.min(tx, GameActivity.clientWidth - 2*r);
        ty = Math.min(ty, GameActivity.clientHeight - CONTROL_BLOCK_SIZE - 2*r);
        ty = Math.max(ty, GAME_WINDOW_TOP + r);
        System.out.println(tx + " target " + ty);
        target = new Target(tx, ty);
    }

    public static float getSlideSize() {
        return SLIDE_SIZE;
    }


    public static void gameStep() {
        //Snake s = new Snake(10, 10, 1, 0, 10, canvas);
        setGameOver();
        for(int i = 0; i < CNT_OF_SNAKES; i++)
            snakes[i].move();
        if (!gameOver) {
            try {
                Thread.sleep(snakeSpeed * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameView.drawText("Points " + getPointsOfSnakes(), GameActivity.clientWidth / 2 - 50, 23);
        } else {
            finishGame();
        }
    }

    private static int getPointsOfSnakes(){
        int res = 0;
        for(int i = 0; i < CNT_OF_SNAKES; i++){
            res += snakes[i].getPoints();
        }
        return res;
    }

    private static void finishGame(){
        if(isInTopFive(getPointsOfSnakes())){
            Intent intent = new Intent(context, AddRecordActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("Points", getPointsOfSnakes());
            context.startActivity(intent);
        } else {
            gameView.drawText("Конец игры. Ваш результат  " + getPointsOfSnakes() + " очков.", GameActivity.clientWidth / 2 - 250, 23);
            finishButton.draw();
        }
    }

    private static boolean isInTopFive(int currPoints){
        Cursor cursor = DatabaseModel.getRecords();
        int count = 1;
        int res = 0;
        while (cursor.moveToNext()) {
            res = cursor.getInt(cursor.getColumnIndex("points"));
            count ++;
            if(count > 5){
                break;
            }
        }
        if(currPoints > res || count <= 5)
            return true;
        return false;
    }

    public static void processButton(float x, float y) {
        //y -= 50;
        System.out.println("INTENT");
        if (finishButton.isClickInside(x, y)) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        }
    }

    public static void setGameOver() {
        for(int i = 0; i < CNT_OF_SNAKES; i++) {
            float[] coordsX = snakes[i].getCoordsX();
            float[] coordsY = snakes[i].getCoordsY();
            float hx = coordsX[snakes[i].getSize() - 1];
            float hy = coordsY[snakes[i].getSize() - 1];
            for(int j = 0; j < CNT_OF_SNAKES; j++) {
                coordsX = snakes[j].getCoordsX();
                coordsY = snakes[j].getCoordsY();
                for (int k = 1; k < snakes[j].getSize() - 1; k++) {
                    if ((((hx >= coordsX[k - 1] && hx <= coordsX[k]) || (hx <= coordsX[k - 1] && hx >= coordsX[k]))
                            && Math.abs(coordsX[k - 1] - coordsX[k]) == Snake.getBlockSize() && hy > coordsY[k - 1] - Snake.getBlockSize() && hy < coordsY[k - 1] + Snake.getBlockSize()) ||
                            (((hy >= coordsY[k - 1] && hy <= coordsY[k]) || (hy <= coordsY[k - 1] && hy >= coordsY[k]))
                                    && Math.abs(coordsY[k - 1] - coordsY[k]) == Snake.getBlockSize() && hx > coordsX[k - 1] - Snake.getBlockSize() && hx < coordsX[k - 1] + Snake.getBlockSize())) {
                        gameOver = true;
                    }
                }
            }
        }

    }

}
