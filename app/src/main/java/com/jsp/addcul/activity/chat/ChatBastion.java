package com.jsp.addcul.activity.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jsp.addcul.R;
import com.jsp.addcul.activity.TmpActivity;

import java.util.ArrayList;

public class ChatBastion extends AppCompatActivity {

      /*
    1.레이아웃 매니저
    2.Viewholder
    3.어뎁터
    */

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbastion);

        setRecyclerAndList(); // 리사이클러 셋팅

    }

    private ArrayList<String> getNameList() {
        String names[] = {"영어", "베트", "태국"};

        ArrayList<String> list = new ArrayList<>();

        for (String name : names) {
            list.add(name);
        }
        return list;
    }

    private void setRecyclerAndList(){
        ArrayList<String> list = getNameList();

        recyclerView = findViewById(R.id.bastion_rc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ChatBastion.MyAdapter(list));
    }


    private class MyAdapter extends RecyclerView.Adapter<ChatBastion.MyViewHolder> {

        // 데이터와 리스트 연결
        ArrayList<String> list;

        public MyAdapter(ArrayList<String> list) {
            this.list = list;
        }

        @NonNull
        @Override // 레이아웃 연결
        public ChatBastion.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, parent, false);
            return new ChatBastion.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatBastion.MyViewHolder holder, int position) {


            String name = list.get(position);

            holder.text1.setText(name + 1);
            holder.text2.setText(name + 2);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    // xml 문서의 인스턴스와 연결
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text1,text2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
        }
    }
}
