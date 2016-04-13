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

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static String createRecordsTable(){
        return "create table " + RECORDS_TABLE + " (" + BaseColumns._ID
                + " integer primary key autoincrement, user text not null, points integer);";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createRecordsTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + RECORDS_TABLE);
        onCreate(db);
    }
}
