package com.jsp.addcul.activity.config;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jsp.addcul.MainActivity;
import com.jsp.addcul.R;
import com.jsp.addcul.Util.Util;
import com.jsp.addcul.activity.TmpActivity;
import com.jsp.addcul.activity.account.LoginActivity;
import com.jsp.addcul.activity.account.MyInfoActivity;
import com.jsp.addcul.activity.googlemap.MapActivity;
import com.jsp.addcul.activity.translate.TranslationActivity;
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
                    Toast.makeText(getApplicationContext(),"구글 검색페이지로 이동합니다.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                    startActivity(intent);
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