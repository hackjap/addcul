package com.jsp.addcul.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jsp.addcul.DTO.Corona19;
import com.jsp.addcul.R;

import java.util.ArrayList;

public class Corona19Adapter extends RecyclerView.Adapter<Corona19Adapter.Corona19ViewHolder> {
    ArrayList<Corona19> list;

    public Corona19Adapter(ArrayList<Corona19> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public Corona19ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_corona19,parent,false);
        return new Corona19ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Corona19ViewHolder holder, int position) {

        // 기존 API 변경으로 인해 수정이필요
//            int result = Integer.parseInt(list.get(position).getConf())-Integer.parseInt(list.get(position+1).getConf());
//            holder.date.setText(""+result);
//            holder.conf.setText(list.get(position).getConf());
//            holder.release.setText(list.get(position).getRelease());
//            holder.death.setText(list.get(position).getDeath());
//            holder.exam.setText(list.get(position).getExam());

        // 시연용 코로나 확진자 셋팅
        holder.date.setText(""+5000);
        holder.conf.setText("" + 4000);
        holder.release.setText("" +2000);
        holder.death.setText("" + 2000);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class Corona19ViewHolder extends RecyclerView.ViewHolder{
        TextView date, conf, release, death, exam;
        public Corona19ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.corona_date);
            conf = itemView.findViewById(R.id.corona_confirmed);
            release = itemView.findViewById(R.id.corona_release);
            death = itemView.findViewById(R.id.corona_death);
//            exam = itemView.findViewById(R.id.corona_exam);
        }
    }
}
