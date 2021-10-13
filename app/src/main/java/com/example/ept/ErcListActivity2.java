package com.example.ept;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ErcListActivity2 extends AppCompatActivity implements Runnable{

    public Handler handler;
    private static final String TAG = "ErcListActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erc_list2);

        Thread t=new Thread(this);
        t.start();
        handler=new Handler(Looper.myLooper()){
            public void handleMessage(Message msg){
                if(msg.what==5){
                    ArrayList<HashMap<String,String>> listItems = (ArrayList<HashMap<String,String>>) msg.obj;
                    SimpleAdapter my_list = new SimpleAdapter(ErcListActivity2.this,listItems,R.layout.list_item,new String[]{"ItemTitle","ItemDetail"},new int[]{R.id.itemTitle,R.id.itemDetail});
                    ListView myList2 = findViewById(R.id.my_list);
                    myList2.setAdapter(my_list);
                }
                super.handleMessage(msg);
            }
        };

    }
    @SuppressLint("DefaultLocale")
    public void run(){

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message msg=handler.obtainMessage(5);
        ArrayList<HashMap<String,String>> listItems =new ArrayList<HashMap<String,String>>();

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
                HashMap<String,String> map=new HashMap<String,String>();
                map.put("ItemTitle",Currency);
                map.put("ItemDetail",""+ex_rate);
                listItems.add(map);
                Log.i(TAG, "run: Currency = " + String.format("%7s",Currency) + "\tex_rate = " + (ex_rate));
            }
//            }
            msg.obj=listItems;
            handler.sendMessage(msg);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}