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
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.croquis.zigzag&referrer=af_tranid%3DaALbSNrhALd2UfOvR5ctCw%26shortlink%3D189189a0%26pid%3Dzigzag_website_aos%26af_click_lookback%3D1d%26af_web_id%3D98a651ce-1f5c-41e4-950c-d741ac07bc7b-o%26utm_source%3Dzigzag_website_aos"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.midasb.co.kr/"));
                                startActivity(intent);
                            }else if (position == 2) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://loveparis.net/"));
                                startActivity(intent);
                            }else if (position == 3) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://justone.co.kr/?enlipleMBDCEnc=bm89MCZrbm89MCZrd3JkU2VxPTI0MzQxOTU1JnM9MjIzNzAmYWRndWJ1bj1HRyZnYj1HRyZzYz01ZGM4ZTA4NDE4NDk0OWRkOTlhOTE4NWViMDJiN2I5YiZtYz0yMjM3MCZ1PWp1c3RvbmUmcHJvZHVjdD1uY3Q="));
                                startActivity(intent);
                            }else if (position == 4) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mocobling.com/"));
                                startActivity(intent);
                            } else if (position == 5) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pippin.co.kr/?da_ref=Yz10UnNFVVI=&enlipleMBDCEnc=bm89MCZrbm89MCZrd3JkU2VxPTI1NTk0NzI4JnM9MjIzNzAmYWRndWJ1bj1HRyZnYj1HRyZzYz01MzVhNDg2MjFmMWM0MDU5OWQ4N2Y4M2RiYTBlNDQxOCZtYz0yMjM3MCZ1PXBpcHBpbjExJnByb2R1Y3Q9bmN0&gclid=Cj0KCQjw2NyFBhDoARIsAMtHtZ4FYF5gtg7oBGGZ6TCGklsQ37amY0XsHQxAU13i8hK0xkIGZcGf-I8aAicYEALw_wcB"));
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
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.croquis.zigzag&referrer=af_tranid%3DaALbSNrhALd2UfOvR5ctCw%26shortlink%3D189189a0%26pid%3Dzigzag_website_aos%26af_click_lookback%3D1d%26af_web_id%3D98a651ce-1f5c-41e4-950c-d741ac07bc7b-o%26utm_source%3Dzigzag_website_aos"));
                                startActivity(intent);
                            }
//                            } else if (position == 1) {
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.midasb.co.kr/"));
//                                startActivity(intent);
//                            }else if (position == 2) {
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://loveparis.net/"));
//                                startActivity(intent);
//                            }else if (position == 3) {
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://justone.co.kr/?enlipleMBDCEnc=bm89MCZrbm89MCZrd3JkU2VxPTI0MzQxOTU1JnM9MjIzNzAmYWRndWJ1bj1HRyZnYj1HRyZzYz01ZGM4ZTA4NDE4NDk0OWRkOTlhOTE4NWViMDJiN2I5YiZtYz0yMjM3MCZ1PWp1c3RvbmUmcHJvZHVjdD1uY3Q="));
//                                startActivity(intent);
//                            }else if (position == 4) {
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mocobling.com/"));
//                                startActivity(intent);
//                            } else if (position == 5) {
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pippin.co.kr/?da_ref=Yz10UnNFVVI=&enlipleMBDCEnc=bm89MCZrbm89MCZrd3JkU2VxPTI1NTk0NzI4JnM9MjIzNzAmYWRndWJ1bj1HRyZnYj1HRyZzYz01MzVhNDg2MjFmMWM0MDU5OWQ4N2Y4M2RiYTBlNDQxOCZtYz0yMjM3MCZ1PXBpcHBpbjExJnByb2R1Y3Q9bmN0&gclid=Cj0KCQjw2NyFBhDoARIsAMtHtZ4FYF5gtg7oBGGZ6TCGklsQ37amY0XsHQxAU13i8hK0xkIGZcGf-I8aAicYEALw_wcB"));
//                                startActivity(intent);
//                            }
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
        btnWomen.setBackgroundResource(R.drawable.style_shop_border);
        btnMen.setBackgroundResource(R.drawable.style_shop_border);
        btnAppliance.setBackgroundResource(R.drawable.style_shop_border);
        btnMakeUp.setBackgroundResource(R.drawable.style_shop_border);
        btnFood.setBackgroundResource(R.drawable.style_shop_border);
        btnFurniture.setBackgroundResource(R.drawable.style_shop_border);

        pointed.setBackgroundResource(R.drawable.style_shop_border_y);


    }
}
