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
import com.example.addcul.R;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    public ArrayList<FoodInfo>foodInfos;
    Context context;
    public OnMyTouchListener listener = null;

    public FoodAdapter(ArrayList<FoodInfo>foodInfos,Context context){
            this.foodInfos = foodInfos;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);
        Log.e("크리에이터 : ","크리에이터 ");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String name = foodInfos.get(position).getName();
        String tag = foodInfos.get(position).getTag();
        String photo = foodInfos.get(position).getPhoto();
        Log.e("네임  : ",name);
        Log.e("tag  : ",tag);
        holder.name.setText(name);
        holder.tag.setText(tag);

        String resName = "@drawable/"+photo;
        String packName = context.getPackageName();

        int resID = context.getResources().getIdentifier(resName,"drawable",packName);

        holder.photo.setBackgroundResource(resID);



    }

    @Override
    public int getItemCount() {
        return foodInfos.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView name,tag;
        LinearLayout photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_food_name);
            tag = itemView.findViewById(R.id.tv_food_tag);
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
