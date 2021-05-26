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

import com.example.addcul.FoodInfo;
import com.example.addcul.NewWordInfo;
import com.example.addcul.R;

import java.util.ArrayList;

public class NewWordAdapter extends RecyclerView.Adapter<NewWordAdapter.MyViewHolder> {

    public ArrayList<NewWordInfo>newWordInfos;
    Context context;
    public OnMyTouchListener listener = null;

    public NewWordAdapter(ArrayList<NewWordInfo>newWordInfos,Context context){
        this.newWordInfos = newWordInfos;
        this.context = context;
    }

    public interface OnMyTouchListener{
        void onTouch(View v,int postion);
    }
    public void setOnMyTouchListener(OnMyTouchListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_word,parent,false);
        Log.e("크리에이터 : ","크리에이터 ");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String num = newWordInfos.get(position).getNum();
        String name = newWordInfos.get(position).getName();
        String info = newWordInfos.get(position).getInfo();
        String subInfo = newWordInfos.get(position).getSubInfo();
        Log.e("번호  : ",num);
        Log.e("네임  : ",name);
        Log.e("정보  : ",info);
        Log.e("서브 정보  : ",subInfo);
        holder.num.setText(num);
        holder.name.setText(name);
        holder.info.setText(info);
        holder.subInfo.setText(subInfo);

//        String resName = "@drawable/"+photo;
//        String packName = context.getPackageName();
//
//        int resID = context.getResources().getIdentifier(resName,"drawable",packName);
//
//        holder.photo.setBackgroundResource(resID);



    }

    @Override
    public int getItemCount() {
        return newWordInfos.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView num, name, info, subInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.tv_word_num);
            name = itemView.findViewById(R.id.tv_word_name);
            info = itemView.findViewById(R.id.tv_word_info);
            subInfo = itemView.findViewById(R.id.tv_word_subInfo);

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
