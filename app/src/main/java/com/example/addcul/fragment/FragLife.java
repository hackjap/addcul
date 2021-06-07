package com.example.addcul.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.Util.DBHelper;
import com.example.addcul.DTO.LifeInfo;
import com.example.addcul.R;
import com.example.addcul.activity.kculture.KakaoActivity;
import com.example.addcul.activity.kculture.YoutubeActivity;
import com.example.addcul.adapter.LifeAdapter;
import com.example.addcul.activity.kculture.NaverActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragLife extends Fragment {
    RecyclerView recyclerView;


    public FragLife() {
        // Required empty public constructor


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_frag_life, container, false);
        ArrayList<LifeInfo> list = new ArrayList<>();

        // DB 구현
        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name,photo from life",null);

        while(cursor.moveToNext()){
            LifeInfo lifeInfo = new LifeInfo();
            lifeInfo.setName(cursor.getString(0));
            lifeInfo.setPhoto(cursor.getString(1));

            list.add(lifeInfo);
        }


        LifeAdapter lifeAdapter  = new LifeAdapter(list,getActivity());

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_life);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(lifeAdapter);

        lifeAdapter.setOnMyTouchListener(new LifeAdapter.OnMyTouchListener() {
            @Override
            public void onTouch(View v, int postion) {
                Log.e("출력 : ","출력 ");

                if(postion == 0){
                    Intent intent = new Intent(getContext(), NaverActivity.class);
                    startActivity(intent);
                }

                else if(postion ==1){
                    Intent intent = new Intent(getContext(), KakaoActivity.class);
                    startActivity(intent);
                }
                else if(postion ==2){

                }
                else if(postion ==3){
                    Intent intent = new Intent(getContext(), YoutubeActivity.class);
                    startActivity(intent);
                }

            }
        });
        return view;
    }
}

