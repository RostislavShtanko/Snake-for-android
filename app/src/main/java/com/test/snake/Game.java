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

public class Game extends View {

    private Paint paint;
    private Snake snake;
    public static Target target;
    public static Context context;
    private int snakeSpeed;
    private float initialX, initialY;
    private ControlBehavior controlBehavior;
    public static Canvas canvas;
    public static MotionEvent event;
    private DatabaseModel databaseModel;

    private final static float SLIDE_SIZE = 30;

    public Game(Context context, int snakeSpeed) {
        super(context);
        this.snakeSpeed = snakeSpeed;
        snake = new Snake(10, 10, 0, 1, 50);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(26);
        targetInit();
        this.context = context;
        controlBehavior = new TouchControl(snake);
        databaseModel = new DatabaseModel(context);
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


    @Override
    public void onDraw(Canvas canvas) {
        //Snake s = new Snake(10, 10, 1, 0, 10, canvas);
        this.canvas = canvas;
        super.onDraw(canvas);
        target.draw(canvas);
        snake.move();
        controlSnake();
        if (!Snake.isGameOver()) {
            try {
                Thread.sleep(snakeSpeed * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            invalidate();
            canvas.drawText("Points " + snake.getPoints(), GameActivity.clientWidth / 2 - 50, GameActivity.clientHeight - 70, paint);
        } else {
            finishGame();
        }
    }

    private void finishGame(){
        if(isInTopFive(snake.getPoints())){
            Intent intent = new Intent(Game.context, AddRecordActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("Points", snake.getPoints());
            Game.context.startActivity(intent);
        } else {
            canvas.drawText("Конец игры. Ваш результат  " + snake.getPoints() + " очков.", GameActivity.clientWidth / 2 - 200, GameActivity.clientHeight - 70, paint);
            putButton(canvas);
        }
    }

    private boolean isInTopFive(int currPoints){
        Cursor cursor = databaseModel.getRecords();
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

    private void putButton(Canvas canvas) {
        paint.setColor(Color.rgb(124, 232, 108));
        canvas.drawRect(GameActivity.clientWidth / 2 - 200, GameActivity.clientHeight / 2 - 50, GameActivity.clientWidth / 2 + 200, GameActivity.clientHeight / 2 + 50, paint);
        Rect mTextBoundRect = new Rect();
        String text = "Начать заново";
        paint.setColor(Color.BLACK);
        paint.setTextSize(46);

        paint.getTextBounds(text, 0, text.length(), mTextBoundRect);
        float textWidth = paint.measureText(text);
        float textHeight = mTextBoundRect.height();
        canvas.drawText(text, GameActivity.clientWidth / 2 - (textWidth / 2f), GameActivity.clientHeight / 2 + (textHeight / 2f), paint);
    }

    private void controlSnake(){
        if(!(controlBehavior instanceof TouchControl)){
            controlBehavior.control();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.event = event;
        switch (Game.event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = Game.event.getX();
                initialY = Game.event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (Snake.isGameOver())
                    processButton(initialX, initialY);
                break;
        }
        if(controlBehavior instanceof TouchControl) {
            controlBehavior.control();
        }
        return true;
    }

    public void processButton(float x, float y) {
        float bx1 = GameActivity.clientWidth / 2 - 200;
        float by1 = GameActivity.clientHeight / 2 - 50;
        float bx2 = GameActivity.clientWidth / 2 + 200;
        float by2 = GameActivity.clientHeight / 2 + 50;
        if (x >= bx1 && x <= bx2 && y >= by1 && y <= by2) {
            Intent intent = new Intent(Game.context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Game.context.startActivity(intent);
        }
    }

}
