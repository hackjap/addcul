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
import com.example.addcul.LifeInfo;
import com.example.addcul.R;

import java.util.ArrayList;

public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.MyViewHolder> {

    public ArrayList<LifeInfo>lifeInfos;
    Context context;
    public OnMyTouchListener listener = null;

    public LifeAdapter(ArrayList<LifeInfo>lifeInfos,Context context){
        this.lifeInfos = lifeInfos;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_life,parent,false);
        Log.e("크리에이터 : ","크리에이터 ");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String name = lifeInfos.get(position).getName();
        String photo = lifeInfos.get(position).getPhoto();
        Log.e("네임  : ",name);
        holder.name.setText(name);

        String resName = "@drawable/"+photo;
        String packName = context.getPackageName();

        int resID = context.getResources().getIdentifier(resName,"drawable",packName);

        holder.photo.setBackgroundResource(resID);
    }

    @Override
    public int getItemCount() {
        return lifeInfos.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView name;
        LinearLayout photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.life_infoName);
            photo = itemView.findViewById(R.id.life_gotoPage);

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
