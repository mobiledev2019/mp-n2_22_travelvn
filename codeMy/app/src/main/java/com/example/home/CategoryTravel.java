package com.example.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.R;
import com.example.controller.ReadJsonDataTravel;
import com.example.model.Travel;
import com.example.model.URLjson;

import java.net.URL;
import java.util.ArrayList;
//import com.example.bottomnavigation.R;

public class CategoryTravel extends AppCompatActivity {
    private RecyclerView viewListCategoryTravels;
    ArrayList<Travel> listTravels;
    private  Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_travel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int number = intent.getIntExtra(MyAdapter.ID_TRAVEL, 0);
        String keyWord = getIntent().getStringExtra("KEY_WORD");
        TextView textView2 = (TextView) findViewById(R.id.id);
        textView2.setText("" + number);

        if(number != 0)
        {
            getTravelList(URLjson.getURLCategoryTravel(""+number));
        }
        else if(keyWord != null)
        {
            Log.d("URL", keyWord);
            getTravelList(URLjson.getURLSearch(keyWord));
        }
        else
        {
            getTravelList(URLjson.getURLCategoryTravel(""+number));

            Log.d("URL", "khong cos du lieu" + keyWord);

        }

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(5000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(listTravels != null)
                                {
                                    runView(listTravels);
                                    viewListCategoryTravels = findViewById(R.id.cate_travel);
                                    RecyclerView.LayoutManager layoutCategories = new LinearLayoutManager(getApplicationContext());
                                    viewListCategoryTravels.setLayoutManager(layoutCategories);

                                    CategoryTravelAdapter categoryTravelAdapter = new CategoryTravelAdapter(getApplicationContext(), listTravels);
                                    viewListCategoryTravels.setAdapter(categoryTravelAdapter);
                                }
                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        };
        thread.start();

    }

    public void runView(ArrayList<Travel> listTravels)
    {
        Log.d("DATA-size------", ""+listTravels.size());
    }
    public void getTravelList(String url) {
        ReadJsonDataTravel readJson = new ReadJsonDataTravel();
        readJson.execute(url);
        listTravels = readJson.travelList;
    }

}
