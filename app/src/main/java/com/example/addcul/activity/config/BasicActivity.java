package com.example.addcul.activity.config;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addcul.MainActivity;
import com.example.addcul.R;
import com.example.addcul.Util.Util;
import com.example.addcul.activity.account.LoginActivity;
import com.example.addcul.activity.account.MyInfoActivity;
import com.example.addcul.activity.googlemap.MapActivity;
import com.example.addcul.activity.translate.TranslationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


// 모든 액티비티의 설정 액티비티

public class BasicActivity extends AppCompatActivity {

    Util util;
    int flag = 0;
    public FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 세로모드 보기 고정

        util = new Util(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    }
    public View.OnClickListener onFooterlistner = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //하단메뉴
            switch (v.getId()) {
                case R.id.img_home: {
                    startActivity(MainActivity.class);
                    break;
                }
                case R.id.img_map:
                    startActivity(MapActivity.class);
                    break;
                case R.id.img_search:
                    Toast.makeText(getApplicationContext(),"곧 기능을 출시할 예정이에요.",Toast.LENGTH_LONG).show();
                    break;
                case R.id.img_translate:
                    startActivity(TranslationActivity.class);
                    break;
                case R.id.img_my_info:
                    if (firebaseUser != null) {
                        startActivity(MyInfoActivity.class);
                    } else {
                        startActivity(LoginActivity.class);
                    }
                    break;
            } // end of switch

        }
    };

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}