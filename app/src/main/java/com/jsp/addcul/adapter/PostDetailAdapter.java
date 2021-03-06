package com.jsp.addcul.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jsp.addcul.DTO.MemberInfo;
import com.jsp.addcul.DTO.PostDetailInfo;
import com.jsp.addcul.DTO.PostInfo;
import com.jsp.addcul.R;
import com.jsp.addcul.activity.post.ReadPostDetailActivity;
import com.jsp.addcul.listener.OnPostListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.ViewHolder> {

    private ArrayList<PostDetailInfo> postDetailInfos;
    private ArrayList<MemberInfo> memberInfos;
    private Activity activity;
    private OnPostListener onPostListener;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String actID;
    //TextView nameTextView;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View v) {
            super(v);
            view = v;

        }

    }

    public PostDetailAdapter(Activity activity, ArrayList<PostDetailInfo> dataSet, ArrayList<MemberInfo> memberInfos,String actID) {
        this.postDetailInfos = dataSet;
        this.activity = activity;
        this.memberInfos = memberInfos;
        this.actID = actID;
    }

    public void setOnPostListener(OnPostListener onPostListener){
        this.onPostListener = onPostListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_post, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        CardView cardView = view.findViewById(R.id.menu);
        cardView.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        View view = viewHolder.view;
        TextView nameTextView = viewHolder.view.findViewById(R.id.post_detail_tv_name); // ????????? ?????? ????????????

        TextView contentsTextView = view.findViewById(R.id.post_detail_tv_contents);
        TextView createdTextView = view.findViewById(R.id.post_detail_tv_created);   // ????????? ?????? ????????????

        String data = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(postDetailInfos.get(position).getCreatedAt());
        String uid = postDetailInfos.get(position).getname();
        Log.e("CXXADAPTER",uid);
        Log.e("CXXADAPTER",data);

        Log.e("CXXACT!!!",actID);

        if(actID.equals("secret"))
            nameTextView.setText("??????");
        else{
            getUserName(uid, viewHolder);
        }


        contentsTextView.setText(postDetailInfos.get(position).getContents());
        createdTextView.setText(data);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity2(ReadPostDetailActivity.class,position,actID);
            }
        });

    }


    @Override
    public int getItemCount() {
      return postDetailInfos.size();
    }




    private void showPopup(View v, final int position) {
        PopupMenu popupMenu = new PopupMenu(activity, v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            String id = postDetailInfos.get(position).getId();
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.modify:
                        onPostListener.onModify(position);
                        return true;
                    case R.id.delete:
                        onPostListener.onDelete(position);
                        return true;

                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.post, popupMenu.getMenu());
        popupMenu.show();
    }

    private void getUserName(String uid, final ViewHolder viewHolder){


        if (firebaseUser != null) {

            DocumentReference documentReference = firebaseFirestore.collection("users").document(uid);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        memberInfos.clear();
                        DocumentSnapshot document = task.getResult();
                            if (document.exists()){
                                memberInfos.add(new MemberInfo(
                                    document.getData().get("name").toString()));
                            // postName.setText(memberInfos.get(0).getName());
                                TextView nameTextView = viewHolder.view.findViewById(R.id.post_detail_tv_name); // ????????? ?????? ????????????
                                Log.e("CXXACT2222",actID);
                                nameTextView.setText(memberInfos.get(0).getName());
                        }
                    }

                }
            });
        }

    }

    private void startToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }


    private void myStartActivity(Class c,int position) {
        Intent intent = new Intent(activity,c);
        intent.putExtra("position",position);
        activity.startActivity(intent);
    }
    private void myStartActivity(Class c, PostInfo postInfo) {
        Intent intent = new Intent(activity, c);
        intent.putExtra("postInfo", postInfo);
        activity.startActivity(intent);


    }
    private void myStartActivity2(Class c,int position,String actID) {
        Intent intent2 = new Intent(activity,c);
        intent2.putExtra("position",position);
        intent2.putExtra("actID",actID);
        activity.startActivity(intent2);
    }

}


