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

import com.example.addcul.DBHelper;
import com.example.addcul.FoodInfo;
import com.example.addcul.R;
import com.example.addcul.activity.YoutubeActivity;
import com.example.addcul.adapter.FoodAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragFood extends Fragment {

    RecyclerView recyclerView;


    public FragFood() {
        // Required empty public constructor


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_frag_food, container, false);
        ArrayList<FoodInfo> list = new ArrayList<>();

        // DB 구현
        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name,tag,photo from food",null);

        while(cursor.moveToNext()){
         FoodInfo foodInfo = new FoodInfo();
         foodInfo.setName(cursor.getString(0));
         foodInfo.setTag(cursor.getString(1));
         foodInfo.setPhoto(cursor.getString(2));

         list.add(foodInfo);
        }


        FoodAdapter foodAdapter = new FoodAdapter(list,getActivity());

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(foodAdapter);

        foodAdapter.setOnMyTouchListener(new FoodAdapter.OnMyTouchListener() {
            @Override
            public void onTouch(View v, int postion) {
                Log.e("출력 : ","출력 ");
                startActivity(YoutubeActivity.class);

            }
        });


//        // This callback will only be called when MyFragment is at least Started.
//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                // Handle the back button event
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()


        // 내일 아이템을 눌렀을때 동작 + 이동되는 액티비티 구성



        return view;
    }

    private void startActivity(Class c){
        Intent intent = new Intent(getContext(),c);
        startActivity(intent);
    }


}
