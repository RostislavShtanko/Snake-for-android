package com.test.snake;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goEasy(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("Speed", 5);
        startActivity(intent);
    }

    public void goMedium(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("Speed", 3);
        startActivity(intent);
    }

    public void goHard(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("Speed", 0);
        startActivity(intent);
    }

    public void showRecords(View view) {
        Intent intent = new Intent(this, RecordsActivity.class);
        startActivity(intent);
    }
}
