package com.test.snake;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Complexity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complexity);
        secActiveButtonColor();
    }

    public void secActiveButtonColor(){
        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        System.out.println(button1);
        int color = Color.rgb(60, 172, 43);
        if(DatabaseModel.getComplexity() == 0){
            button3.setBackgroundColor(color);
        } else if(DatabaseModel.getComplexity() == 3){
            button2.setBackgroundColor(color);
        } else {
            button1.setBackgroundColor(color);
        }
    }

    public void goEasy(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        DatabaseModel.updComplexity(5);
        startActivity(intent);
    }

    public void goMedium(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        DatabaseModel.updComplexity(3);
        startActivity(intent);
    }

    public void goHard(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        DatabaseModel.updComplexity(0);
        startActivity(intent);
    }

}
