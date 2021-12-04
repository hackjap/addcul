package com.jsp.addcul.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsp.addcul.R;
import com.jsp.addcul.activity.chat.IndexActivity;
import com.jsp.addcul.activity.chat.IndexActivity2;
import com.jsp.addcul.activity.chat.IndexActivity3;
import com.jsp.addcul.activity.chat.IndexActivity4;
import com.jsp.addcul.activity.chat.IndexActivity5;
import com.jsp.addcul.activity.chat.IndexActivity6;

public class ChatList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);


        findViewById(R.id.chat_kr).setOnClickListener(onClickListener);
        findViewById(R.id.chat_tai).setOnClickListener(onClickListener);
        findViewById(R.id.chat_in).setOnClickListener(onClickListener);
        findViewById(R.id.chat_vat).setOnClickListener(onClickListener);
        findViewById(R.id.chat_en).setOnClickListener(onClickListener);
        findViewById(R.id.chat_etc).setOnClickListener(onClickListener);





    }
    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            /*
             *  중간메뉴
             */
            switch (v.getId()) {
                // SOS
                case R.id.chat_kr:
                    startActivity(IndexActivity.class);
                    break;
                case R.id.chat_tai:
                    startActivity(IndexActivity2.class);
                    break;
                // 언어교환
                case R.id.chat_in:
                    startActivity(IndexActivity3.class);
                    break;
                case R.id.chat_vat:
                    startActivity(IndexActivity4.class);
                    break;
                case R.id.chat_en:
                    startActivity(IndexActivity5.class);
                    break;
                case R.id.chat_etc:
                    startActivity(IndexActivity6.class);
                    break;
            }
        }
    };

}
