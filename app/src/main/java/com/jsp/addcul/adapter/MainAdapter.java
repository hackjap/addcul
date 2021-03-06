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
import com.jsp.addcul.DTO.PostInfo;
import com.jsp.addcul.R;
import com.jsp.addcul.activity.post.ReadPostDetailActivity;
import com.jsp.addcul.listener.OnPostListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<PostInfo> mDataset;
    private ArrayList<MemberInfo> uDataset; // 멤버 객체
    private Activity activity;
    private OnPostListener onPostListener;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    String actID;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View v) {
            super(v);
            view = v;

        }

    }

    public MainAdapter(Activity activity, ArrayList<PostInfo> dataSet, ArrayList<MemberInfo> userData, String actID) {
        this.mDataset = dataSet;
        this.activity = activity;
        this.uDataset = userData;
        this.actID = actID;


    }

    public void setOnPostListener(OnPostListener onPostListener) {
        this.onPostListener = onPostListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);

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

        Log.e("텍스트뷰 : ", "크리에이트");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        View view = viewHolder.view;
        TextView titleTextView = view.findViewById(R.id.titleTextView); // 게시글 제목 텍스트뷰
        TextView contentsTextView = view.findViewById(R.id.contentsTextView);
        TextView createdTextView = view.findViewById(R.id.createdAtTextView);   // 게시글 날짜 텍스트뷰
        TextView nameTextView = view.findViewById(R.id.post_tv_name);
        String uid = firebaseUser.getUid();

        nameTextView.setText(mDataset.get(position).getPublisher());
        titleTextView.setText(mDataset.get(position).getTitle());
        contentsTextView.setText(mDataset.get(position).getContents());
        createdTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getCreatedAt()));



        String publisher = "오류";
       // Log.e("CXXMem", uDataset.get(0).getName());
        //  Log.e("CXXUDD",uDataset.size()+"");

        //  채팅 작성자명 동기화
        for (int i = 0; i < uDataset.size(); i++) {
            String chatID = mDataset.get(position).getPublisher();  // 각 대화마다의 작성자 uid를 구함
            String userID = uDataset.get(i).getUid();   // 각 사용자의 uid
            // userID = uDataset.get(i).getUid();
            if (chatID.equals(userID)) {   // 로그인 사용자라면
                publisher = uDataset.get(i).getName();
                break;

            } else {
                publisher = "구글 로그인";
            }
        }

        if(actID.equals("secret")){
            nameTextView.setText("익명");
        }else
        {
            nameTextView.setText(publisher);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(ReadPostDetailActivity.class, position, actID);
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
//    private void myStartActivity(Class c,PostInfo postInfo) {
//        Intent intent = new Intent(activity, c);
//        intent.putExtra("postInfo",postInfo);
//        activity.startActivity(intent);
//    }

}


