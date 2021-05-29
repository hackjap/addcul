package com.example.addcul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.MemberInfo;
import com.example.addcul.PostDetailInfo;
import com.example.addcul.PostInfo;
import com.example.addcul.R;
import com.example.addcul.Util;
import com.example.addcul.adapter.PostDetailAdapter;
import com.example.addcul.listener.OnPostListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReadPostDetailActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ArrayList<PostDetailInfo> postDetailInfos;
    ArrayList<PostInfo>postList;
    ArrayList<MemberInfo> memberList;
    RecyclerView.Adapter postDetailAdapter;
    Util util;

    //ArrayList<String>testArray;
    String publisher;
    int position;
    TextView postTitle,postDetail, postConents, postName, postUpdated;
    ImageView sendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post_detail);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        postName = (TextView) findViewById(R.id.tv_post_name);
        postTitle = (TextView) findViewById(R.id.tv_post_title);
        postDetail = (TextView)findViewById(R.id.tv_post_detail);
        postConents = (TextView) findViewById(R.id.post_detail_tv_contents);
        postUpdated = (TextView)findViewById(R.id.tv_post_updated);
        sendBtn = (ImageView)findViewById(R.id.post_detail_btn_comment_submit);

        util = new Util(this);  // 커스텀 기능
        postList = new ArrayList<>();
        memberList = new ArrayList<>();
        postDetailInfos = new ArrayList<>();
       // testArray = new ArrayList<>();
        //testArray.add("장성필");



        // recyclerView

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.post_detail_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReadPostDetailActivity.this));
        postDetailAdapter = new PostDetailAdapter(ReadPostDetailActivity.this,postDetailInfos,memberList);
        ((PostDetailAdapter)postDetailAdapter).setOnPostListener(onPostListener);
        recyclerView.setAdapter(postDetailAdapter);


        // 전달받은 인텐드 값 가져오기
        Intent postIntent = getIntent();
        if (postIntent != null) {
            position = postIntent.getIntExtra("position", 0);
        } else {
            position = -1;
        }

        // run
        postDetailUpdate(); // 게시판 상세 페이지
        comentUpdate(); // 댓글 업데이트


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageUpload();
            }
        });

    }

    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete(int position) {
            final String id = postDetailInfos.get(position).getId();
            firebaseFirestore.collection("posts_free_detail").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            util.showToast("게시글을 삭제하였습니다.");
                            comentUpdate();
                            Log.e("포스트 게시물 삭제 : ", id);
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

           // myStartActivity(WritePostActivity.class, postList.get(position));
        }

    };


    @Override
    protected void onResume() {
        super.onResume();
        postDetailUpdate();
        comentUpdate();
    }

    // 파이어스토어 업로드
    private void storageUpload() {
       // final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();    // 게시글 제목
        final String contents = ((EditText)findViewById(R.id.post_detail_et_comment)).getText().toString();
        int rn = ((int)(Math.random()*100000)+1);

        postDetailUpdate();

       // String SID = postList.get
        if ( contents.length()>0) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // UID
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            PostDetailInfo postDetailInfos = (PostDetailInfo)getIntent().getSerializableExtra("postDetailInfos");
            final DocumentReference documentReference = postDetailInfos == null ?firebaseFirestore.collection("posts_free_detail").document():firebaseFirestore.collection("posts_free_detail").document(postDetailInfos.getId());
            final Date date = postDetailInfos == null ? new Date() : postDetailInfos.getCreatedAt();

            storeUploader(documentReference,new PostDetailInfo(firebaseUser.getUid(),contents,date,documentReference.getId()));

        }
        else{
            startToast("게시글을 입력해주세요.");
        }

    }
    private void storeUploader(DocumentReference documentReference , PostDetailInfo writeInfo) {
        documentReference.set(writeInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //loaderLayout.setVisibility(View.GONE);
                        startToast("댓글 등록을 성공하였습니다.");
                        //finish();
                        myStartActivity(ReadPostDetailActivity.class);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // loaderLayout.setVisibility(View.GONE);
                        startToast("댓글 등록을 실패하였습니다.");
                    }
                });
    }



    private void comentUpdate() {
        if (firebaseUser != null) {
            Log.e("XComent: ","11111111");
            CollectionReference collectionReference = firebaseFirestore.collection("posts_free_detail");
            collectionReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                postDetailInfos.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.e("XComent: ","2222222");
                                    postDetailInfos.add(new PostDetailInfo(
                                            document.getData().get("name").toString(),
                                            document.getData().get("contents").toString(),
                                            new Date(document.getDate("createdAt").getTime()),
                                            document.getId()));

                                    //getName();
                                    Log.e("XComent: ",postDetailInfos.get(0).getContents());
                                }

                                postDetailAdapter.notifyDataSetChanged();

                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }
    }

    private void postDetailUpdate() {
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("posts_free");
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
                                      Log.e("XComent: ","44444");
                                }
                                Date date = new Date();
                                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String updated = transFormat.format(date);

                                postTitle.setText(postList.get(position).getTitle());
                                postDetail.setText(postList.get(position).getContents());
                                //postName.setText(postList.get(position).getPublisher());
                                publisher = postList.get(position).getPublisher();
                                postUpdated.setText(updated);

                                getUserName();


                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }


    private void getUserName() {
        if (firebaseUser != null) {
            DocumentReference documentReference = firebaseFirestore.collection("users").document(publisher);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            memberList.add(new MemberInfo(
                                    document.getData().get("name").toString()));
                            postName.setText(memberList.get(0).getName());

                        } else {

                        }
                    }

                }
            });
        }
    } // end of getUserName()



    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
