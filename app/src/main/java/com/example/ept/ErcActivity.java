package com.example.ept;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ErcActivity extends AppCompatActivity {

    Double ex_rate1 = 0.1547;
    Double ex_rate2 = 0.132;
    Double ex_rate3 = 182.4343;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erc);

        //exchange dollar
        Button ex1 = (Button) findViewById(R.id.ex_1);
        ex1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                exchange(ex_rate1,"美元");
            }
        });
        //exchange euro
        Button ex2 = (Button) findViewById(R.id.ex_2);
        ex2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                exchange(ex_rate2,"欧元");
            }
        });
        //exchange won
        Button ex3 = (Button) findViewById(R.id.ex_3);
        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                exchange(ex_rate3,"韩元");
            }
        });
        //config exchange rate
        Button config = (Button) findViewById(R.id.config);
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent config_interface = new Intent(ErcActivity.this,ConfigActivity.class);
                config_interface.putExtra("ex_rate1",ex_rate1);
                config_interface.putExtra("ex_rate2",ex_rate2);
                config_interface.putExtra("ex_rate3",ex_rate3);
                startActivityForResult(config_interface,1);
            }
        });

    }

    void exchange(double ex_rate,String s){
        EditText yuan_input = (EditText) findViewById(R.id.yuan_input);
        if(yuan_input.getText().toString().length() == 0){
            Toast.makeText(ErcActivity.this, "No valid data entered!Please retry!!", Toast.LENGTH_SHORT).show();
            return;
        }
        TextView result = (TextView)findViewById(R.id.result);
        Double yuan = Double.valueOf(yuan_input.getText().toString());
        result.setText(yuan+"人民币≈"+ex_rate*yuan+s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    // config update
                    final Bundle bdl = data.getExtras();
                    this.ex_rate1 = bdl.getDouble("new_rate1");
                    this.ex_rate2 = bdl.getDouble("new_rate2");
                    this.ex_rate3 = bdl.getDouble("new_rate3");
                }
                break;
            default:
        }
    }
}