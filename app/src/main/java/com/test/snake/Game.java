package com.test.snake;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.content.Intent;

import java.util.Random;

/**
 * Created by Ростислав on 02.04.2016.
 */

public class Game{

    public static Snake[] snakes;
    public final static int CNT_OF_SNAKES = 2;
    public static Target target;
    public static Context context;
    private static int snakeSpeed;
    private static GameView gameView;
    private static Button finishButton;

    private final static float SLIDE_SIZE = 30;

    public Game(Context context, int snakeSpeed, GameView gameView) {
        this.snakeSpeed = snakeSpeed;
        targetInit();
        this.gameView = gameView;
        snakes = new Snake[CNT_OF_SNAKES];
        snakesInit();
        this.context = context;
        finishButton = new Button(GameActivity.clientWidth / 2 - 200, GameActivity.clientHeight / 2 - 50, GameActivity.clientWidth / 2 + 200, GameActivity.clientHeight / 2 + 50,
                "Начать заново",Color.rgb(124, 232, 108), 46);
    }

    private void snakesInit(){
        for(int i = 0; i < CNT_OF_SNAKES; i++) {
            int color = Color.rgb(255, 20, 147);
            if(i != 0)
                color = Color.rgb(30, 30, 30);
            snakes[i] = new Snake(i * 20 + 10, i * 20 + 10, 0, 1, 50, "touch", color);
        }
    }

    public static void targetInit() {
        Random rand = new Random();
        int r = Target.getRADIUS();
        int tx = rand.nextInt(GameActivity.clientWidth - 2 * r) + r;
        int ty = rand.nextInt(GameActivity.clientHeight - 2 * r) + r;
        ty = Math.min(ty, 1100);
        ty = Math.max(ty, r);
        target = new Target(tx, ty);
    }

    public static float getSlideSize() {
        return SLIDE_SIZE;
    }


    public static void gameStep() {
        //Snake s = new Snake(10, 10, 1, 0, 10, canvas);
        for(int i = 0; i < CNT_OF_SNAKES; i++)
            snakes[i].move();
        if (!Snake.isGameOver()) {
            try {
                Thread.sleep(snakeSpeed * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameView.drawText("Points " + getPointsOfSnakes(), GameActivity.clientWidth / 2 - 50, GameActivity.clientHeight - 70);
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
            gameView.drawText("Конец игры. Ваш результат  " + getPointsOfSnakes() + " очков.", GameActivity.clientWidth / 2 - 200, GameActivity.clientHeight - 70);
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
        y -= 50;
        System.out.println("INTENT");
        if (finishButton.isClickInside(x, y)) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        }
    }

}
