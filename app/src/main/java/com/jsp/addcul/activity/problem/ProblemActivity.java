package com.jsp.addcul.activity.problem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jsp.addcul.DTO.ProblemInfo;
import com.jsp.addcul.R;
import com.jsp.addcul.Util.DBHelper;
import com.jsp.addcul.activity.config.BasicActivity;
import com.jsp.addcul.adapter.ProblemAdapter;

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
        findViewById(R.id.img_search).setOnClickListener(onFooterlistner);

        // 로그인 인증
        ImageView myInfoProfile = findViewById(R.id.img_my_info);   // 프로필
        TextView myInfoText = findViewById(R.id.tv_my_info);     // 닉네임


        if (firebaseUser == null) { // 로그인 상태가 아닐때

        } else {  // 로그인 상태일때
            myInfoProfile.setImageResource(R.drawable.ic_account_circle_black_24dp);
            myInfoText.setText("내정보");

        }
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
            public void onTouch(View v, int position) {
                Log.e("출력 : ","출력 ");
                if(position == 0) {
                    startActivity(PregnancybirthActivity.class);
                }
                else if(position == 1){
                    startActivity(EmployActivity.class);
                }
                else if(position == 2){
                    startActivity(BasiclifeActivity.class);
                }
                else{
                    startActivity(BasiclifeActivity.class);
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


