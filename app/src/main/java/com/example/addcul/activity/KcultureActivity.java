package com.example.addcul.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.addcul.R;
import com.example.addcul.fragment.FragFood;
import com.example.addcul.fragment.FragLife;
import com.example.addcul.fragment.FragMusic;
import com.example.addcul.fragment.FragTravel;

import java.util.List;

public class KcultureActivity extends BasicActivity {

    FragmentManager fragmentManager;
    FragFood fragFood;
    FragMusic fragMusic;
    FragLife fragLife;
    FragTravel fragTravel;

    LinearLayout contentLayout, btn_food_layout, btn_music_layout, btn_life_layout, btn_travel_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kculture);


        //btn_food_layout,btn_life_layout,btn_music_layout,btn_travel_layout;

        btn_food_layout = (LinearLayout) findViewById(R.id.btn_food_layout);
        btn_music_layout = (LinearLayout) findViewById(R.id.btn_music_layout);
        btn_life_layout = (LinearLayout) findViewById(R.id.btn_life_layout);
        btn_travel_layout = (LinearLayout) findViewById(R.id.btn_travel_layout);
        contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

        // footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_home).setOnClickListener(onFootlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFootlistner);
        findViewById(R.id.img_map).setOnClickListener(onFootlistner);
        // 로그인
        findViewById(R.id.img_my_info).setOnClickListener(onFootlistner);
        findViewById(R.id.tv_my_info).setOnClickListener(onFootlistner);


      //  final Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contentLayout);

        fragmentManager = getSupportFragmentManager();
        // 프래그먼트 객체 만들기
        fragFood = new FragFood();
        fragMusic = new FragMusic();
        fragLife = new FragLife();
        fragTravel = new FragTravel();
        // 프래그먼트 첫 화면 로딩
        btn_food_layout.setBackgroundResource(R.drawable.part_round_back);
        btn_music_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
        btn_life_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
        btn_travel_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.contentLayout, fragFood);
        ft.commit();


        btn_food_layout.setOnClickListener(onClickListener);
        btn_music_layout.setOnClickListener(onClickListener);
        btn_life_layout.setOnClickListener(onClickListener);
        btn_travel_layout.setOnClickListener(onClickListener);


    }
    public interface onBackPressedListener{
        void onBackPressed();
    }

    @Override
    public void onBackPressed() {

        // 프래그먼트 onBackPressedListener 사용
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for(Fragment fragment:fragmentList){
            if(fragment instanceof onBackPressedListener){

            }
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            FragmentTransaction ft = fragmentManager.beginTransaction();
            switch (v.getId()) {
                case R.id.btn_food_layout:
                    Log.e("버튼 : ","버튼 클릭 ");
                    btn_food_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_music_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_life_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_travel_layout.setBackgroundColor(Color.parseColor("#0000ff00"));

                    ft = fragmentManager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.contentLayout, fragFood);
                    ft.commit();
                    break;
                case R.id.btn_music_layout:
                    btn_music_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_food_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_life_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_travel_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    ft = fragmentManager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.contentLayout, fragMusic);
                    ft.commit();
                    break;
                case R.id.btn_life_layout:
                    btn_life_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_food_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_music_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_travel_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    ft = fragmentManager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.contentLayout, fragLife);
                    ft.commit();
                    break;
                case R.id.btn_travel_layout:
                    btn_travel_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_music_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_food_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_life_layout.setBackgroundColor(Color.parseColor("#0000ff00"));

                    ft = fragmentManager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.contentLayout, fragTravel);
                    ft.commit();
                    break;
            }
        }
    };


}
