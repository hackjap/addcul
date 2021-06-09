package com.jsp.addcul.activity.config;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jsp.addcul.R;
import com.jsp.addcul.MainActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        IntroTread introTread = new IntroTread(handler);
        introTread.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==1){
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            };
        }
    };
}
