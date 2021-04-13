package com.example.addcul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.addcul.R;
import com.example.addcul.Util;
import com.example.addcul.adapter.ImageSliderAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    FirebaseUser firebaseUser;
    ImageView myInfoProfile;  // 이미지 뷰
    TextView myInfoText;    // 닉네임 text

    String nickName;
    String photoUrl;

    private com.example.addcul.Util util;
    int flag = 0;

    // 슬라이드

    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    private String[] images = new String[] {
            "https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F99F2E033599D964307",
            "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg" };

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // 파이어베이스 유저 초기화

        //FirebaseAuth.getInstance().signOut();


        // 슬라이드
        sliderViewPager = findViewById(R.id.sliderViewPager);
        layoutIndicator = findViewById(R.id.layoutIndicators);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(this, images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(images.length);


        myInfoProfile = findViewById(R.id.img_my_info);   // 프로필
        myInfoText = findViewById(R.id.tv_my_info);     // 닉네임



        util = new Util(this);

        if(firebaseUser == null) { // 로그인 상태가 아닐때
            flag = 0;
            myInfoProfile.setImageResource(R.drawable.ic_lock_open_black_24dp);
            myInfoText.setText("로그인");

        }else{  // 로그인 상태일때

            flag = 1;

            /* 구글
            Intent intent = getIntent();
            nickName = intent.getStringExtra("nickName");    // loginActivity로 부터 닉네임 전달받음
            photoUrl = intent.getStringExtra("photoUrl");    //loginActivity로 부터 프로필 전달받음

             */

            // 구글 로그인일 경우,
            if(nickName != null & photoUrl != null) {   // 구글 로그인 상태일때
                myInfoText.setText(nickName);   // 닉네임 텍스트뷰에 세팅
                Glide.with(this).load(photoUrl).into(myInfoProfile); // 프로필 URL을 이미지 뷰에 세팅

            }

            else{ // 일반 로그인일 경우


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
        findViewById(R.id.img_translate).setOnClickListener(onClickListener);
        findViewById(R.id.img_map).setOnClickListener(onClickListener);
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
                    startActivity(ReadChatActivity.class);
                    break;
                case R.id.tv_lang_change:
                    //startActivity()
                    break;
                // 한국문화
                case R.id.img_kor_info:
                    startActivity(KcultureActivity.class);
                    break;
                case R.id.tv_kor_info:
                    break;
                // 문제해결
                case R.id.img_problem:
                    startActivity(ProblemActivity.class);
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
                    Intent intent = new Intent(getApplicationContext(),MyInfoActivity.class);
                    intent.putExtra("nickname",nickName);
                    intent.putExtra("profile",String.valueOf(photoUrl));

                    if(flag==0){
                        startActivity(LoginActivity.class);
                    }else{


                        startActivity(intent);
                    }

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

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }
}
