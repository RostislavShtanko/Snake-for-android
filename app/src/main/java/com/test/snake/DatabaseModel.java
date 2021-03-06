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

    public static void addRecord(String name, int value) {
        ContentValues values = new ContentValues();
        // Задайте значения для каждого столбца
        values.put("user", name);
        values.put("points", value);
        // Вставляем данные в таблицу
        mSqLiteDatabase.insert("records", null, values);
    }

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

    public static void updComplexity(int value){
        ContentValues values = new ContentValues();
        // Задайте значения для каждого столбца
        values.put("complexity", value);
        // Вставляем данные в таблицу
        mSqLiteDatabase.replace("gameState", null, values);
    }

    public static int getComplexity(){
        Cursor cursor = mSqLiteDatabase.query("gameState", new String[]{
                        "complexity"}, null,
                null,
                null,
                null,
                null
        );
        int res = 0;
        while (cursor.moveToNext()) {
            res = cursor.getInt(cursor.getColumnIndex("complexity"));
        }
        return res;
    }

    public static void dropTable(String title) {
        mSqLiteDatabase.delete(title, null, null);
    }
}
