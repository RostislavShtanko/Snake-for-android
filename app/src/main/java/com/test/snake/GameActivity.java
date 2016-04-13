package com.test.snake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.graphics.Point;

public class GameActivity extends AppCompatActivity {
    private Game a;
    public static int clientWidth, clientHeight;

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
        a = new Game(this, speed);
        setContentView(a);
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

}