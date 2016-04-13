package com.test.snake;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

public class RecordsActivity extends AppCompatActivity {

    private DatabaseModel databaseModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        databaseModel = new DatabaseModel(MainActivity.getContext());
        putRecordsOnActivity();
    }

    private void putRecordsOnActivity(){
        Cursor cursor = databaseModel.getRecords();

        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.append("ТОП 5 ПИТОНОВ \n");
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        int count = 1;
        while (cursor.moveToNext()) {
            int points = cursor.getInt(cursor.getColumnIndex("points"));
            String name = cursor.getString(cursor
                    .getColumnIndex("user"));
            textView.append(name + " " + points + "\n");

            // Устанавливаем текстовое поле в системе компоновки activity
            System.out.println(name + " " + points);
            count ++;
            if(count > 5){
                break;
            }
        }
        setContentView(textView);
        cursor.close();
    }

}
