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

        util = new Util(this);  // ????????? ??????
        postList = new ArrayList<>();
        memberList = new ArrayList<>();
        postDetailInfos = new ArrayList<>();
        // testArray = new ArrayList<>();
        //testArray.add("?????????");

        ImageView myInfoProfile = findViewById(R.id.img_my_info);   // ?????????
        TextView myInfoText = findViewById(R.id.tv_my_info);     // ?????????

        if (firebaseUser == null) { // ????????? ????????? ?????????
            Toast.makeText(getApplicationContext(), "????????? ??? ??????????????????.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);

        } else {  // ????????? ????????????
            myInfoProfile.setImageResource(R.drawable.ic_account_circle_black_24dp);
            myInfoText.setText("?????????");
        }

        // MainAdapter??? ?????? act????????? ????????? ??? ?????????
        // ???????????? ????????? ??? ????????????
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
        postDetailUpdate(); // ????????? ?????? ???????????? (?????????, ??????)


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
                            util.showToast("???????????? ?????????????????????.");
                            postDetailUpdate();
                            Log.e("????????? ????????? ?????? : ", id);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            util.showToast("???????????? ???????????? ??????????????????.");
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

    // ?????????????????? ?????????
    private void storageUpload(int position) {  // ????????? ????????????????????? ??????(comment)??? ?????????????????? ?????????

       // final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();    // ????????? ??????
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
            startToast("???????????? ??????????????????.");
        }

    }
    private void storeUploader(DocumentReference documentReference , PostDetailInfo writeInfo) {
        documentReference.set(writeInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //loaderLayout.setVisibility(View.GONE);
                        startToast("?????? ????????? ?????????????????????.");
                        //finish();
                        myStartActivity2(ReadPostDetailActivity.class,position,actID);
                        postDetailUpdate();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // loaderLayout.setVisibility(View.GONE);
                        startToast("?????? ????????? ?????????????????????.");
                    }
                });
    }



    private void comentUpdate(ArrayList<PostInfo> postList) {
        if (firebaseUser != null) {

           // postDetailUpdate(); // ????????? ?????? ????????? ???????????? ?????? ???????????? ?????? ????????? ????????????

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

    private void postDetailUpdate() {   // post(?????????) ????????? ????????? ?????????????????? ???????????? ??????
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
                                    postName.setText("??????");
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
