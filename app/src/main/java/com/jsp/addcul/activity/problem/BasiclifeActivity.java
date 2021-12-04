package com.jsp.addcul.activity.problem;

import android.content.Context;
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

public class BasiclifeActivity extends AppCompatActivity {

    /*
    1.레이아웃 매니저
    2.Viewholder
    3.어뎁터
    */

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basiclife);

        setRecyclerAndList(); // 리사이클러 셋팅

    }

    private ArrayList<String> getNameList() {
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> link = new ArrayList<>();
        ArrayList<String> subtext = new ArrayList<>();

        return title;
    }

    private void setRecyclerAndList(){
        ArrayList<String> list = getNameList();

        recyclerView = findViewById(R.id.walefare_rc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list));
    }


    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        // 데이터와 리스트 연결
        ArrayList<String> list;

        public MyAdapter(ArrayList<String> list) {
            this.list = list;
        }

        @NonNull
        @Override // 레이아웃 연결
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


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