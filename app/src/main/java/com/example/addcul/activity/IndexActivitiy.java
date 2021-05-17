package com.example.addcul.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.ChatData;
import com.example.addcul.MemberInfo;
import com.example.addcul.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class IndexActivitiy extends AppCompatActivity {


    RecyclerView recyclerView;
    EditText editText;
    Button sendButton;
    ArrayAdapter adapter;
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

        memberInfos = new ArrayList<>();

        // final String userName = "user"+ new Random().nextInt(10000);
        final String userName = firebaseUser.getUid();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter();

        getUserName(userName);


        databaseReference.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatData chatData = snapshot.getValue(ChatData.class);
                adapter.add(chatData.getUserName() + ": " + chatData.getMessage());

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
                                    final ChatData chatData = new ChatData(memberInfos.get(0).getName(), editText.getText().toString());
                                    databaseReference.child("message").push().setValue(chatData);
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
