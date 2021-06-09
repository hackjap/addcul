package com.jsp.addcul.fragment;

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

import com.jsp.addcul.Util.DBHelper;
import com.jsp.addcul.R;
import com.jsp.addcul.DTO.ShopInfo;
import com.jsp.addcul.adapter.ShopAdapter;

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
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://display.musinsa.com/display/brands/mahagrid/goods?gclid=Cj0KCQjw2NyFBhDoARIsAMtHtZ4owa1L1VRtdeN-Y_j-2Fp6AiWDFVLB5KcR-86RG3DjqO59ufieA5caAmebEALw_wcB"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jogunshop.com/"));
                                startActivity(intent);
                            }else if (position == 2) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://byther.kr/"));
                                startActivity(intent);
                            }else if (position == 3) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://modernif.co.kr/"));
                                startActivity(intent);
                            }else if (position == 4) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.superstari.co.kr/?gclid=Cj0KCQjw2NyFBhDoARIsAMtHtZ4EP10SYD7u84b8NOY_6cyCRppUySVPRdLILScA05qOkcqlblvPg54aAoY4EALw_wcB"));
                                startActivity(intent);
                            } else if (position == 5) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://locker-room.co.kr/"));
                                startActivity(intent);
                            }
                        }
                    });
                    break;
                }
                case R.id.frag_shop_appliance_btn:{
                    setBtnBackColor(btnAppliance);
                    String tableName = "shopApp";
                    getDatabase(tableName);
                    shopAdapter = new ShopAdapter(list, getActivity());
                    recyclerView.setAdapter(shopAdapter);

                    shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
                        @Override
                        public void onTouch(View v, int position) {

                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.danawa.com/?src=adwords&kw=GA0000020&gclid=Cj0KCQjw2NyFBhDoARIsAMtHtZ4a0q2xeoXczP9z2FeNGdSwmXnCyp6OrzVdOuHJzVdsyRd3zEAizMAaArksEALw_wcB"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.samsung.com/sec/"));
                                startActivity(intent);
                            }else if (position == 2) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lge.co.kr/"));
                                startActivity(intent);
                            }else if (position == 3) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.e-himart.co.kr/app/display/showDisplayShop?originReferrer=himartindex"));
                                startActivity(intent);
                            }else if (position == 4) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.etlandmall.co.kr/mobile/main/index.do"));
                                startActivity(intent);
                            }

                        }
                    });
                    break;
                }
                case R.id.frag_shop_makeUp_btn:{
                    setBtnBackColor(btnMakeUp);
                    String tableName = "shopMake";
                    getDatabase(tableName);
                    shopAdapter = new ShopAdapter(list, getActivity());
                    recyclerView.setAdapter(shopAdapter);

                    shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
                        @Override
                        public void onTouch(View v, int position) {

                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mynunc.com/brand/missha?utm_source=google&utm_medium=sa&utm_campaign=N%5FBRAND&utm_term=%EB%AF%B8%EC%83%A4&_C_=611&_AT=036B126E018002FE8732&gclid=Cj0KCQjw--GFBhDeARIsACH_kdaxWwY-_8YA6xJkujjlhlR0__61VHWYPtRrGKEXqFl2O5ijOgWyK5EaAiMwEALw_wcB"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tonystreet.com/"));
                                startActivity(intent);
                            }else if (position == 2) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naturecollection.com/mall/brand.jsp?cate_seq=662"));
                                startActivity(intent);
                            }else if (position == 3) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.innisfree.com/kr/ko/Main.do?utm_campaign=madit_conversion_al&utm_medium=sa&utm_source=google&utm_content=na&utm_trg=brand&utm_term=%EC%9D%B4%EB%8B%88%EC%8A%A4%ED%94%84%EB%A6%AC&gclid=Cj0KCQjw--GFBhDeARIsACH_kdY3im6DO5yx-IocL58vijz82crmPlErsP196fvhwcLuxGJNdOg0tT8aAnl7EALw_wcB"));
                                startActivity(intent);
                            }else if (position == 4) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.etude.com/kr/ko/main"));
                                startActivity(intent);
                            }

                        }
                    });
                    break;
                }
                case R.id.frag_shop_food_btn:{
                    setBtnBackColor(btnFood);
                    String tableName = "shopFood";
                    getDatabase(tableName);
                    shopAdapter = new ShopAdapter(list, getActivity());
                    recyclerView.setAdapter(shopAdapter);

                    shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
                        @Override
                        public void onTouch(View v, int position) {

                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://emart.ssg.com/specialStore/nobrand/main.ssg?ckwhere=e_ggbr&EKAMS=google.272.5085.20504.2034359.602460933&trackingDays=1&gclid=Cj0KCQjw--GFBhDeARIsACH_kdaawBbcqDaHs4Ya851IEbt6vSdegfm1zGMbkl5ag3g-q-P5mY9P4K0aAksxEALw_wcB"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://front.homeplus.co.kr/leaflet?gnbNo=92"));
                                startActivity(intent);
                            }else if (position == 2) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.coupang.com/np/categories/194276"));
                                startActivity(intent);
                            }else if (position == 3) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kurly.com/shop/main/index.php?&utm_source=1055&utm_medium=2106&utm_campaign=joinpage&utm_term=%EC%BB%AC%EB%A6%AC&utm_content=brand&gclid=Cj0KCQjw--GFBhDeARIsACH_kdaA7PL4k1um_cf_qq5i9yNmtjm7ly3LKyDXQbrggZRmNuvq49LuHsYaAsUxEALw_wcB"));
                                startActivity(intent);
                            }else if (position == 4) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://foodzim.co.kr/?gclid=Cj0KCQjw--GFBhDeARIsACH_kdYaT1YnDxuMF0FT0AjA8vUKYfgKtTh97QOdOWr_BSj6ACWE4WFyWBQaAligEALw_wcB"));
                                startActivity(intent);
                            }

                        }
                    });
                    break;
                }
                case R.id.frag_shop_furniture_btn:{
                    setBtnBackColor(btnFurniture);
                    String tableName = "shopFurni";
                    getDatabase(tableName);
                    shopAdapter = new ShopAdapter(list, getActivity());
                    recyclerView.setAdapter(shopAdapter);

                    shopAdapter.setOnMyTouchListener(new ShopAdapter.OnMyTouchListener() {
                        @Override
                        public void onTouch(View v, int position) {

                            if (position == 0) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ikea.com/kr/ko/?utm_source=google&utm_medium=cpc&utm_campaign=sa_go_pc&utm_content=1-1.%EB%B8%8C%EB%9E%9C%EB%93%9C_%EB%B8%8C%EB%9E%9C%EB%93%9C&utm_term=%EC%9D%B4%EC%BC%80%EC%95%84&gclid=Cj0KCQjw--GFBhDeARIsACH_kdZZlqNMfOjwtMvvpc6Jjq5cBVVzYUfxv3bNo2nUKEX19rX_QcM-ZFYaAvDsEALw_wcB"));
                                startActivity(intent);
                            } else if (position == 1) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ohou.se/store?utm_source=brand_google&utm_medium=cpc&utm_campaign=commerce&utm_content=e&utm_term=%EC%98%A4%EB%8A%98%EC%9D%98%EC%A7%91&source=14&affect_type=UtmUrl&gclid=Cj0KCQjw--GFBhDeARIsACH_kdbSn1AMl7bwwUlssYlgN9BvgVGQkJAnvbiZgw_FV6jB6bDdeA8keDUaAsHjEALw_wcB"));
                                startActivity(intent);
                            }else if (position == 2) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.daisomall.co.kr/"));
                                startActivity(intent);
                            }else if (position == 3) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.modernhousemall.com/main/index.php?utm_source=google&utm_medium=SA_PC&utm_campaign=modernhouse&utm_term=%EB%AA%A8%EB%8D%98%ED%95%98%EC%9A%B0%EC%8A%A4"));
                                startActivity(intent);
                            }else if (position == 4) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.guud.com/index?utm_source=google&utm_campaign=searchad&utm_medium=cpc&utm_term=%EA%B9%8C%EC%82%AC%EB%AF%B8%EC%95%84&epe_vid=103&epe_tcd=156645561&gclid=Cj0KCQjw--GFBhDeARIsACH_kdbYnWnGuH4e8VchxCeDqksunwpHv3-AZK1pJoTVbnsE7egRRn2zwfIaAvokEALw_wcB"));
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
