package com.example.addcul.activity.post;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.DTO.PostInfo;
import com.example.addcul.MainActivity;
import com.example.addcul.R;
import com.example.addcul.Util.Util;
import com.example.addcul.activity.account.LoginActivity;
import com.example.addcul.activity.config.BasicActivity;
import com.example.addcul.fragment.FragPostFree;
import com.example.addcul.fragment.FragPostSecret;
import com.example.addcul.fragment.FragPostSos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReadPostActivity extends BasicActivity {

    private static final String TAG = "ReadPostActivity";

    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.Adapter mainAdapter;
    private ArrayList<PostInfo> postList;
    private Util util;

    FragmentManager fragmentManager;
    FragPostFree fragPostFree;
    FragPostSos fragPostSos;
    FragPostSecret fragPostSecret;
    LinearLayout contentLayout, btn_post_free_layout, btn_post_sos_layout, btn_post_secret_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        btn_post_free_layout = (LinearLayout) findViewById(R.id.btn_post_free_layout);
        btn_post_sos_layout = (LinearLayout) findViewById(R.id.btn_post_sos_layout);
        btn_post_secret_layout = (LinearLayout) findViewById(R.id.btn_post_secret_layout);
        contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

        /*
         *프래그먼트
         */
        fragmentManager = getSupportFragmentManager();
        // 프래그먼트 객체 만들기
        fragPostFree = new FragPostFree();
        fragPostSos = new FragPostSos();
        fragPostSecret = new FragPostSecret();

        // 프래그먼트 첫 화면 로딩
        btn_post_free_layout.setBackgroundResource(R.drawable.part_round_back);
        btn_post_sos_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
        btn_post_secret_layout.setBackgroundColor(Color.parseColor("#0000ff00"));

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.contentLayout, fragPostFree);
        ft.commit();

        btn_post_free_layout.setOnClickListener(onClickListener);
        btn_post_sos_layout.setOnClickListener(onClickListener);
        btn_post_secret_layout.setOnClickListener(onClickListener);

        // footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_search).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_home).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_map).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_my_info).setOnClickListener(onFooterlistner);
        //postUpdate();


        ImageView myInfoProfile = findViewById(R.id.img_my_info);   // 프로필
        TextView myInfoText = findViewById(R.id.tv_my_info);     // 닉네임


        if (firebaseUser == null) { // 로그인 상태가 아닐때
            Toast.makeText(getApplicationContext(), "로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        } else {  // 로그인 상태일때
            myInfoProfile.setImageResource(R.drawable.ic_account_circle_black_24dp);
            myInfoText.setText("내정보");
        }

    }   // end of oncreate

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            FragmentTransaction ft = fragmentManager.beginTransaction();
            switch (v.getId()) {
                case R.id.btn_post_free_layout:
                    Log.e("버튼 : ","버튼 클릭 ");
                    btn_post_free_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_post_sos_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_post_secret_layout.setBackgroundColor(Color.parseColor("#0000ff00"));


                    ft = fragmentManager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.contentLayout, fragPostFree);
                    ft.commit();
                    break;

                case R.id.btn_post_sos_layout:
                    btn_post_sos_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_post_free_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_post_secret_layout.setBackgroundColor(Color.parseColor("#0000ff00"));

                    ft = fragmentManager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.contentLayout, fragPostSos);
                    ft.commit();
                    break;

                case R.id.btn_post_secret_layout:
                    btn_post_secret_layout.setBackgroundResource(R.drawable.part_round_back);
                    btn_post_free_layout.setBackgroundColor(Color.parseColor("#0000ff00"));
                    btn_post_sos_layout.setBackgroundColor(Color.parseColor("#0000ff00"));

                    ft = fragmentManager.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.contentLayout, fragPostSecret);
                    ft.commit();
                    break;

            }
        }
    };





    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);
    }


    private void myStartActivity(Class c,PostInfo postInfo) {
        Intent intent = new Intent(this, c);
        intent.putExtra("postInfo",postInfo);
        startActivity(intent);


    }

}
