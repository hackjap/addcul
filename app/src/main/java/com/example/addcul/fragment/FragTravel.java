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

import com.example.addcul.R;
import com.example.addcul.DTO.TravelInfo;
import com.example.addcul.Util.DBHelper;
import com.example.addcul.adapter.TravelAdapter;
import com.example.addcul.lifeInfo.NaverActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragTravel extends Fragment {
    RecyclerView recyclerView;


    public FragTravel() {
        // Required empty public constructor


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_frag_travel, container, false);
        ArrayList<TravelInfo> list = new ArrayList<>();

        // DB 구현
        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name,photo from travel",null);

        while(cursor.moveToNext()){
            TravelInfo travelInfo = new TravelInfo();
            travelInfo.setName(cursor.getString(0));
            travelInfo.setPhoto(cursor.getString(1));

            list.add(travelInfo);
        }


        TravelAdapter travelAdapter= new TravelAdapter(list,getActivity());

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_travel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(travelAdapter);

        travelAdapter.setOnMyTouchListener(new TravelAdapter.OnMyTouchListener() {
            @Override
            public void onTouch(View v, int postion) {
                Log.e("출력 : ","출력 ");
                Intent intent = new Intent(getContext(), NaverActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
