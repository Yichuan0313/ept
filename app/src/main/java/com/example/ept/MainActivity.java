package com.example.ept;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transform);
        Button bt = (Button) findViewById(R.id.calculate);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // get height
                EditText height_input = (EditText) findViewById(R.id.height_input);
                double height = Double.valueOf(height_input.getText().toString());
                // get weight
                EditText weight_input = (EditText) findViewById(R.id.weight_input);
                double weight = Double.valueOf(weight_input.getText().toString());
                // calculate BMI
                double BMI = weight / Math.pow(height / 100, 2);
                // give suggestion
                String suggestion = "";
                if(BMI<=18.4){
                    suggestion = "偏瘦，请适当增加营养摄入。";
                }
                if(BMI>=18.5&&BMI<=23.9){
                    suggestion = "正常，请保持当前的饮食和运动习惯。";
                }
                if(BMI>=24.0&&BMI<=27.9){
                    suggestion = "过重，请适当减少饮食，增加运动。";
                }
                if(BMI>=28.0){
                    suggestion = "肥胖，请务必减少饮食并适当增加运动量。";
                }
                TextView result = (TextView) findViewById(R.id.suggest);
                result.setText("您当前的BMI为"+String.format("%.2f", BMI)+","+suggestion);
            }
        });
    }
}