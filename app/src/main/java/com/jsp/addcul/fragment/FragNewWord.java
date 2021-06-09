package com.jsp.addcul.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jsp.addcul.Util.DBHelper;
import com.jsp.addcul.DTO.NewWordInfo;
import com.jsp.addcul.R;
import com.jsp.addcul.adapter.NewWordAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragNewWord extends Fragment {

    RecyclerView recyclerView;


    public FragNewWord() {
        // Required empty public constructor


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_frag_new_word, container, false);
        ArrayList<NewWordInfo> list = new ArrayList<>();

        // DB 구현
        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select num, name,info,subInfo from newWord",null);

        while(cursor.moveToNext()){
            NewWordInfo newWordInfo = new NewWordInfo();
            newWordInfo.setNum(cursor.getString(0));
            newWordInfo.setName(cursor.getString(1));
            newWordInfo.setInfo(cursor.getString(2));
            newWordInfo.setSubInfo(cursor.getString(3));

            list.add(newWordInfo);
        }


        NewWordAdapter newWordAdapter = new NewWordAdapter(list,getActivity());

        recyclerView = (RecyclerView)view.findViewById(R.id.rv_newWord);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newWordAdapter);

//        newWordAdapter.setOnMyTouchListener(new NewWordAdapter.OnMyTouchListener() {
//            @Override
//            public void onTouch(View v, int postion) {
//                Log.e("출력 : ","출력 ");
//                startActivity(YoutubeActivity.class);
//
//            }
//        });


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

//    private void startActivity(Class c){
//        Intent intent = new Intent(getContext(),c);
//        startActivity(intent);
//    }


}
