package com.example.addcul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.addcul.PasswordResetActivity;
import com.example.addcul.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_login).setOnClickListener(onClickListener);
        findViewById(R.id.btn_pw_reset).setOnClickListener(onClickListener);
        findViewById(R.id.btn_signup).setOnClickListener(onClickListener);


    }


    private void updateUI(FirebaseUser currentUser) {
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    login();
                    break;
                case R.id.btn_pw_reset: // 비밀번호 재설정
                    myStartActivity(PasswordResetActivity.class);
                    break;
                case R.id.btn_signup:
                    myStartActivity(SignUpActivity.class);
                    break;
            }
        }
    };

    private void login() {
        String id = ((EditText) findViewById(R.id.et_id)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_pw)).getText().toString();

        // 비밀번호 규칙 - 추후 정규표현식 사용
        Boolean passRule =id.length() > 0 && password.length() > 0;

        if (passRule) {
            mAuth.signInWithEmailAndPassword(id, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인에 성공했습니다");
                                // 로그인 시, 하단메뉴 내정보로 변경

                                myStartActivity(MainActivity.class);

                            } else {
                                if (task.getException() != null) { // 비밀번호가 null 값이 아닐때
                                    startToast(task.getException().toString());
                                    startToast("로그인실패");
                                }
                            }

                        }
                    });

        } else {
            startToast("이메일 또는 비밀번호를 입력해주세요!!");
        }
    }// end of signup()

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
} //end

