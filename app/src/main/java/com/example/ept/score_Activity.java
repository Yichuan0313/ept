package com.example.ept;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class score_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores);
        //plus 1
        Button plus1 = (Button) findViewById(R.id.plus1);
        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                change(1,0);
            }
        });
        Button plus4 = (Button) findViewById(R.id.plus4);
        plus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                change(1,1);
            }
        });
        //plus 2
        Button plus2 = (Button) findViewById(R.id.plus2);
        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                change(2,0);
            }
        });
        Button plus5 = (Button) findViewById(R.id.plus5);
        plus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                change(2,1);
            }
        });
        //plus 3
        Button plus3 = (Button) findViewById(R.id.plus3);
        plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                change(3,0);
            }
        });
        Button plus6 = (Button) findViewById(R.id.plus6);
        plus6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                change(3,1);
            }
        });
        //reset
        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                change(0,0);
                change(0,1);
            }
        });

    }
    void change(int i,int flag){
        if(flag==0){
            TextView score_text = (TextView) findViewById(R.id.score_a);
            int score = Integer.valueOf(score_text.getText().toString());
            if(i==0){
                score_text.setText("0");
                return;
            }
            score += i;
            score_text.setText(""+score);
        }
        if(flag==1){
            TextView score_text = (TextView) findViewById(R.id.score_b);
            int score = Integer.valueOf(score_text.getText().toString());
            if(i==0){
                score_text.setText("0");
                return;
            }
            score += i;
            score_text.setText(""+score);
        }

    }
}