package com.example.addcul.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addcul.R;
import com.example.addcul.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


// 모든 액티비티의 설정 액티비티

public class BasicActivity extends AppCompatActivity {

    Util util;
    int flag =0;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 세로모드 보기 고정

        util = new Util(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        if (firebaseUser == null) { // 로그인 상태가 아닐때
            flag = 0;

        } else {  // 로그인 상태일때


        }
    }
    View.OnClickListener onFootlistner = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            //하단메뉴

            switch (v.getId()) {
                case R.id.img_home: {
                    startActivity(MainActivity.class);
                   // FirebaseAuth.getInstance().signOut();
                   // util.showToast("로그아웃 ");
                    break;
                }

                case R.id.img_map:
                    // FirebaseAuth.getInstance().signOut();
                    startActivity(GoogleMapActivitiy.class);
                    break;
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



    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

}