package com.example.addcul.activity.account;

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

import com.example.addcul.DTO.MemberInfo;
import com.example.addcul.DTO.PostInfo;
import com.example.addcul.R;
import com.example.addcul.Util.Util;
import com.example.addcul.activity.config.BasicActivity;
import com.example.addcul.activity.MainActivity;
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
    TextView myInfoNickname, myInfoEmail, myInfopNum, myInfoSex, myInfoBirth;
    ImageView freeLogProgress,sosLogProgress,secretLogProgress;
    ArrayList<PostInfo> postList;
    ArrayList<PostInfo> myPostList;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<MemberInfo> memberInfos;
    String currentUid;
    int flagFree = 0, flagSos = 0, flagSecret = 0;
    // 댓글기록
    RecyclerView recyclerViewFree,recyclerViewSos,recyclerViewSecret;
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
        myPostList = new ArrayList<>();

        // 활동 로그 리사이클러뷰
        recyclerViewFree = findViewById(R.id.rv_myinfo_free);
        recyclerViewSos = findViewById(R.id.rv_myinfo_sos);
        recyclerViewSecret = findViewById(R.id.rv_myinfo_secret);
        freeLogProgress = findViewById(R.id.freeLogProgress);
        sosLogProgress = findViewById(R.id.sosLogProgress);
        secretLogProgress = findViewById(R.id.secretLogProgress);


        // 활동 로그 버튼
        findViewById(R.id.mylog_free_btn).setOnClickListener(onClickListener);
        findViewById(R.id.mylog_sos_btn).setOnClickListener(onClickListener);
        findViewById(R.id.mylog_secret_btn).setOnClickListener(onClickListener);

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
            TextView tvMyinfo = (TextView) findViewById(R.id.tv_my_info);
            ImageView ivMyinfo = (ImageView) findViewById(R.id.img_my_info);
            ivMyinfo.setImageResource(R.drawable.ic_lock_open_black_24dp);
            tvMyinfo.setText("로그인");
        } else {
            TextView tvMyinfo = (TextView) findViewById(R.id.tv_my_info);
            ImageView ivMyinfo = (ImageView) findViewById(R.id.img_my_info);
            ivMyinfo.setImageResource(R.drawable.ic_account_circle_yellow_24dp);
            tvMyinfo.setText("내정보");

            //getPostData(actID);


            memberUpdate();

        }


    }

    private void setRecyclerView(RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyInfoActivity.this));
        postMyLogAdapter = new PostMyLogAdapter(this, myPostList, memberInfos);
        // ((PostDetailAdapter)postDetailAdapter).setOnPostListener(onPostListener);
        recyclerView.setAdapter(postMyLogAdapter);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mylog_free_btn: {
                    getPostData(recyclerViewFree,"free");
                    if (flagFree == 0) {
                        recyclerViewFree.setVisibility(View.VISIBLE);
                        freeLogProgress.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                        flagFree = 1;
                    } else {
                        recyclerViewFree.setVisibility(View.GONE);
                        freeLogProgress.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);

                        flagFree = 0;
                    }
                    break;
                }
                case R.id.mylog_sos_btn:{
                    getPostData(recyclerViewSos,"sos");

                    if (flagSos == 0) {
                        recyclerViewSos.setVisibility(View.VISIBLE);
                        sosLogProgress.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                        flagSos = 1;
                    } else {
                        recyclerViewSos.setVisibility(View.GONE);
                        sosLogProgress.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                        flagSos = 0;
                    }
                    break;
                }
                case R.id.mylog_secret_btn:{
                    getPostData(recyclerViewSecret,"secret");

                    if (flagSecret == 0) {
                        recyclerViewSecret.setVisibility(View.VISIBLE);
                        secretLogProgress.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                        flagSecret = 1;
                    } else {
                        recyclerViewSecret.setVisibility(View.GONE);
                        secretLogProgress.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                        flagSecret = 0;
                    }
                    break;
                }

            }
        }
    };

    private void getPostData(final RecyclerView recyclerView, final String actID) {   // post(게시판) 객체를 가져와 상세페이지를 띄어주는 함수
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("posts_" + actID);
            Log.e("CXXPOSTACT", "posts_" + actID);
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

                                // 게시판 로그 활동만 가져오기
                                String uid = firebaseUser.getUid();
                                myPostList.clear();
                                Log.e("CXXLOG", postList.size()+"");
                                for (int i = 0; i < postList.size(); i++) {
                                    if (uid.equals(postList.get(i).getPublisher())) {
                                        myPostList.add(postList.get(i));
                                    }
                                }
                                Log.e("CXXLOG", myPostList.size()+"");


//                                postMyLogAdapter.notifyDataSetChanged();
                                Date date = new Date();
                                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String updated = transFormat.format(date);

                                setRecyclerView(recyclerView);


                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    private void memberUpdate() {
        if (firebaseUser != null) {
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

                                // 등록정보 set 하기
                                registerInfo();

//

                                Log.e("cxFFmember", memberInfos.get(3).getName());

                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }
    }

    void registerInfo() {
        int position = 0;
        for (int index = 0; index < memberInfos.size(); index++) {
            if (currentUid.equals(memberInfos.get(index).getUid()))
                position = index;
            Log.e("position : ", position + "");
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
