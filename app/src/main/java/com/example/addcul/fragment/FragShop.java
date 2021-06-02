package com.example.addcul.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addcul.Util.DBHelper;
import com.example.addcul.R;
import com.example.addcul.DTO.ShopInfo;
import com.example.addcul.adapter.ShopAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragShop extends Fragment {
    RecyclerView recyclerView;
    ShopAdapter shopAdapter;
    // DB 구현
    LinearLayout btnWomen;
    LinearLayout btnMen;
    LinearLayout btnAppliance;
    LinearLayout btnMakeUp;
    LinearLayout btnFood;
    LinearLayout btnFurniture;

    ArrayList<ShopInfo> list;

    public FragShop() {
        // Required empty public constructor


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        list = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_frag_shop, container, false);



        btnWomen = view.findViewById(R.id.frag_shop_women_btn);
        btnMen = view.findViewById(R.id.frag_shop_men_btn);
        btnAppliance = view.findViewById(R.id.frag_shop_appliance_btn);
        btnMakeUp = view.findViewById(R.id.frag_shop_makeUp_btn);
        btnFood = view.findViewById(R.id.frag_shop_food_btn);
        btnFurniture = view.findViewById(R.id.frag_shop_furniture_btn);


        btnWomen.setOnClickListener(onClickListener);
        btnMen.setOnClickListener(onClickListener);
        btnAppliance.setOnClickListener(onClickListener);
        btnMakeUp.setOnClickListener(onClickListener);
        btnFood.setOnClickListener(onClickListener);
        btnFurniture.setOnClickListener(onClickListener);



        recyclerView = (RecyclerView) view.findViewById(R.id.rv_shop);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));





        String tableName = "shopWomen";
        getDatabase(tableName);
        shopAdapter = new ShopAdapter(list, getActivity());
        recyclerView.setAdapter(shopAdapter);

        shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
            @Override
            public void onTouch(View v, int position) {

                if (position == 0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.midasb.co.kr/"));
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com/"));
                    startActivity(intent);
                }

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
        setBtnBackColor(btnWomen);

        return view;
    }

    private void getDatabase(String tableName) {

        // DB 구현
        DBHelper dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name,tag,photo from " + tableName, null);

        list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ShopInfo shopInfo = new ShopInfo();
            shopInfo.setName(cursor.getString(0));
            shopInfo.setTag(cursor.getString(1));
            shopInfo.setPhoto(cursor.getString(2));


            list.add(shopInfo);
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.frag_shop_women_btn: {
                    setBtnBackColor(btnWomen);
                    String tableName = "shopWomen";
                    getDatabase(tableName);
                    shopAdapter = new ShopAdapter(list, getActivity());
                    recyclerView.setAdapter(shopAdapter);
                    shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
                        @Override
                        public void onTouch(View v, int position) {

                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.midasb.co.kr/"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com/"));
                                startActivity(intent);
                            }

                        }
                    });
                    break;
                }
                case R.id.frag_shop_men_btn:{
                    setBtnBackColor(btnMen);
                    String tableName = "shopMen";
                    getDatabase(tableName);
                    shopAdapter = new ShopAdapter(list, getActivity());
                    recyclerView.setAdapter(shopAdapter);

                    shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
                        @Override
                        public void onTouch(View v, int position) {

                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.midasb.co.kr/"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com/"));
                                startActivity(intent);
                            }

                        }
                    });
                    break;
                }
                case R.id.frag_shop_appliance_btn:{
                    setBtnBackColor(btnAppliance);
                    String tableName = "shopMen";
                    getDatabase(tableName);
                    shopAdapter = new ShopAdapter(list, getActivity());
                    recyclerView.setAdapter(shopAdapter);

                    shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
                        @Override
                        public void onTouch(View v, int position) {

                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.midasb.co.kr/"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com/"));
                                startActivity(intent);
                            }

                        }
                    });
                    break;
                }
                case R.id.frag_shop_makeUp_btn:{
                    setBtnBackColor(btnMakeUp);
                    String tableName = "shopMen";
                    getDatabase(tableName);
                    shopAdapter = new ShopAdapter(list, getActivity());
                    recyclerView.setAdapter(shopAdapter);

                    shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
                        @Override
                        public void onTouch(View v, int position) {

                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.midasb.co.kr/"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com/"));
                                startActivity(intent);
                            }

                        }
                    });
                    break;
                }
                case R.id.frag_shop_food_btn:{
                    setBtnBackColor(btnFood);
                    String tableName = "shopMen";
                    getDatabase(tableName);
                    shopAdapter = new ShopAdapter(list, getActivity());
                    recyclerView.setAdapter(shopAdapter);

                    shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
                        @Override
                        public void onTouch(View v, int position) {

                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.midasb.co.kr/"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com/"));
                                startActivity(intent);
                            }

                        }
                    });
                    break;
                }
                case R.id.frag_shop_furniture_btn:{
                    setBtnBackColor(btnFurniture);
                    String tableName = "shopMen";
                    getDatabase(tableName);
                    shopAdapter = new ShopAdapter(list, getActivity());
                    recyclerView.setAdapter(shopAdapter);

                    shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
                        @Override
                        public void onTouch(View v, int position) {

                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.midasb.co.kr/"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com/"));
                                startActivity(intent);
                            }

                        }
                    });
                    break;
                }



            }
        }
    };
//    private void startActivity(Class c){
//        Intent intent = new Intent(getContext(),c);
//        startActivity(intent);
//    }


    // 해당하는 버튼의 배경색 변경
    public void setBtnBackColor(LinearLayout pointed){
        btnWomen.setBackgroundResource(R.drawable.style_main_fill);
        btnMen.setBackgroundResource(R.drawable.style_main_fill);
        btnAppliance.setBackgroundResource(R.drawable.style_main_fill);
        btnMakeUp.setBackgroundResource(R.drawable.style_main_fill);
        btnFood.setBackgroundResource(R.drawable.style_main_fill);
        btnFurniture.setBackgroundResource(R.drawable.style_main_fill);
        pointed.setBackgroundResource(R.drawable.style_main_fill_yellow);

    }

}
