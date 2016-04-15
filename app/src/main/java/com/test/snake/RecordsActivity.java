package com.test.snake;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        putRecordsOnActivity();
    }

    private void putRecordsOnActivity(){
        Cursor cursor = DatabaseModel.getRecords();

        TextView textView = (TextView)findViewById(R.id.textViewRecords);
        textView.setTextSize(20);
        textView.setText("");
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
        cursor.close();
    }

    public void clearClick(View view) {
        Toast toast;
        try {
            DatabaseModel.dropTable("records");
            toast = Toast.makeText(getApplicationContext(),
                    "Рекурды успешно очищены!", Toast.LENGTH_SHORT);
        } catch (NullPointerException e){
            toast = Toast.makeText(getApplicationContext(),
                    "Ошибка!", Toast.LENGTH_SHORT);
            toast.show();
        }
        toast.show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
