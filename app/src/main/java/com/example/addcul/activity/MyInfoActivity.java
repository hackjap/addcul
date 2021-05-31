package com.example.addcul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.MemberInfo;
import com.example.addcul.PostInfo;
import com.example.addcul.R;
import com.example.addcul.Util;
import com.example.addcul.adapter.PostMyLogAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyInfoActivity extends BasicActivity {


    private static final String TAG = "MyInfoActivity";

    Util util;
    CircleImageView myInfoProfile;
    TextView myInfoNickname,myInfoEmail,myInfopNum,myInfoSex,myInfoBirth;
    ArrayList<PostInfo> postList;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<MemberInfo> memberInfos;
    String currentUid;
    int flagFree=0;
    // 댓글기록
    RecyclerView recyclerViewFree;
    PostMyLogAdapter postMyLogAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        util = new Util(this);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        memberInfos = new ArrayList<>();
        postList = new ArrayList<>();

        recyclerViewFree = findViewById(R.id.rv_myinfo_free);

        // 활동 로그 버튼
        findViewById(R.id.mylog_free_btn).setOnClickListener(onClickListener);

        // footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_home).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_map).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_my_info).setOnClickListener(onFooterlistner);

        myInfoProfile = findViewById(R.id.img_myInfo_profile);
        myInfoNickname = findViewById(R.id.tv_myinfo_nickname);
        myInfoEmail = findViewById(R.id.tv_myinfo_email);
        myInfoBirth = findViewById(R.id.tv_myinfo_birth);
        myInfoSex = findViewById(R.id.tv_myinfo_sex);
        myInfopNum = findViewById(R.id.tv_myinfo_pnum);

        currentUid = firebaseUser.getUid();  //현재 로그인 UID

        String actID = "free";
        getPostData(actID);


        recyclerViewFree.setHasFixedSize(true);
        recyclerViewFree.setLayoutManager(new LinearLayoutManager(MyInfoActivity.this));
        postMyLogAdapter = new PostMyLogAdapter(this,postList,memberInfos);
       // ((PostDetailAdapter)postDetailAdapter).setOnPostListener(onPostListener);
        recyclerViewFree.setAdapter(postMyLogAdapter);

        // 로그아웃
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(MainActivity.class);
                Toast.makeText(getApplicationContext(), "로그아웃 하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        if (firebaseUser == null) { // 로그인 상태가 아닐때
            util.showToast("로그인해주세요");
            startActivity(LoginActivity.class);
            TextView tvMyinfo = (TextView)findViewById(R.id.tv_my_info);
            ImageView ivMyinfo = (ImageView) findViewById(R.id.img_my_info);
            ivMyinfo.setImageResource(R.drawable.ic_lock_open_black_24dp);
            tvMyinfo.setText("로그인");
        } else {
            TextView tvMyinfo = (TextView)findViewById(R.id.tv_my_info);
            ImageView ivMyinfo = (ImageView) findViewById(R.id.img_my_info);
            ivMyinfo.setImageResource(R.drawable.ic_account_circle_yellow_24dp);
            tvMyinfo.setText("내정보");
            memberUpdate();

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


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mylog_free_btn:{
                    if(flagFree==0){
                        recyclerViewFree.setVisibility(View.VISIBLE);
                        flagFree = 1;
                    }
                    else{
                        recyclerViewFree.setVisibility(View.GONE);
                        flagFree =0;
                    }
                }

            }
        }
    };

    private void getPostData(String actID) {   // post(게시판) 객체를 가져와 상세페이지를 띄어주는 함수
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("posts_"+ actID);
            Log.e("CXXPOSTACT","posts_"+actID);
            collectionReference
                    .orderBy("createdAt", Query.Direction.DESCENDING).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                postList.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    postList.add(new PostInfo(
                                            document.getData().get("title").toString(),
                                            document.getData().get("contents").toString(),
                                            document.getData().get("publisher").toString(),
                                            new Date(document.getDate("createdAt").getTime()),
                                            document.getId()));

                                }
                                postMyLogAdapter.notifyDataSetChanged();
                                Date date = new Date();
                                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String updated = transFormat.format(date);
//
//                                postTitle.setText(postList.get(position).getTitle());
//                                postDetail.setText(postList.get(position).getContents());
//                                //postName.setText(postList.get(position).getPublisher());
//                                publisher = postList.get(position).getPublisher();
//                                postUpdated.setText(updated);

//                                getUserName();
//                                comentUpdate(postList);
//                                Log.e("CXX! :",postList.get(0).getId());
                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
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


                                }

                                // 동작
                                registerInfo();

//

                                Log.e("cxFFmember",memberInfos.get(3).getName());

                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }
    }
    void registerInfo(){
        int position = 0;
        for (int index = 0; index <memberInfos.size(); index++) {
            if (currentUid.equals(memberInfos.get(index).getUid()))
                position = index;
            Log.e("position : ",position+"");
        }
        myInfoNickname.setText(memberInfos.get(position).getName());
        myInfoEmail.setText(memberInfos.get(position).getEmail());
        myInfoSex.setText(memberInfos.get(position).getSex());
        myInfopNum.setText(memberInfos.get(position).getPhoneNumber());
        myInfoBirth.setText(memberInfos.get(position).getBirthDay());
    }

    public void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
