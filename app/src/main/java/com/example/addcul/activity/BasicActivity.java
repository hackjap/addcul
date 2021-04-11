package com.example.addcul.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addcul.Util;


// 모든 액티비티의 설정 액티비티

public class BasicActivity extends AppCompatActivity {

    Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 세로모드 보기 고정

        /*
        util = new Util(this);
        //
        // 하단메뉴
        findViewById(R.id.img_home).setOnClickListener(onClickListener);
        findViewById(R.id.img_translate).setOnClickListener(onClickListener);
        findViewById(R.id.img_map).setOnClickListener(onClickListener);
        // 로그인
        findViewById(R.id.img_my_info).setOnClickListener(onClickListener);
        findViewById(R.id.tv_my_info).setOnClickListener(onClickListener);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {


             //하단메뉴

            switch (v.getId()) {
                case R.id.img_home: {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(MainActivity.class);
                    util.showToast("로그아웃 ");
                    break;
                }

                case R.id.img_map:
                    // FirebaseAuth.getInstance().signOut();
                    startActivity(GoogleMapActivitiy.class);
                case R.id.img_translate:
                    startActivity(TranslationActivity.class);
                    break;
                // 로그인
                case R.id.tv_my_info:
                    startActivity(LoginActivity.class);
                case R.id.img_my_info:
                    startActivity(LoginActivity.class);
                    break;
            } // end of switch

        }


    };



         */
    }
        private void startActivity (Class c){
            Intent intent = new Intent(this, c);
            startActivity(intent);
        }

}