package com.example.addcul.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.ChatInfo;
import com.example.addcul.MemberInfo;
import com.example.addcul.R;
import com.example.addcul.adapter.RealChatAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class IndexActivitiy extends AppCompatActivity {


    RecyclerView recyclerView;
    EditText editText;
    Button sendButton;
    RealChatAdapter adapter;
    ArrayList<ChatInfo> chatInfos;
    ArrayList<MemberInfo> memberInfos;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_activitiy);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_realChat);
        editText = (EditText) findViewById(R.id.edit_contents);
        sendButton = (Button) findViewById(R.id.send_button);

        chatInfos = new ArrayList<>();
        memberInfos = new ArrayList<>();

        // final String userName = "user"+ new Random().nextInt(10000);
        final String userName = firebaseUser.getUid();


        adapter = new RealChatAdapter(IndexActivitiy.this,chatInfos,memberInfos);
        recyclerView.setLayoutManager(new LinearLayoutManager(IndexActivitiy.this));
        recyclerView.setAdapter(adapter);

        getUserName(userName);
        //chatUpdate();

        DatabaseReference conditionRef = databaseReference.child("message");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String chatinfo = snapshot.getValue().toString();
                Log.e("snapshot : ",chatinfo);
                chatInfos.add(new ChatInfo(chatinfo));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        conditionRef.addValueEventListener(valueEventListener);

        /*
        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               // ChatInfo chatInfo = snapshot.getValue(ChatInfo.class);
                String chatName = snapshot.getValue(String.class);
                Log.e("snapshot : ",chatName);
                chatInfos.add(new ChatInfo(chatName));
                Log.e("snapshot2 : ",chatInfos.get(0).getText());
                 adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */

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
                                chatInfos.clear();

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                    chatInfos.add(new ChatInfo(
                                            document.getData().get("text").toString(),
                                            document.getData().get("publisher").toString(),
                                            new Date(document.getDate("created").getTime()),
                                            document.getId())); // 문서 id
                                    //getName();


                                }

                                adapter.notifyDataSetChanged();

                            } else {
                                //  Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }
    }



    private void getUserName(String userName) {
        if (firebaseUser != null) {
            DocumentReference documentReference = firebaseFirestore.collection("users").document(userName);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            memberInfos.add(new MemberInfo(
                                    document.getData().get("name").toString()));

                            sendButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final ChatInfo chatInfo = new ChatInfo( editText.getText().toString());
                                    databaseReference.child("message").child("username").push().setValue(chatInfo);
                                    editText.setText("");
                                }
                            });
                        } else {

                        }
                    }

                }
            });
        }
    } // end of getUserName()
}
