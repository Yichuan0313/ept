package com.example.ept;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ErcListActivity  extends ListActivity implements Runnable{

    public Handler handler;
    private static final String TAG = "ErcListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread t=new Thread(this);
        t.start();
        handler=new Handler(Looper.myLooper()){
            public void handleMessage(Message msg){
                if(msg.what==7){
                    List<String> l1=(List<String>) msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(ErcListActivity.this, android.R.layout.simple_list_item_1, l1);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    @SuppressLint("DefaultLocale")
    public void run(){

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message msg=handler.obtainMessage(7);
        List<String> ls=new ArrayList();

        try {
//            String page = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get().select("div[class='turn_page'] p span").text();
//            Log.i(TAG, "run: total_page="+page);
//            for(int i=1;i<=Integer.parseInt(page);i++){
            Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Elements rate_info  = doc.getElementsByTag("table").get(1).getElementsByTag("tr");
            rate_info.remove(0);//Remove index row
            for(Element info:rate_info){
                Elements tds = info.getElementsByTag("td");
                String Currency = tds.get(0).text();
                float ex_rate = 100/Float.valueOf(tds.get(5).text());
                ls.add(String.format("%7s\t%.4f",Currency,ex_rate));
                Log.i(TAG, "run: Currency = " + String.format("%7s",Currency) + "\tex_rate = " + (ex_rate));
            }
//            }
            msg.obj=ls;
            handler.sendMessage(msg);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
