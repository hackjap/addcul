package com.example.addcul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.addcul.R;
import com.example.addcul.Util;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    FirebaseUser firebaseUser;
    ImageView myInfoProfile;  // 이미지 뷰
    TextView myInfoText;    // 닉네임 text
    private com.example.addcul.Util util;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myInfoProfile = findViewById(R.id.img_my_info);   // 프로필
        myInfoText = findViewById(R.id.tv_my_info);     // 닉네임

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // 파이어베이스 유저 초기화

        util = new Util(this);

        if(firebaseUser == null) { // 로그인 상태가 아닐때
            myInfoProfile.setImageResource(R.drawable.ic_lock_open_black_24dp);
            myInfoText.setText("로그인");

        }else{  // 로그인 상태일때

            Intent intent = getIntent();
            String nickName = intent.getStringExtra("nickName");    // loginActivity로 부터 닉네임 전달받음
            String photoUrl = intent.getStringExtra("photoUrl");    //loginActivity로 부터 프로필 전달받음

            if(nickName != null & photoUrl != null) {   // 구글 로그인
                myInfoText.setText(nickName);   // 닉네임 텍스트뷰에 세팅
                Glide.with(this).load(photoUrl).into(myInfoProfile); // 프로필 URL을 이미지 뷰에 세팅

            }
            else{

                myInfoProfile.setImageResource(R.drawable.img_bar_myinfo);
                myInfoText.setText("내정보");
                // 이미지 크키 설정
                ViewGroup.LayoutParams params = myInfoProfile.getLayoutParams();
                params.width = 100;
                params.height =100;
                myInfoProfile.setLayoutParams(params);

            }
            // myInfoProfile.setImageResource(R.drawable.ic_lock_open_black_24dp);




        }

        /*
         *  중간메뉴 (SOS,언어교환,한국문화,문제해결)
         */
        // SOS
        findViewById(R.id.img_sos).setOnClickListener(onClickListener);
        findViewById(R.id.tv_sos).setOnClickListener(onClickListener);
        // 언어교환
        findViewById(R.id.img_lang_change).setOnClickListener(onClickListener);
        findViewById(R.id.tv_lang_change).setOnClickListener(onClickListener);
        // 한국문화
        findViewById(R.id.img_kor_info).setOnClickListener(onClickListener);
        findViewById(R.id.tv_kor_info).setOnClickListener(onClickListener);
        // 문제해결
        findViewById(R.id.img_problem).setOnClickListener(onClickListener);
        findViewById(R.id.tv_problem).setOnClickListener(onClickListener);

        // 하단메뉴
        findViewById(R.id.img_home).setOnClickListener(onClickListener);
        findViewById(R.id.img_post).setOnClickListener(onClickListener);
        findViewById(R.id.img_search).setOnClickListener(onClickListener);
            // 로그인
        findViewById(R.id.img_my_info).setOnClickListener(onClickListener);
        findViewById(R.id.tv_my_info).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            /*
             *  중간메뉴
             */
            switch (v.getId()){
                // SOS
                case R.id.img_sos:
                    startActivity(ReadPostActivity.class);
                    break;

                case R.id.tv_sos:
                    startActivity(ReadPostActivity.class);
                    break;
                // 언어교환
                case R.id.img_lang_change :
                    //startActivity()
                    break;
                case R.id.tv_lang_change:
                    //startActivity()
                    break;
                // 한국문화
                case R.id.img_kor_info:
                    startActivity(YoutubeActivity.class);
                    break;
                case R.id.tv_kor_info:
                    startActivity(YoutubeActivity.class);
                    break;
                // 문제해결
                case R.id.img_problem:
                    //startActivity()
                    break;
                case R.id.tv_problem:
                    //startActivity()
                    break;

            /*
             * 하단메뉴
             */
                case R.id.img_home:{
                    FirebaseAuth.getInstance().signOut();
                    startActivity(MainActivity.class);
                    util.showToast("로그아웃 ");
                }

                case R.id.img_search:
                   // FirebaseAuth.getInstance().signOut();
                  //  startActivity(MainActivity.class);
                case R.id.img_post:
                    startActivity(ReadChatActivity.class);
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

    private void startActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
