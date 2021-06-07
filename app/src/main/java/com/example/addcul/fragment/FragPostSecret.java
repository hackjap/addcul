package com.example.addcul.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.DTO.MemberInfo;
import com.example.addcul.DTO.PostInfo;
import com.example.addcul.R;
import com.example.addcul.Util.Util;
import com.example.addcul.activity.post.WritePostSecretActivity;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragPostSecret extends Fragment {

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    RecyclerView.Adapter mainAdapter;
    ArrayList<PostInfo> postList;
    ArrayList<MemberInfo> memberInfos;
    Util util;

    public FragPostSecret() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        postUpdate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_frag_post_secret, container, false);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        memberInfos = new ArrayList<>();
        postList = new ArrayList<>();
        String actID = "secret";

        mainAdapter = new MainAdapter(getActivity(), postList, memberInfos, actID);
        ((MainAdapter) mainAdapter).setOnPostListener(onPostListener);
        util = new Util(getActivity());

        RecyclerView recyclerView = view.findViewById(R.id.rc_post_free);
        view.findViewById(R.id.goto_post_write).setOnClickListener(onClickListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter(new MainAdapter(ReadPostActivity.this,postList));
        recyclerView.setAdapter(mainAdapter);




        postUpdate();
        getMemberData();


        return view;


    }

    private void getMemberData() {
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
                                mainAdapter.notifyDataSetChanged();

                            } else {
                            }
                        }

                    });

        }

    }

    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete(int position) {
            final String id = postList.get(position).getId();
            firebaseFirestore.collection("posts_secret").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            util.showToast("게시글을 삭제하였습니다.");
                            postUpdate();
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

            myStartActivity(WritePostSecretActivity.class, postList.get(position));
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
                    Intent intent = new Intent(getActivity(), WritePostSecretActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void postUpdate() {
        if (firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("posts_secret");
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

    private void myStartActivity(Class c, PostInfo postInfo) {
        Intent intent = new Intent(getActivity(), c);
        intent.putExtra("postInfo", postInfo);
        startActivity(intent);
    }
}
