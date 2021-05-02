package com.example.addcul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.addcul.MemberInfo;
import com.example.addcul.R;
import com.example.addcul.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyInfoActivity extends AppCompatActivity {

    private static final String TAG = "MyInfoActivity";

    Util util;
    ImageView myInfoProfile;
    TextView myInfoNickname;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<MemberInfo> memberInfos;
    private ArrayList<MemberInfo>memberData;
    String currentUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        util = new Util(this);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        memberInfos = new ArrayList<>();
        memberData = new ArrayList<>();

        // 로그아웃
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(MainActivity.class);
                Toast.makeText(getApplicationContext(),"로그아웃 하였습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        if(firebaseUser == null) { // 로그인 상태가 아닐때
            util.showToast("로그인해주세요");
            startActivity(LoginActivity.class);
        }

        else {

            myInfoProfile = findViewById(R.id.img_myinfo_profile);
            myInfoNickname = findViewById(R.id.tv_myinfo_nickname);
            currentUid = firebaseUser.getUid();  //현재 로그인 UID

            memberUpdate();

            //myInfoNickname.setText(memberData.get(0).getName());
            Log.e("cxmember22",memberInfos.get(0).getName());
            Log.e("cxupdate : ", " 도큐먼트 그전까지는되네-1");

           // int position = 0;

            //for (int index = 0; index <1; index++) {
               // if (currentUid.equals(memberInfos.get(index).getUid()))
                //   position = index;
          //  Log.e("cxfor","For문 안 ");
       // }
            //Log.e("member  : ",memberInfos.get(0).getUid());
           // Log.e("cxUID : ",currentUid);
         //   Log.e("cxname : ",memberInfos.get(0).getName());
//            myInfoNickname.setText(memberInfos.get(0).getName().toString());

            //Log.e("nickname : ", memberInfos.get(0).getName());
        /* 구글 로그인
        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickname");
        String profile = intent.getStringExtra("profile");

       // Log.e("nickname : ",nickname);
       // Log.e("profile : ",profile);

        Log.e("nickname : ",memberInfos.get(0).getName());

        myInfoNickname.setText(memberInfos.get(0).getName());
        String url = "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg";
        Glide.with(this).load(url).into(myInfoProfile);

        Log.e("url : ",url);


         */
        }


    }

    private void memberUpdate() {
        if(firebaseUser!=null){
            CollectionReference collectionReference = firebaseFirestore.collection("users");
            collectionReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                memberInfos.clear();
                                Log.e("cxupdate : ", " 도큐먼트 그전까지는되네1");
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    Log.e("cxupdate : ", " 도큐먼트 그전까지는되네2");
                                   memberInfos.add(new MemberInfo(
                                            document.getData().get("email").toString(),
                                            document.getData().get("name").toString(),
                                            document.getData().get("sex").toString(),
                                            document.getData().get("phoneNumber").toString(),
                                            document.getData().get("birthDay").toString(),
                                            document.getData().get("uid").toString()));

                                            //memberData = memberInfos;

                                            Log.e("cxupdate : ",document.get("uid").toString());
                                            //Log.e("cxmember",memberInfos.get(3).getName());
                                }
                                Log.e("cxFFmember",memberInfos.get(3).getName());
                                // chatAdapter.notifyDataSetChanged();

                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }
    }

    public void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
