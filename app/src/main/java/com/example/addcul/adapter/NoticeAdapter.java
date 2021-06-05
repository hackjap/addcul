package com.example.addcul.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.R;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    ArrayList<String> noticeList;

    public OnMyTouchLister lister = null;
    public NoticeAdapter(ArrayList<String> list) {
        this.noticeList = list;
    }


    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice,parent,false);


        return new NoticeViewHolder(view);
    }

    public interface OnMyTouchLister{
        void onTouch(View v,int position);
    }

    public void setOnMyTouchLister(OnMyTouchLister lister){
        this.lister = lister;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        holder.textView.setText(noticeList.get(position));
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    class NoticeViewHolder extends RecyclerView.ViewHolder{

        TextView textView ;
        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.notice_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(lister!=null)
                        lister.onTouch(v,position);

                }
            });
        }
    }
}
