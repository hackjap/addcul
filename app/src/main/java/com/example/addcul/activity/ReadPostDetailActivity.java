package com.example.addcul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.addcul.MemberInfo;
import com.example.addcul.PostInfo;
import com.example.addcul.R;
import com.google.android.gms.tasks.OnCompleteListener;
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
    ArrayList<PostInfo> postList;
    ArrayList<MemberInfo> memberList;

    String publisher;
    int position;
    TextView postTitle, postDetail, postName, postUpdated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post_detail);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        postTitle = (TextView) findViewById(R.id.tv_post_title);
        postDetail = (TextView) findViewById(R.id.tv_post_detail);
        postName = (TextView) findViewById(R.id.tv_post_name);
        postUpdated = (TextView) findViewById(R.id.tv_post_updated);


        postList = new ArrayList<>();
        memberList = new ArrayList<>();


        // 전달받은 인텐드 값 가져오기
        Intent postIntent = getIntent();
        if (postIntent != null) {
            position = postIntent.getIntExtra("position", 0);
        } else {
            position = -1;
        }


        // run
        postDetailUpdate();

    }

    private void postDetailUpdate() {
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
}
