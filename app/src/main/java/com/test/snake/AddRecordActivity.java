package com.test.snake;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddRecordActivity extends AppCompatActivity {

    private int points;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            points = extras.getInt("Points");
        }
        TextView textView = (TextView)findViewById(R.id.textView3);
        textView.setText("Поздравляем!\n Вы вошли в пятерку лучших, набрав " + points + " очков!");
    }

    public void addNewRecord(View view) {
        EditText mEdit = (EditText)findViewById(R.id.editTextName);
        name = mEdit.getText().toString();

        DatabaseModel.addRecord(name, points);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
