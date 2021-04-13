package com.example.addcul.activity;

import android.os.Bundle;

import com.example.addcul.R;


// 모든 액티비티의 설정 액티비티

public class ProblemActivity extends BasicActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_problem);

        findViewById(R.id.img_home).setOnClickListener(onFootlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFootlistner);
        findViewById(R.id.img_map).setOnClickListener(onFootlistner);
        // 로그인
        findViewById(R.id.img_my_info).setOnClickListener(onFootlistner);
        findViewById(R.id.tv_my_info).setOnClickListener(onFootlistner);





    }

}
