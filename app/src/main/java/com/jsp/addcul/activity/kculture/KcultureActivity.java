package com.jsp.addcul.activity.kculture;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jsp.addcul.MainActivity;
import com.jsp.addcul.R;
import com.jsp.addcul.activity.config.BasicActivity;
import com.jsp.addcul.fragment.FragLife;
import com.jsp.addcul.fragment.FragNewWord;
import com.jsp.addcul.fragment.FragShop;
import com.jsp.addcul.fragment.FragTravel;

public class KcultureActivity extends BasicActivity {

    FragmentManager fragmentManager;
//    FragFood fragFood;
    FragNewWord fragNewWord;
//    FragMusic fragMusic;
    FragShop fragShop;
    FragLife fragLife;
    FragTravel fragTravel;

    LinearLayout contentLayout, btn_newWord_layout, btn_shop_layout, btn_life_layout, btn_travel_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kculture);


        //btn_food_layout,btn_life_layout,btn_music_layout,btn_travel_layout;

        btn_newWord_layout = (LinearLayout) findViewById(R.id.btn_newWord_layout);
        btn_shop_layout = (LinearLayout) findViewById(R.id.btn_shop_layout);
        btn_life_layout = (LinearLayout) findViewById(R.id.btn_life_layout);
        btn_travel_layout = (LinearLayout) findViewById(R.id.btn_travel_layout);
        contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

        // footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_search).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_home).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_map).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_my_info).setOnClickListener(onFooterlistner);


      //  final Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contentLayout);

        fragmentManager = getSupportFragmentManager();
        // 프래그먼트 객체 만들기
        fragNewWord = new FragNewWord();
        fragShop = new FragShop();
        fragLife = new FragLife();
        fragTravel = new FragTravel();
        // 프래그먼트 첫 화면 로딩
        btn_newWord_layout.setBackgroundResource(R.drawable.part_round_back);
        btn_shop_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
        btn_life_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
        btn_travel_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.contentLayout, fragNewWord);
        ft.commit();


        btn_newWord_layout.setOnClickListener(onClickListener);
        btn_shop_layout.setOnClickListener(onClickListener);
        btn_life_layout.setOnClickListener(onClickListener);
        btn_travel_layout.setOnClickListener(onClickListener);


        ImageView myInfoProfile = findViewById(R.id.img_my_info);   // 프로필
        TextView myInfoText = findViewById(R.id.tv_my_info);     // 닉네임


        if (firebaseUser == null) { // 로그인 상태가 아닐때

        } else {  // 로그인 상태일때
            myInfoProfile.setImageResource(R.drawable.ic_account_circle_black_24dp);
            myInfoText.setText("내정보");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //    public interface onBackPressedListener{
//        void onBackPressed();
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        // 프래그먼트 onBackPressedListener 사용
//        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
//        for(Fragment fragment:fragmentList){
//            if(fragment instanceof onBackPressedListener){
//
//            }
//        }
//
//    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            FragmentTransaction ft = fragmentManager.beginTransaction();
            switch (v.getId()) {
                case R.id.btn_newWord_layout:
                    Log.e("버튼 : ","버튼 클릭 ");
                    btn_newWord_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_shop_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_life_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_travel_layout.setBackgroundColor(Color.parseColor("#0000ff00"));

                    ft = fragmentManager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.contentLayout, fragNewWord);
                    ft.commit();
                    break;
                case R.id.btn_shop_layout:
                    btn_shop_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_newWord_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_life_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_travel_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    ft = fragmentManager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.contentLayout, fragShop);
                    ft.commit();

                    break;
                case R.id.btn_life_layout:
                    btn_life_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_newWord_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_shop_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_travel_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    ft = fragmentManager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.contentLayout, fragLife);
                    ft.commit();
                    break;
                case R.id.btn_travel_layout:
                    btn_travel_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_shop_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_newWord_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
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
