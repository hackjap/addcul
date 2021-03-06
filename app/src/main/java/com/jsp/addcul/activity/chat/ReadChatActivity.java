package com.jsp.addcul.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jsp.addcul.DTO.ChatInfo;
import com.jsp.addcul.DTO.MemberInfo;
import com.jsp.addcul.R;
import com.jsp.addcul.Util.Util;
import com.jsp.addcul.activity.config.BasicActivity;
import com.jsp.addcul.activity.account.LoginActivity;
import com.jsp.addcul.adapter.ChatAdapter;
import com.jsp.addcul.listener.OnPostListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = database.child("text");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_chat);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        // footer ?????????
        // ????????????
        findViewById(R.id.img_home).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_map).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_my_info).setOnClickListener(onFooterlistner);

        if(firebaseUser == null){
          //  util.showToast("????????? ??? ???????????? ?????????.");
            Toast.makeText(getApplicationContext(),"????????? ??? ???????????? ?????????.",Toast.LENGTH_SHORT).show();
            myStartActivity(LoginActivity.class);

        }
        else {
            util = new Util(this);
            chatList = new ArrayList<>();
            memberInfos = new ArrayList<>();
            chatAdapter = new ChatAdapter(ReadChatActivity.this, chatList, memberInfos);
            ((ChatAdapter) chatAdapter).setOnPostListener(onChatListener);

            testArray = new ArrayList<>();

            TextView tvMyinfo = (TextView)findViewById(R.id.tv_my_info);
            ImageView ivMyinfo = (ImageView) findViewById(R.id.img_my_info);
            ivMyinfo.setImageResource(R.drawable.ic_account_circle_black_24dp);
            tvMyinfo.setText("?????????");

            loaderLayout = findViewById(R.id.loaderLayout);
            RecyclerView recyclerView = findViewById(R.id.recyclerView_chat);
            findViewById(R.id.img_chat_send).setOnClickListener(onClickListener);
           // findViewById(R.id.btn_refresh).setOnClickListener(onClickListener);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(ReadChatActivity.this));
            recyclerView.setAdapter(chatAdapter);
            Log.e("?????????", "?????????");


            // ?????? ????????? ????????????
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
                            util.showToast("???????????? ?????????????????????.");
                            chatUpdate();
                            Log.e("????????? ?????? : ", id);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            util.showToast("???????????? ???????????? ??????????????????.");

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
                    writeChat();
                    break;


            }
        }
    };

    private void writeChat(){

        conditionRef.setValue("writechat");

    }



    private void realChatUpdate() {
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
                                            document.getId())); // ?????? id
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
                                            document.getId())); // ?????? id
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
                                Log.e("cxupdate : ", "??????????????? 1");
                                memberInfos.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    Log.e("cxupdate : ", "??????????????? 2");
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


    // ?????????????????? ?????????
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
            util.showToast("????????? ??????????????????.");

        }

    }

    private void storeUploader(DocumentReference documentReference, ChatInfo chatInfo) {
        final Util util = new Util(this);
        documentReference.set(chatInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    //    loaderLayout.setVisibility(View.GONE);
                        util.showToast("????????????");
                        myStartActivity(ReadChatActivity.class);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       //
                        // loaderLayout.setVisibility(View.GONE);
                        util.showToast("????????????");

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
