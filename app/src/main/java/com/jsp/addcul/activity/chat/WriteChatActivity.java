package com.jsp.addcul.activity.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jsp.addcul.R;
import com.jsp.addcul.activity.config.BasicActivity;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class WriteChatActivity extends BasicActivity {
    private static final String TAG = "WriteChatActivity";
    private FirebaseUser user;
    private ArrayList<String> pathList = new ArrayList<>();
    LinearLayout parent;
    private RelativeLayout buttonsBackgroundLayout;
    private RelativeLayout loaderLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.ac);
        //parent = findViewById(R.id.contentsLayout_chat);
        //buttonsBackgroundLayout = findViewById(R.id.buttonsBackgrundLayout_chat);
        loaderLayout= findViewById(R.id.loaderLayout);
        findViewById(R.id.img_chat_send).setOnClickListener(onClickListener);   // 등록하기 버튼


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {


                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    ImageView imageView = new ImageView(WriteChatActivity.this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonsBackgroundLayout.setVisibility(View.VISIBLE);
                        }
                    });

                    EditText editText = new EditText(WriteChatActivity.this);
                    editText.setLayoutParams(layoutParams);
                    editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE|InputType.TYPE_CLASS_TEXT);
                    editText.setHint("내용");
                    parent.addView(editText);

                }
                break;

            }
        }
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_chat_send:
                    //storageUpload();
                    break;
                case R.id.buttonsBackgrundLayout:
                    if(buttonsBackgroundLayout.getVisibility()==View.VISIBLE){
                        buttonsBackgroundLayout.setVisibility(View.GONE);
                    }
            }
        }
    };



    private void myStartActivity(Class c, String media) {
        Intent intent = new Intent(this, c);
        intent.putExtra("media", media);
        startActivityForResult(intent, 0);
    }


    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }



    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
