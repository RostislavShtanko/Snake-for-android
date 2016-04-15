package com.test.snake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ростислав on 13.04.2016.
 */
public class DatabaseModel {

    private static DatabaseHelper mDatabaseHelper = new DatabaseHelper(MainActivity.getAppContext());;
    private static SQLiteDatabase mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();

    public static Cursor getRecords(){
        Cursor cursor = mSqLiteDatabase.query("records", new String[]{
                        "user", "points"}, null,
                null,
                null,
                null,
                "points DESC"
        );

        return cursor;
    }

    public static void addRecord(String name, int value) {
        ContentValues values = new ContentValues();
        // Задайте значения для каждого столбца
        values.put("user", name);
        values.put("points", value);
        // Вставляем данные в таблицу
        mSqLiteDatabase.insert("records", null, values);
    }

    public static void dropTable(String title) {
        mSqLiteDatabase.delete(title, null, null);
    }
}
