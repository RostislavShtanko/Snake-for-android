package com.test.snake;

/**
 * Created by Ростислав on 12.04.2016.
 */

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns{
    private static final String DATABASE_NAME = "snake.db";
    private static final int DATABASE_VERSION = 1;
    public static final String RECORDS_TABLE = "records";
    public static final String GAME_STATE_TABLE = "gameState";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static String createRecordsTable(){
        return "create table " + RECORDS_TABLE + " (" + BaseColumns._ID
                + " integer primary key autoincrement, user text not null, points integer);";
    }

    private static String createComplexityTable() {
        return "create table " + GAME_STATE_TABLE + " (" + BaseColumns._ID
                + " integer primary key autoincrement, complexity integer);";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DB CREATE");
        db.execSQL(createRecordsTable());
        db.execSQL(createComplexityTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + RECORDS_TABLE);
        db.execSQL("DROP TABLE IF IT EXISTS " + GAME_STATE_TABLE);
        onCreate(db);
    }

}
