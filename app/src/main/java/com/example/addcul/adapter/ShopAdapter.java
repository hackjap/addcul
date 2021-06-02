package com.example.addcul.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.R;
import com.example.addcul.DTO.ShopInfo;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {

    public ArrayList<ShopInfo>shopInfos;
    Context context;
    public OnMyTouchListener listener = null;

    public ShopAdapter(ArrayList<ShopInfo>shopInfos,Context context){
        this.shopInfos = shopInfos;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop,parent,false);
        Log.e("크리에이터 : ","크리에이터 ");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String name = shopInfos.get(position).getName();
        String tag = shopInfos.get(position).getTag();
        String photo = shopInfos.get(position).getPhoto();
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
        return shopInfos.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView name,tag;
        ImageView photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_shop_name);
            tag = itemView.findViewById(R.id.tv_shop_tag);
            photo = itemView.findViewById(R.id.logo_shop);

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
