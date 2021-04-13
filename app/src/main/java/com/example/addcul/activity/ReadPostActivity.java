package com.example.addcul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.PostInfo;
import com.example.addcul.R;
import com.example.addcul.Util;
import com.example.addcul.adapter.MainAdapter;
import com.example.addcul.listener.OnPostListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class ReadPostActivity extends BasicActivity {

    private static final String TAG = "ReadPostActivity";

    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView.Adapter mainAdapter;
    private ArrayList<PostInfo> postList;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();





        util = new Util(this);
        postList = new ArrayList<>();
        mainAdapter = new MainAdapter(ReadPostActivity.this,postList);
        ((MainAdapter)mainAdapter).setOnPostListener(onPostListener);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        findViewById(R.id.goto_post_write).setOnClickListener(onClickListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReadPostActivity.this));
        //recyclerView.setAdapter(new MainAdapter(ReadPostActivity.this,postList));
        recyclerView.setAdapter(mainAdapter);
        Log.e("어뎁터","어뎁터");

        // footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_home).setOnClickListener(onFootlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFootlistner);
        findViewById(R.id.img_map).setOnClickListener(onFootlistner);
        // 로그인
        findViewById(R.id.img_my_info).setOnClickListener(onFootlistner);
        findViewById(R.id.tv_my_info).setOnClickListener(onFootlistner);

    }




    protected void onResume() {
        super.onResume();
        postUpdate();
    }

    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete(int position) {
           final String id = postList.get(position).getId();
            firebaseFirestore.collection("posts").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            util.showToast("게시글을 삭제하였습니다.");
                            postUpdate();
                            Log.e("포스트 게시물 삭제 : ",id);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            util.showToast("게시글을 삭제하지 못하였습니다.");
                        }
                    });


        }

        @Override
        public void onModify(int position) {
            myStartActivity(WritePostActivity.class, postList.get(position));
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
                case R.id.goto_post_write:
                    myStartActivity(WritePostActivity.class);
                    break;
            }
        }
    };
    private void postUpdate(){
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("posts");
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
                                mainAdapter.notifyDataSetChanged();

                            } else {
                              //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);
    }


    private void myStartActivity(Class c,PostInfo postInfo) {
        Intent intent = new Intent(this, c);
        intent.putExtra("postInfo",postInfo);
        startActivity(intent);
    }

}
