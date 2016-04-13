package com.test.snake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ростислав on 13.04.2016.
 */
public class DatabaseModel {

    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;

    public DatabaseModel(Context context){

        mDatabaseHelper = new DatabaseHelper(context);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();

    }

    public Cursor getRecords(){
        Cursor cursor = mSqLiteDatabase.query("records", new String[]{
                        "user", "points"}, null,
                null,
                null,
                null,
                "points DESC"
        );

        return cursor;
    }

    public void addRecord(String name, int value) {
        ContentValues values = new ContentValues();
        // Задайте значения для каждого столбца
        values.put("user", name);
        values.put("points", value);
        // Вставляем данные в таблицу
        mSqLiteDatabase.insert("records", null, values);
    }
}
