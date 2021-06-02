package com.example.addcul.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.DTO.ProblemInfo;
import com.example.addcul.R;

import java.util.ArrayList;

public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.MyViewHolder> {

    public ArrayList<ProblemInfo>problemInfos;
    Context context;
    public OnMyTouchListener listener = null;

    public ProblemAdapter(ArrayList<ProblemInfo>problemInfos,Context context){
            this.problemInfos = problemInfos;
            this.context = context;
    }

    public interface OnMyTouchListener{
        void onTouch(View v, int postion);
    }
    public void setOnMyTouchListener(OnMyTouchListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);
        Log.e("크리에이터 : ","크리에이터 ");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String title = problemInfos.get(position).getTitle();
        String subtitle = problemInfos.get(position).getSubtitle();
        String photo = problemInfos.get(position).getPhoto();

        holder.title.setText(title);
        holder.subtitle.setText(subtitle);


        String resName = "@drawable/"+photo;
        String packName = context.getPackageName();

        int resID = context.getResources().getIdentifier(resName,"drawable",packName);
        Log.e("packName : ",packName);
        Log.e("res Name : ",resName);
        holder.photo.setBackgroundResource(resID);

    }

    @Override
    public int getItemCount() {
        return problemInfos.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView title,subtitle;
        LinearLayout photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_food_name);
            subtitle = itemView.findViewById(R.id.tv_food_tag);
            photo = itemView.findViewById(R.id.food_container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null)
                        listener.onTouch(v,position);

                }
            });
        }
    }
}
