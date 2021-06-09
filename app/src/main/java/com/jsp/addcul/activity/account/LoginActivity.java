package com.jsp.addcul.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jsp.addcul.R;
import com.jsp.addcul.MainActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "SignUpActivity";
    // 구글
    private SignInButton btn_google;    // 구글 로그인 버튼
    private FirebaseAuth mAuth;  // 파이어 베이스 인증 객체
    private GoogleApiClient googleApiClient;    // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();


        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        findViewById(R.id.btn_google).setOnClickListener(onClickListener);
        findViewById(R.id.btn_login).setOnClickListener(onClickListener);   // 로그인
        findViewById(R.id.tv_pw_reset).setOnClickListener(onClickListener); // 비밀번호 찾기
        findViewById(R.id.tv_signup).setOnClickListener(onClickListener);  // 회원가입

        TextView textView;



        // 메인에 넣어줄 계정 uid


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
                case R.id.tv_pw_reset: // 비밀번호 재설정
                    myStartActivity(PasswordResetActivity.class);
                    break;
                case R.id.tv_signup:
                    myStartActivity(SignUpActivity.class);
                    break;

                case R.id.btn_google:
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(intent,REQ_SIGN_GOOGLE); // 구글에서 인증 후 결과 값을 받아옴
            }

        }
    };

    // 구글 로그인 인증을 요청 했을 때 결과 값을 되뎔라 받는 곳 .
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_SIGN_GOOGLE){
            GoogleSignInResult  result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){ // 인증결과가 성공적이면..
                GoogleSignInAccount account = result.getSignInAccount(); // account라는 데이터는 구글로그인 정보를 담고있습니다. ( 닉네임,프로필사진,Url,이메일주소 .. 등 )
                 resultLogin(account); // 로그인 결과 값 출력 수행하라는 메소드
            }
        }
    }

    private void resultLogin(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ // 로그인이 성공했으면...
                            startToast("구글 로그인성공 ");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("nickName",account.getDisplayName());
                            intent.putExtra("photoUrl",String.valueOf(account.getPhotoUrl())); // String.valueOf() 특정 자료형을 String 형태로 변환 .
                            startActivity(intent);
                        }else{
                                startToast("로그인 실패 ");
                        }
                    }
                });
    }

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
                                getUid();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("uid",uid);
                                startActivity(intent);

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


    private void getUid() {
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("users");
            collectionReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    uid = document.getData().get("uid").toString();
                                }
                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
} //end

