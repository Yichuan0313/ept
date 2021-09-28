package com.example.ept;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ErcActivity extends AppCompatActivity implements Runnable{

    private static final String TAG = "ErcActivity";
    MyHandler handler = new MyHandler(this);
    public float ex_rate1 = -1;
    public float ex_rate2 = -1;
    public float ex_rate3 = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erc);

        Thread t = new Thread(this);
        t.start();

        SharedPreferences sp = getSharedPreferences("my_rate_1",ErcActivity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        ex_rate1 = sp.getFloat("ex_rate_1",0.1547f);
        ex_rate2 = sp.getFloat("ex_rate_2",0.132f);
        ex_rate3 = sp.getFloat("ex_rate_3",182.4343f);

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
        float yuan = Float.valueOf(yuan_input.getText().toString());
        result.setText(yuan+"人民币≈"+String.format("%.2f",ex_rate*yuan)+s);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    // config update
                    final Bundle bdl = data.getExtras();
                    ex_rate1 = bdl.getFloat("new_rate1");
                    Toast.makeText(ErcActivity.this,String.valueOf(ex_rate1), Toast.LENGTH_SHORT).show();
                    ex_rate2 = bdl.getFloat("new_rate2");
                    ex_rate3 = bdl.getFloat("new_rate3");
                    SharedPreferences sp = getSharedPreferences("my_rate_1",ErcActivity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putFloat("ex_rate_1",ex_rate1);
                    editor.putFloat("ex_rate_2",ex_rate2);
                    editor.putFloat("ex_rate_3",ex_rate3);

                    editor.apply();
                }
                break;
            default:
        }
    }

    @Override
    public void run() {
        Message msg = handler.obtainMessage(5);
        // get information from internet
        URL url = null;
        try{
            url = new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpsURLConnection http  = (HttpsURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            String html = Stream2String(in);
            Log.i(TAG, "run: "+html);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        msg.obj = "hello from run()";
        handler.sendMessage(msg);
    }

    public String Stream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream,"gb2312");
        while(true){
            int rsz = in.read(buffer,0,buffer.length);
            if (rsz < 0 )
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }

    static class MyHandler extends Handler {
        WeakReference<ErcActivity> mActivity;

        MyHandler(ErcActivity activity) {
            mActivity = new WeakReference<ErcActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ErcActivity theActivity = mActivity.get();
            if(msg.what == 5){
                //todo list
                TextView result = theActivity.findViewById(R.id.result);
                result.setText(msg.obj.toString());
            }
            super.handleMessage(msg);
        }
    }

}