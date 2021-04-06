package com.example.addcul.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addcul.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.util.Util;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FirebaseUser firebaseUser;
    ImageView myInfoImage;
    TextView myInfoText;
    private Util util;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myInfoImage = findViewById(R.id.img_my_info);
        myInfoText = findViewById(R.id.tv_my_info);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser == null) { // 로그인 상태가 아닐때
            myInfoImage.setImageResource(R.drawable.ic_lock_open_black_24dp);
            myInfoText.setText("로그인");
        }else{  // 로그인 상태일때
            myInfoImage.setImageResource(R.drawable.img_bar_myinfo);
            myInfoText.setText("내정보");
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
        findViewById(R.id.img_my_info).setOnClickListener(onClickListener);
        findViewById(R.id.img_post).setOnClickListener(onClickListener);
        findViewById(R.id.img_search).setOnClickListener(onClickListener);

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
                    //startActivity()
                    break;
                case R.id.tv_kor_info:
                    //startActivity()
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
                case R.id.img_search:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(MainActivity.class);
                case R.id.img_post:
                    startActivity(ReadChatActivity.class);
                    break;
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
}
