package com.example.addcul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.ChatInfo;
import com.example.addcul.MemberInfo;
import com.example.addcul.R;
import com.example.addcul.Util;
import com.example.addcul.adapter.ChatAdapter;
import com.example.addcul.listener.OnPostListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class ReadChatActivity extends BasicActivity {

    private static final String TAG = "ReadChatActivity";

    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.Adapter chatAdapter;
    private ArrayList<ChatInfo> chatList;
    private ArrayList<MemberInfo>memberInfos;
    private Util util;
    private RelativeLayout loaderLayout;
    private ArrayList<MemberInfo>testArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_chat);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        if(firebaseUser == null){
            util.showToast("로그인 후 이용가능 합니다.");
            myStartActivity(LoginActivity.class);
        }
        else {

            util = new Util(this);
            chatList = new ArrayList<>();
            memberInfos = new ArrayList<>();
            chatAdapter = new ChatAdapter(ReadChatActivity.this, chatList, memberInfos);
            ((ChatAdapter) chatAdapter).setOnPostListener(onChatListener);

            testArray = new ArrayList<>();

            //Log.e("cxchat : ",testArray.get(0).getName());

            loaderLayout = findViewById(R.id.loaderLayout);
            RecyclerView recyclerView = findViewById(R.id.recyclerView_chat);
            findViewById(R.id.img_chat_send).setOnClickListener(onClickListener);
           // findViewById(R.id.btn_refresh).setOnClickListener(onClickListener);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(ReadChatActivity.this));
            recyclerView.setAdapter(chatAdapter);
            Log.e("어뎁터", "어뎁터");


            // 멤버 데이터 불러오기
            getName();

            if(memberInfos.size()!=0)
                Log.e("GetName : ",testArray.get(0).getName());



            //Log.e("GetName : ",testArray.get(0).getName());
            //Log.e("GetName : ",testArray.get(1).getName());
            //Log.e("GetName : ",testArray.get(2).getName());

        }
    }

    protected void onResume() {
        super.onResume();
        chatUpdate();

    }

    OnPostListener onChatListener = new OnPostListener() {
        @Override
        public void onDelete(int position) {
            final String id = chatList.get(position).getId();
            firebaseFirestore.collection("chats").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            util.showToast("게시글을 삭제하였습니다.");
                            chatUpdate();
                            Log.e("게시글 삭제 : ", id);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            util.showToast("게시글을 삭제하지 못하였습니다.");

                        }
                    });
                    getName();

  ;

        }



        @Override
        public void onModify(int position) {
            myStartActivity(WriteChatActivity.class, chatList.get(position));
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
              /*  case R.id.logoutButton:
                    FirebaseAuth.getInstance().signOut();
                    myStartActivity(SignUpActivity.class);
                    finish();
                    break;

               */
                case R.id.img_chat_send:
                    storageUpload();
                    break;


            }
        }
    };




    private void chatUpdate() {
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("chats");
            collectionReference
                    .orderBy("created", Query.Direction.ASCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                chatList.clear();

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    chatList.add(new ChatInfo(
                                            document.getData().get("text").toString(),
                                            document.getData().get("publisher").toString(),
                                            new Date(document.getDate("created").getTime()),
                                            document.getId())); // 문서 id
                                            //getName();


                                }

                                chatAdapter.notifyDataSetChanged();

                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }
    }

    private void getName() {
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("users");
            collectionReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Log.e("cxupdate : ", "채팅은될까 1");
                                memberInfos.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    Log.e("cxupdate : ", "채팅은될까 2");
                                   memberInfos.add(new MemberInfo(
                                           document.getData().get("name").toString(),
                                           document.getData().get("uid").toString()));
                                }
                                chatAdapter.notifyDataSetChanged();
                                testArray = memberInfos;



                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }

                            //Log.e("GetName : ",testArray.get(0).getName());
                            //Log.e("GetName : ",testArray.get(1).getName());
                            //Log.e("GetName : ",testArray.get(2).getName());
                        }

                    });

        }

    }


    // 파이어스토어 업로드
    private void storageUpload() {
        final String text = ((EditText) findViewById(R.id.et_chat_text)).getText().toString();

        if (text.length() > 0) {
            //loaderLayout.setVisibility(View.VISIBLE);
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // UID
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            ChatInfo chatInfo = (ChatInfo) getIntent().getSerializableExtra("chatInfo");
            // final DocumentReference documentReference = firebaseFirestore.collection("chats").document();
            final DocumentReference documentReference = chatInfo == null ? firebaseFirestore.collection("chats").document() : firebaseFirestore.collection("chats").document(chatInfo.getId());
            final Date date = chatInfo == null ? new Date() : chatInfo.getCreated();


            chatInfo = new ChatInfo(text, firebaseUser.getUid(), date);
            storeUploader(documentReference, chatInfo);
        } else {
            util.showToast("내용을 입력해주세요.");

        }

    }

    private void storeUploader(DocumentReference documentReference, ChatInfo chatInfo) {
        final Util util = new Util(this);
        documentReference.set(chatInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    //    loaderLayout.setVisibility(View.GONE);
                        util.showToast("전송완료");
                        myStartActivity(ReadChatActivity.class);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       //
                        // loaderLayout.setVisibility(View.GONE);
                        util.showToast("전송실패");

                    }
                });


    }


    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    private void myStartActivity(Class c, ChatInfo chatInfo) {
        Intent intent = new Intent(this, c);
        intent.putExtra("chatInfo", chatInfo);
        startActivity(intent);
    }

}
