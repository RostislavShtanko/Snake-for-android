package com.test.snake;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.graphics.Point;

public class GameActivity extends AppCompatActivity {
    private Game a;
    private GameView gameView;
    private float initialX, initialY;
    public static int clientWidth, clientHeight;
    public static MotionEvent event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        int speed = 1;
        if (extras != null) {
            speed = extras.getInt("Speed");
        }

        super.onCreate(savedInstanceState);
        windowInit();

        setContentView(R.layout.activity_main);
        gameView = new GameView(this);
        a = new Game(this, speed, gameView);
        setContentView(gameView);
    }

    private void windowInit() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        clientWidth = size.x;
        clientHeight = size.y;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        this.event = event;
        System.out.println("wetqet");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                initialY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (Snake.isGameOver())
                    Game.processButton(initialX, initialY);
                break;
        }
        for(int i = 0; i < Game.CNT_OF_SNAKES; i++){
            if(Game.snakes[i].controlBehavior instanceof TouchControl){
                Game.snakes[i].controlBehavior.control();
            }
        }
        return true;
    }

}