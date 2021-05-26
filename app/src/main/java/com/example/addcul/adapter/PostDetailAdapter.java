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

import com.example.addcul.PostDetailInfo;
import com.example.addcul.R;
import com.example.addcul.activity.ReadPostDetailActivity;
import com.example.addcul.listener.OnPostListener;

import java.util.ArrayList;

public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.ViewHolder> {

    private ArrayList<PostDetailInfo> postDetailInfos;
    private ArrayList<String> testInfos;
    private Activity activity;
    private OnPostListener onPostListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View v) {
            super(v);
            view = v;

        }

    }

    public PostDetailAdapter(Activity activity, ArrayList<PostDetailInfo> dataSet) {
        this.postDetailInfos = dataSet;
        this.activity = activity;

    }

    public void setOnPostListener(OnPostListener onPostListener){
        this.onPostListener = onPostListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_post, viewGroup, false);
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
        TextView nameTextView = view.findViewById(R.id.titleTextView); // 게시글 제목 텍스트뷰
        TextView contentsTextView = view.findViewById(R.id.post_detail_tv_contents);
        TextView createdTextView = view.findViewById(R.id.createdAtTextView);   // 게시글 날짜 텍스트뷰

        nameTextView.setText("작성자(이름)");
        contentsTextView.setText(postDetailInfos.get(position).getContents().toString());

        //createdTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(postDetailInfos.get(position).getCreatedAt()));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStartActivity(ReadPostDetailActivity.class,position);
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


