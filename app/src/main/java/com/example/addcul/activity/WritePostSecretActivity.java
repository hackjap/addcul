package com.example.addcul.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.addcul.PostInfo;
import com.example.addcul.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;

public class WritePostSecretActivity extends BasicActivity {
    private static final String TAG = "WritePostSosActivity";
    private FirebaseUser user;
    private ArrayList<String> pathList = new ArrayList<>();
    LinearLayout parent;
    private int pathCount,successCount;
    private RelativeLayout buttonsBackgroundLayout;
    private RelativeLayout loaderLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post_secret);
        parent = findViewById(R.id.contentsLayout);
        buttonsBackgroundLayout = findViewById(R.id.buttonsBackgrundLayout);
        loaderLayout= findViewById(R.id.loaderLayout);
        findViewById(R.id.check).setOnClickListener(onClickListener);   // 등록하기 버튼


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {


                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    ImageView imageView = new ImageView(WritePostSecretActivity.this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonsBackgroundLayout.setVisibility(View.VISIBLE);
                        }
                    });

                    EditText editText = new EditText(WritePostSecretActivity.this);
                    editText.setLayoutParams(layoutParams);
                    editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE|InputType.TYPE_CLASS_TEXT);
                    editText.setHint("내용");
                    parent.addView(editText);

                }
                break;

            }
        }
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.check:
                    storageUpload();
                    break;
                case R.id.buttonsBackgrundLayout:
                    if(buttonsBackgroundLayout.getVisibility()==View.VISIBLE){
                        buttonsBackgroundLayout.setVisibility(View.GONE);
                    }
            }
        }
    };

    // 파이어스토어 업로드
    private void storageUpload() {
        final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();    // 게시글 제목
        final String contents = ((EditText)findViewById(R.id.contentsEditText)).getText().toString();

        if (title.length() > 0 && contents.length()>0) {
            loaderLayout.setVisibility(View.VISIBLE);
            user = FirebaseAuth.getInstance().getCurrentUser(); // UID
            FirebaseStorage storage = FirebaseStorage.getInstance();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            PostInfo postInfo = (PostInfo)getIntent().getSerializableExtra("postinfo");
            final DocumentReference documentReference = postInfo == null ?firebaseFirestore.collection("posts_secret").document():firebaseFirestore.collection("posts_secret").document(postInfo.getId());
            final Date date = postInfo == null ? new Date() : postInfo.getCreatedAt();
            storeUploader(documentReference,new PostInfo(title,contents,user.getUid(),date));

    }
        else{
            startToast("게시글을 입력해주세요.");
        }

    }
    private void storeUploader(DocumentReference documentReference , PostInfo writeInfo) {
    documentReference.set(writeInfo)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                   loaderLayout.setVisibility(View.GONE);
                    startToast("게시물 등록을 성공하였습니다.");
                    finish();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   loaderLayout.setVisibility(View.GONE);
                    startToast("게시물 등록을 실패하였습니다.");
                }
            });
    }

    private void myStartActivity(Class c, String media) {
        Intent intent = new Intent(this, c);
        intent.putExtra("media", media);
        startActivityForResult(intent, 0);
    }


    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
