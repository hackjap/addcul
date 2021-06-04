package com.example.addcul.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.R;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    public NoticeAdapter() {
    }


    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice,parent,false);


        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder{

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
