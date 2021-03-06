package com.jsp.addcul.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.jsp.addcul.DTO.ChatData;
import com.jsp.addcul.DTO.ChatInfo;
import com.jsp.addcul.DTO.MemberInfo;
import com.jsp.addcul.R;
import com.jsp.addcul.activity.account.LoginActivity;
import com.jsp.addcul.adapter.RealChatAdapter;

import java.util.ArrayList;
import java.util.Date;

public class IndexActivity2 extends AppCompatActivity {


    RecyclerView recyclerView;
    EditText editText;
    ImageView sendButton;
    RealChatAdapter adapter;
    ArrayList<ChatInfo> chatInfos;
    ArrayList<MemberInfo> memberInfos;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference conditionRef = databaseReference.child("tai");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_activitiy2);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_chat);
        editText = (EditText) findViewById(R.id.et_chat_text);
        sendButton = (ImageView) findViewById(R.id.img_chat_send);

        chatInfos = new ArrayList<>();
        memberInfos = new ArrayList<>();

        // final String userName = "user"+ new Random().nextInt(10000);



        ImageView myInfoProfile = findViewById(R.id.img_my_info);   // ?????????
        TextView myInfoText = findViewById(R.id.tv_my_info);     // ?????????


        if (firebaseUser == null) { // ????????? ????????? ?????????
            Toast.makeText(getApplicationContext(), "????????? ??? ??????????????????.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        } else {  // ????????? ????????????
            final String userName = firebaseUser.getUid();
            myInfoProfile.setImageResource(R.drawable.ic_account_circle_black_24dp);
            myInfoText.setText("?????????");

            getUserName(userName);
            //chatUpdate();


            adapter = new RealChatAdapter(IndexActivity2.this,chatInfos,memberInfos);
            recyclerView.setLayoutManager(new LinearLayoutManager(IndexActivity2.this));
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(adapter.getItemCount()-1);


            conditionRef.orderByValue().addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    ChatData chatData = snapshot.getValue(ChatData.class);
                    Log.e("snapshot : ",chatData.getUserName() +" : " + chatData.getMessage());


                    chatInfos.add(new ChatInfo(chatData.getUserName(),chatData.getMessage(),new Date(),chatData.getUid()));
                    // adapter.notifyDataSetChanged();
                    adapter = new RealChatAdapter(IndexActivity2.this,chatInfos,memberInfos);
                    recyclerView.setLayoutManager(new LinearLayoutManager(IndexActivity2.this));
                    recyclerView.setAdapter(adapter);
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
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
                                    document.getData().get("name").toString(),
                                    firebaseUser.getUid())

                            );
                            sendButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(editText.getText().length() > 0){
                                        final ChatData chatData = new ChatData( memberInfos.get(0).getName(), editText.getText().toString(),firebaseUser.getUid());
                                        conditionRef.push().setValue(chatData);
                                        editText.setText("");

                                    }
                                        // ?????????????????? ??? ?????? ??????
                                    else{
                                        Toast.makeText(getApplicationContext(),"?????? ????????? ??????????????????",Toast.LENGTH_LONG).show();
                                    }
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
