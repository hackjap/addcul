package com.example.addcul.adapter;

import android.app.Activity;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.ChatInfo;
import com.example.addcul.MemberInfo;
import com.example.addcul.R;
import com.example.addcul.listener.OnPostListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private ArrayList<ChatInfo> mDataset;
    private ArrayList<MemberInfo> uDataset;
    private Activity activity;
    private OnPostListener onPostListener;
    //private String googleNickname;
    private Object context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View v) {
            super(v);
            view = v;

        }

    }

    public ChatAdapter(Activity activity, ArrayList<ChatInfo> dataSet, ArrayList<MemberInfo> userData) {
        this.activity = activity;
        this.mDataset = dataSet;
        this.uDataset = userData;


    }


    public void setOnPostListener(OnPostListener onPostListener) {
        this.onPostListener = onPostListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
       /* CardView cardView = view.findViewById(R.id.chat_menu);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, viewHolder.getAdapterPosition());
            }
        });


        */
        Log.e("텍스트뷰 : ", "크리에이트");
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        Log.e("텍스트뷰 : ", "바인드뷰홀더");
        View view = viewHolder.view;
        TextView NameTextView = view.findViewById(R.id.tv_chat_name); // 채팅작성자
        TextView TextTextView = view.findViewById(R.id.tv_chat_text);   // 채팅내용
        TextView createdTextView = view.findViewById(R.id.tv_chat_created);   // 채팅날짜



        // context= this.activity;

       // Intent intent = ((Activity)context).getIntent();
      //  googleNickname = intent.getStringExtra("nickname");








        //Log.e("udata : ", uDataset.get(0).getUid());
//        Log.e("mdata : ", uDataset.get(position).getUid());

        //String userID = uDataset.get(position).getUid();

        String userID="";
        String chatID;


        for (int i = 0; i < uDataset.size();i++) {
            chatID = mDataset.get(position).getPublisher();
            userID = uDataset.get(i).getUid();
            Log.e("chatID :  ",position+" : " +mDataset.get(position).getPublisher());
            Log.e("userID :  ",i+" : " + uDataset.get(i).getUid());
            if ( chatID.equals(userID)) {
                userID = uDataset.get(i).getName();
                break;
            } else {
                userID = "장성필";
            }

        }


       // Log.e("최종 userID :  ", userID);
        NameTextView.setText(userID);
        TextTextView.setText(mDataset.get(position).getText());
        createdTextView.setText(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault()).format(mDataset.get(position).getCreated()));


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    private void showPopup(View v, final int position) {
        PopupMenu popupMenu = new PopupMenu(activity, v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            String id = mDataset.get(position).getId();

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


}


