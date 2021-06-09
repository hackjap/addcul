package com.jsp.addcul.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.jsp.addcul.DTO.MemberInfo;
import com.jsp.addcul.R;
import com.jsp.addcul.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

// 회원가입 액티비티

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG ="SignUpActivity";
    private FirebaseAuth mAuth;
    private RelativeLayout loaderLayout;
    private FirebaseUser user;
    private MemberInfo memberInfo;
    private String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_sign).setOnClickListener(onClickListener);
        loaderLayout = findViewById(R.id.loaderLayout);

        RadioGroup rg = findViewById(R.id.radioGroup);

        rg.setOnCheckedChangeListener(onCheckedChangeListener);

    }


    // 뒤로가기눌러서 앱종료
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(1);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_sign:
                        signUp();
                        break;

            }
        }
    };
    private void signUp(){
        final String id = ((EditText)findViewById(R.id.et_id)).getText().toString();
        String password = ((EditText)findViewById(R.id.et_pw)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.et_pw_check)).getText().toString();
        final String name = ((EditText)findViewById(R.id.et_name)).getText().toString();
        final String birth = ((EditText)findViewById(R.id.et_birth)).getText().toString();
        final String phoneNum = ((EditText)findViewById(R.id.et_phone_num)).getText().toString();





        boolean signUpRule = id.length() > 0 && passwordCheck.length()>0 && name.length() > 0 && birth.length()>0 && phoneNum.length() > 0 ;

        if(signUpRule) {
            if (password.equals(passwordCheck)) { // 비밀번호 유효성 검사
                mAuth.createUserWithEmailAndPassword(id, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                     user = FirebaseAuth.getInstance().getCurrentUser();
                                     memberInfo = new MemberInfo(id,name,sex,phoneNum,birth,user.getUid());
                                    // 회원정보 firestore로 보내기
                                    storeUploader(memberInfo);
                                    //loaderLayout.setVisibility(View.VISIBLE);
                                    startToast("회원가입에 성공했습니다");
                                    Log.e("가입성공 : ",memberInfo.getName());
                                    myStartActivity(MainActivity.class);


                                } else {
                                    if (task.getException() != null) // 비밀번호가 null 값이 아닐때
                                        startToast(task.getException().toString());
                                }

                                // ...
                            }
                        });
            } else {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

            }
        }else {
            startToast("빈칸 없이 모두 작성해주세요!!");
        }





    }// end of signup()

    private void storeUploader(final MemberInfo memberInfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser(); // 사용자 UID

        db.collection("users").document(user.getUid()).set(memberInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startToast("회원정보 등록을 성공하였습니다.");
                        //loaderLayout.setVisibility(View.GONE);
                        Log.e("가입성공 : ", memberInfo.getName());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("회원정보 등록에 실패하였습니다.");
                      //  loaderLayout.setVisibility(View.GONE);
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void startToast(String msg){
        Toast.makeText(this, msg,  Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.rb_man){
                sex = "남자";
            }else if(checkedId == R.id.rb_woman){
                sex = "여자";
            }
        }
    };

} //end

