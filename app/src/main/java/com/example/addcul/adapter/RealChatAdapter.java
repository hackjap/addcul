package com.example.addcul.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.ChatInfo;
import com.example.addcul.MemberInfo;
import com.example.addcul.R;
import com.example.addcul.activity.ReadPostDetailActivity;
import com.example.addcul.listener.OnPostListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class RealChatAdapter extends RecyclerView.Adapter<RealChatAdapter.ViewHolder> {

    private ArrayList<ChatInfo> cDateset;
    private ArrayList<MemberInfo> mDataset;
    private Activity activity;
    private OnPostListener onPostListener;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public RealChatAdapter(Activity activity, ArrayList<ChatInfo>cDataset, ArrayList<MemberInfo> mDataSet) {
        this.cDateset = cDataset;
        this.mDataset = mDataSet;
        this.activity = activity;

    }

    public void setOnPostListener(OnPostListener onPostListener){
        this.onPostListener = onPostListener;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_realchat, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        LinearLayout chatBack;
        TextView titleTextView;
        TextView contentsTextView;
        TextView createdTextView;


        public ViewHolder(View v) {
            super(v);
            view = v;
            chatBack = view.findViewById(R.id.realchat_back);
            titleTextView = view.findViewById(R.id.tv_realchat_name); // 게시글 제목 텍스트뷰
            contentsTextView = view.findViewById(R.id.tv_realchat_text);
            createdTextView =view.findViewById(R.id.tv_realchat_created);   // 게시글 날짜 텍스트뷰

        }

    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        View view = viewHolder.view;


//        String loginUid = firebaseUser.getUid();
//        String publisher="";
//
//        // 채팅 작성자명 동기화
//        for(int i=0;i< mDataset.size();i++){
//            String chatID = cDateset.get(position).getPublisher(); // 채팅 작성자 uid
//            String userID = mDataset.get(i).getUid(); // 각 사용자 uid
//
//            if(chatID.equals(userID)){
//                publisher = mDataset.get(i).getName();
//                break; // 해당 작성자 찾으면 break;
//            }else{
//                publisher = "구글 로그인";
//            }
//        }

//        for(int i =0;i<mDataset.size();i++){
//            String chatID  = cDateset.get(position).getPublisher();
//            if(loginUid.equals(chatID)){    // 로그인한 사용자이면
//                viewHolder.titleTextView
//            }
//        }
       // viewHolder.titleTextView.setText(publisher);
        viewHolder.contentsTextView.setText(cDateset.get(position).getText());
        //viewHolder.createdTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cDateset.get(position).getCreated()));



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(ReadPostDetailActivity.class,position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    private void showPopup(View v, final int position) {
        PopupMenu popupMenu = new PopupMenu(activity, v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            String id = cDateset.get(position).getId();
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

    private void startToast(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }


    private void myStartActivity(Class c,int position) {
        Intent intent = new Intent(activity,c);
        intent.putExtra("position",position);
        activity.startActivity(intent);
    }
//    private void myStartActivity(Class c,PostInfo postInfo) {
//        Intent intent = new Intent(activity, c);
//        intent.putExtra("postInfo",postInfo);
//        activity.startActivity(intent);
//    }

}


