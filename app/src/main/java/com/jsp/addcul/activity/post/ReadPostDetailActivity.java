package com.jsp.addcul.activity.post;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jsp.addcul.DTO.MemberInfo;
import com.jsp.addcul.DTO.PostDetailInfo;
import com.jsp.addcul.DTO.PostInfo;
import com.jsp.addcul.R;
import com.jsp.addcul.Util.Util;
import com.jsp.addcul.activity.account.LoginActivity;
import com.jsp.addcul.activity.config.BasicActivity;
import com.jsp.addcul.adapter.PostDetailAdapter;
import com.jsp.addcul.listener.OnPostListener;
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

public class ReadPostDetailActivity extends BasicActivity {

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ArrayList<PostDetailInfo> postDetailInfos;
    ArrayList<PostInfo>postList;
    ArrayList<MemberInfo> memberList;
    PostDetailAdapter postDetailAdapter;
    Util util;

    //ArrayList<String>testArray;
    String publisher;
    int position;
    TextView postTitle,postDetail, postConents, postName, postUpdated;
    ImageView sendBtn;
    String actID = "";


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

        ImageView myInfoProfile = findViewById(R.id.img_my_info);   // 프로필
        TextView myInfoText = findViewById(R.id.tv_my_info);     // 닉네임

        if (firebaseUser == null) { // 로그인 상태가 아닐때
            Toast.makeText(getApplicationContext(), "로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);

        } else {  // 로그인 상태일때
            myInfoProfile.setImageResource(R.drawable.ic_account_circle_black_24dp);
            myInfoText.setText("내정보");
        }

        // MainAdapter로 부터 act별칭과 포지션 값 받아옴
        // 전달받은 인텐드 값 가져오기
        Intent postIntent = getIntent();
        if (postIntent != null) {
            position = postIntent.getIntExtra("position", 0);
            this.actID = postIntent.getStringExtra("actID");
            Log.e("CXXACT :",actID);
        } else {
            position = -1;
        }

        Log.e("CXXPOSITION",position+"");
        // run
        postDetailUpdate(); // 게시판 상세 업데이트 (게시판, 댓글)


        // recyclerView

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.post_detail_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReadPostDetailActivity.this));
        postDetailAdapter = new PostDetailAdapter(ReadPostDetailActivity.this,postDetailInfos,memberList,actID);
        ((PostDetailAdapter)postDetailAdapter).setOnPostListener(onPostListener);
        recyclerView.setAdapter(postDetailAdapter);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageUpload(position);
            }
        });

        postDetailUpdate();

    }

    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete(int position) {
            final String id = postDetailInfos.get(position).getId();
            firebaseFirestore.collection("posts_"+actID+"_detail").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            util.showToast("게시글을 삭제하였습니다.");
                            postDetailUpdate();
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
        //comentUpdate();
    }

    // 파이어스토어 업로드
    private void storageUpload(int position) {  // 게시글 상세페이지에서 댓글(comment)을 파이어스토어 업로드

       // final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();    // 게시글 제목
        final String contents = ((EditText)findViewById(R.id.post_detail_et_comment)).getText().toString();
        int rn = ((int)(Math.random()*100000)+1);

        //postDetailUpdate();


        String SID = postList.get(position).getId();

        if ( contents.length()>0) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // UID
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            PostDetailInfo postDetailInfos = (PostDetailInfo)getIntent().getSerializableExtra("postDetailInfos");
            final DocumentReference documentReference = postDetailInfos == null ?firebaseFirestore.collection("posts_"+actID+"_detail_"+SID).document():firebaseFirestore.collection("posts_"+actID+"_detail_"+SID).document(postDetailInfos.getId());
            final Date date = postDetailInfos == null ? new Date() : postDetailInfos.getCreatedAt();

            Log.e("CXXSID : ","posts_"+actID+"_detail"+SID);
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
                        myStartActivity2(ReadPostDetailActivity.class,position,actID);
                        postDetailUpdate();
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



    private void comentUpdate(ArrayList<PostInfo> postList) {
        if (firebaseUser != null) {

           // postDetailUpdate(); // 게시판 정보 객체를 받아와서 해당 게시판에 맞는 댓글창 업데이트

            String SID = postList.get(position).getId();
            Log.e("CXXCOMMENT!!: ","posts_"+actID+"_detail"+SID);
            CollectionReference collectionReference = firebaseFirestore.collection("posts_"+actID+"_detail_"+SID);
            collectionReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                postDetailInfos.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    postDetailInfos.add(new PostDetailInfo(
                                            document.getData().get("name").toString(),
                                            document.getData().get("contents").toString(),
                                            new Date(document.getDate("createdAt").getTime()),
                                            document.getId()));
                                }
                                //getName();
                            //    Log.e("CXXComent: ",postDetailInfos.get(0).getContents());
                                postDetailAdapter.notifyDataSetChanged();

                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }
    }

    private void postDetailUpdate() {   // post(게시판) 객체를 가져와 상세페이지를 띄어주는 함수
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("posts_"+actID);
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
                                Date date = new Date();
                                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String updated = transFormat.format(date);

                                postTitle.setText(postList.get(position).getTitle());
                                postDetail.setText(postList.get(position).getContents());
                                //postName.setText(postList.get(position).getPublisher());
                                publisher = postList.get(position).getPublisher();
                                postUpdated.setText(updated);

                                if(actID.equals("secret")){
                                    postName.setText("익명");
                                }
                                else{
                                    getUserName();
                                }

                                comentUpdate(postList);
                                Log.e("CXX! :",postList.get(0).getId());


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
    private void myStartActivity2(Class c,int position,String actID) {
        Intent intent = new Intent(this,c);
        intent.putExtra("position",position);
        intent.putExtra("actID",actID);
        startActivity(intent);
    }
}
