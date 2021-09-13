package com.jsp.addcul.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jsp.addcul.R;

import java.util.ArrayList;

public class TmpActivity extends AppCompatActivity {

    /*
    1.레이아웃 매니저
    2.Viewholder
    3.어뎁터
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmp);

        RecyclerView recyclerView = findViewById(R.id.tmp_recyclerview);

        // 리사이클러에 담을 문자열 배열 생성
        String names[] = {"Jang", "sung", "Lee", "kim"};
        ArrayList<String> list = new ArrayList<>();
        for (String name:names) {
            list.add(name);
        }
        Log.e("리스트 ", list.toString());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));


    }

    // 리사이클러뷰에 연결할 어뎁터
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        public ArrayList<String>list;
        public MyAdapter(ArrayList<String> list) {
            this.list = list;
        }


        // ViewHolder를 연결함
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tmp, parent, false);
            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            String name = list.get(position);
            holder.name.setText(name);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    // 리사이클러 어뎁터에 연결할 ViewHolder
    private class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_tmp);
        }
    }
}