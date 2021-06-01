package com.example.addcul.adapter;

import android.app.Activity;
import android.content.Intent;
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

import com.example.addcul.MemberInfo;
import com.example.addcul.PostInfo;
import com.example.addcul.R;
import com.example.addcul.listener.OnPostListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PostMyLogAdapter extends RecyclerView.Adapter<PostMyLogAdapter.ViewHolder> {

    private ArrayList<PostInfo> mDataset;
    private ArrayList<MemberInfo> uDataset; // 멤버 객체
    private Activity activity;
    private OnPostListener onPostListener;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View v) {
            super(v);
            view = v;

        }

    }

    public PostMyLogAdapter(Activity activity, ArrayList<PostInfo> dataSet, ArrayList<MemberInfo> userData) {
        this.activity = activity;
        this.mDataset = dataSet;    // 채팅데이터
        this.uDataset = userData;   // 유저데이터



    }

    public void setOnPostListener(OnPostListener onPostListener) {
        this.onPostListener = onPostListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mylog, viewGroup, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        TextView titleTextView = view.findViewById(R.id.mylog_tv_title); // 게시글 제목 텍스트뷰
        TextView contentsTextView = view.findViewById(R.id.mylog_tv_contents);
        TextView createdTextView = view.findViewById(R.id.mylog_tv_created);   // 게시글 날짜 텍스트뷰
        String uid = firebaseUser.getUid();

        titleTextView.setText(mDataset.get(position).getTitle());
        contentsTextView.setText(mDataset.get(position).getContents());
        createdTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getCreatedAt()));

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


    private void myStartActivity(Class c, int position, String actID) {
        Intent intent = new Intent(activity, c);
        intent.putExtra("position", position);
        intent.putExtra("actID", actID);
        activity.startActivity(intent);
    }

}


