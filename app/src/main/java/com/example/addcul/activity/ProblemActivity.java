package com.example.addcul.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.DBHelper;
import com.example.addcul.ProblemInfo;
import com.example.addcul.R;
import com.example.addcul.adapter.ProblemAdapter;

import java.util.ArrayList;


// 모든 액티비티의 설정 액티비티

public class ProblemActivity extends BasicActivity {


    RecyclerView recyclerView;
    LinearLayout contentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_problem);

        // footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_home).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_map).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_my_info).setOnClickListener(onFooterlistner);


        // DB 구현
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select title,subtitle,photo from problem",null);
        ArrayList<ProblemInfo> list = new ArrayList<>();

        while(cursor.moveToNext()){
            ProblemInfo problemInfo = new ProblemInfo();
            problemInfo.setTitle(cursor.getString(0));
            problemInfo.setSubtitle(cursor.getString(1));
            problemInfo.setPhoto(cursor.getString(2));
            list.add(problemInfo);
        }


        ProblemAdapter problemAdapter = new ProblemAdapter(list,this);

        recyclerView = (RecyclerView)findViewById(R.id.rv_problem);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(problemAdapter);

        problemAdapter.setOnMyTouchListener(new ProblemAdapter.OnMyTouchListener() {
            @Override
            public void onTouch(View v, int postion) {
                Log.e("출력 : ","출력 ");
                if(postion == 0) {
                    startActivity(PregnancybirthActivity.class);
                }
                else{
                    startActivity(ExperienceActivity03_2.class);
                }
            }
        });

        /*
        FoodAdapter foodAdapter = new FoodAdapter(list);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(foodAdapter);

        foodAdapter.setOnMyTouchListener(new FoodAdapter.OnMyTouchListener() {
            @Override
            public void onTouch(View v, int postion) {
                Log.e("출력 : ","출력 ");
                startActivity(YoutubeActivity.class);

            }
        });








        View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getId()) {

            }
        }
    };



         */
}
    private void startActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }


    }


