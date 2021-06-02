package com.example.addcul.activity.googlemap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.addcul.DTO.CultureMAPInfo;
import com.example.addcul.R;
import com.example.addcul.activity.config.BasicActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// README
/*
 *  getMapAsync()
 *  run(map) : 아래 기능 실행
 *      locationServices : 위치에 따른 위도,경도,날씨 값 리턴
 *      geocoderServices : 위치에 따른 주소 값 리턴
 *      markerServices : 마커 생성
 *      makeRequest : Volley 통신 후 url의 string 을 반환
 */
public class MapActivity extends BasicActivity implements AutoPermissionsListener, GoogleMap.OnMarkerClickListener {

    SupportMapFragment mapFragment;
    GoogleMap map;
    TextView tvAddress, tvCategory, tvName, tvHomepage, tvpNum;

    //nav_category
    TextView btnCategoryArt, btnCategoryMuseum, btnMyLocation,
            navCategoryTvConsertholl,navCategoryTvLibrary,navCategoryTvArtcenter,navCategoryTvEtc;
    ImageView imgCall, imgUrl;
    LinearLayout mapExplainContainer;
    LatLng latLng;
    Location location;

    String url; // 최종 url
    String defaultUrl; // 기본 url
    String myAPIKey; // api 키
    String result; // parsing 결과
    String address; // 주소

    Marker marker;
    int nearPostion = 0;
    int partPosition = 0;

    //volley
    RequestQueue requestQueue;
    StringRequest stringRequest;

    //
    ArrayList<CultureMAPInfo> data;

    // backbutton
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;


    //local-footer
    FrameLayout kCultureNav;
    FrameLayout welefareNav;

    TextView footerTvKculture;
    TextView footerTvWelefare;
    TextView footerTvMychoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // navCategory
        btnCategoryArt = (TextView) findViewById(R.id.btn_category_art);
        btnCategoryMuseum = (TextView) findViewById(R.id.btn_category_museum);
//        btnMyLocation = (TextView) findViewById(R.id.btn_location);
        navCategoryTvConsertholl = (TextView) findViewById(R.id.nav_category_tv_concert_hall);  // 공연장
        navCategoryTvLibrary = (TextView) findViewById(R.id.nav_category_tv_library);  // 도서관
        navCategoryTvArtcenter = (TextView) findViewById(R.id.nav_category_tv_artcenter);  // 문화 예술회관
        navCategoryTvEtc = (TextView) findViewById(R.id.nav_category_tv_etc);  // 기타


        // data
        tvCategory = (TextView) findViewById(R.id.tv_category);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvAddress = (TextView) findViewById(R.id.tv_address);
//        tvpNum = (TextView) findViewById(R.id.tv_pnum);
//        tvHomepage = (TextView) findViewById(R.id.tv_homepage);
        imgCall = (ImageView) findViewById(R.id.img_call);
        imgUrl = (ImageView) findViewById(R.id.img_url);
        mapExplainContainer = (LinearLayout) findViewById(R.id.map_explain_container);

        // local-footer - Binding
        kCultureNav = (FrameLayout) findViewById(R.id.map_frame_kculture_nav);
        welefareNav = (FrameLayout) findViewById(R.id.map_frame_welefare_nav);
        footerTvKculture = (TextView) findViewById(R.id.map_footer_tv_kculture);
        footerTvWelefare = (TextView) findViewById(R.id.map_footer_tv_welefare);
        footerTvMychoice = (TextView) findViewById(R.id.map_footer_tv_my_choice);

        localFooterClickAction(); // localfooter 클릭 시 동작


        // global - footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_home).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_map).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_my_info).setOnClickListener(onFooterlistner);

        ImageView ivMap = (ImageView) findViewById(R.id.img_map);
        TextView tvMap = (TextView) findViewById(R.id.tv_map);
        ivMap.setImageResource(R.drawable.img_bar_map_yellow);


        defaultUrl = "http://openapi.seoul.go.kr:8088/";
        myAPIKey = "73555766486a616e38336d64466f53";

        data = new ArrayList<>();   // data 객체 초기화

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        AutoPermissions.Companion.loadAllPermissions(this, 101); // auto permission

        if (firebaseUser == null) { // 로그인 상태가 아닐때

        } else {
            TextView tvMyinfo = (TextView) findViewById(R.id.tv_my_info);
            ImageView ivMyinfo = (ImageView) findViewById(R.id.img_my_info);
            ivMyinfo.setImageResource(R.drawable.ic_account_circle_black_24dp);
            tvMyinfo.setText("내정보");

        }

        // 구글맵 비동기 로드
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                // 37.55512522440527, 126.96981185690053 서울역
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.55512522440527, 126.96981185690053), 12));
                map.setMyLocationEnabled(true); // 내위치 설정
                //run();

            }
        });


        // 현재 위치 정보 가져오기 버튼 동작
        run();


    } // end of onCreate

    @Override
    public void onBackPressed() {

        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            finish();
        } else {
            backPressedTime = tempTime;
            // Toast.makeText(getApplicationContext(),"종료 ",Toast.LENGTH_SHORT).show();
            mapExplainContainer.setVisibility(View.GONE);
        }

    }

    private void run() {    // makeRequest
        String listCount = "1/200/";
        url = defaultUrl + myAPIKey + "/json/culturalSpaceInfo/" + listCount;
        Log.e("Location : ", url);
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // tvWeather.setText("날씨 : \n"+response);


                        setJason(response); // data 객체 데이터 값 삽입

                        latLng = new LatLng(data.get(0).getLatitude(), data.get(0).getLongitude());


                        LatLng testLocation = new LatLng(37.566680508881475, 126.99758279442203);  //37.566680508881475, 126.99758279442203 을지로4가역


                        //  map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14)); // 기본 설정 위치
                        map.setMyLocationEnabled(true);
                        locationServices(); // location에 내 위치 저장

                        // 현재 내 위치
                        Double myLatitude = location.getLatitude();
                        Double myLongitude = location.getLongitude();
                        // Double myLatitude = testLocation.latitude;
                        //Double myLongitude =testLocation.longitude;


                        Double oper;
                        Double min = 99999.0;
                        for (int i = 0; i < data.size(); i++) {
                            oper = ((myLatitude - data.get(i).getLatitude()) + (myLongitude - data.get(i).getLongitude()));

                            // 거리 비교를 위해 절대값 처리
                            if (oper <= 0) {
                                oper = oper * -1;
                            } else {
                                oper = oper * 1;
                            }
                            Log.e("TestOper : ", oper.toString());
                            Log.e("Testmin : ", min.toString());

                            if (oper < min) {

                                min = oper;
                                nearPostion = i;

                            }
                        }

//                        btnMyLocation.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(data.get(nearPostion).getLatitude(),data.get(nearPostion).getLongitude()),13));
//                            }
//                        });


                        // map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude,myLongitude),15));
                        //Log.e("내 위치 위도 : ",myLatitude.toString());

                        // nav_category 버튼 동작 : 미술관 마커표시
                        btnCategoryArt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "미술관");

                            }
                        });
                        btnCategoryMuseum.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "박물관/기념관");
                            }
                        });
                        navCategoryTvConsertholl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "공연장");
                            }
                        });
                        navCategoryTvLibrary.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "도서관");
                            }
                        });
                        navCategoryTvArtcenter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "문화예술회관");
                            }
                        });
                        navCategoryTvEtc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "기타");
                            }
                        });


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvName.setText(error.getMessage());
                    }
                }
        ); // end of stringRequest argument
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Integer markerPosition = (Integer) marker.getTag();  // 마커에서 데이터 검색
        getContents(markerPosition);
        mapExplainContainer.setVisibility(View.VISIBLE);

        // textViewChange(i);
        /* 기본 예시
        Integer clickCount = (Integer) marker.getTag();  // 마커에서 데이터 검색
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            "has been clicked" + clickCount + "times",
                    Toast.LENGTH_SHORT).show();
        }
         */


        return false;
    }

    public void getContents(final int position) {
        tvCategory.setText(data.get(position).getCategory());
        tvName.setText(data.get(position).getName());
        tvAddress.setText(data.get(position).getAddr());
        // Log.e("포지션 : ",partPosition+" ");
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전화 연결
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.get(position).getpNum()));
                startActivity(intent);
            }
        });
        imgUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //url 연결
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(position).getHomepage()));
                startActivity(intent);
            }
        });


        // tvHomepage.setText("홈페이지 : "+ data.get(position).getHomepage());
        //tvpNum.setText("전화번호 : " + data.get(position).getpNum());
    }

    public void markerService(ArrayList<CultureMAPInfo> data, String mapCategory) {
//       int categoryPostion=0;

        map.clear();
        for (int i = 0; i < data.size(); i++) {

            String category = data.get(i).getCategory();
            Log.e("Test Category : ", category);
            Log.e("Test MAPCategory : ", mapCategory);
            if (mapCategory.equals(category)) {
                Log.e("Test Position : ", "111");
                latLng = new LatLng(data.get(i).getLatitude(), data.get(i).getLongitude());

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng).title(data.get(i)
                                .getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                marker = map.addMarker(markerOptions);
                marker.setTag(i);


                getContents(i);
            }


            /*
            tvName.setText("이름 : " + data.get(i).getName());
            tvAddress.setText("주소 : " + data.get(i).getAddr());
            tvLatlng.setText("위도 : " + latitude + "\n경도 : " + longitude);


             */
        }
        map.setOnMarkerClickListener(this);

    }


    public void setJason(String response) {

        try {
            Log.e("JSonData : ", "33333333333333333333333333333333");
            Log.e("getJason : ", response);

            JSONObject jsonObject = new JSONObject(response);
            String culturalSpaceInfo = jsonObject.getString("culturalSpaceInfo");
            JSONObject subObject = new JSONObject(culturalSpaceInfo);

            String row = subObject.getString("row");
            JSONArray jsonArray = new JSONArray(row);

            for (int i = 0; i < jsonArray.length(); i++) {
                String index = jsonArray.getString(i);
                JSONObject culture = new JSONObject(index);
                String category = culture.getString("SUBJCODE");    // 종류
                String name = culture.getString("FAC_NAME");        // 이름
                String addr = culture.getString("ADDR");            // 주소
                String pNum = culture.getString("PHNE");            // 전화번호
                String hompage = culture.getString("HOMEPAGE");
                double latitude = culture.getDouble("X_COORD");    // 위도
                double longitude = culture.getDouble("Y_COORD");  // 경도

                data.add(new CultureMAPInfo(category, name, addr, pNum, hompage, latitude, longitude));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void geocoderServices(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 8);
            //geocoder.
            if (list != null) {
                if (list.size() == 0) {
                    // tv.setText("주소 정보 없음");
                } else {
                    address = list.get(0).getAddressLine(0).toString();
                    tvAddress.setText("주소 : " + address);
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void locationServices() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {

            }
        } catch (SecurityException e) {
            tvAddress.setText(e.getMessage());
        }

        GPSListener gpsListener = new GPSListener();
        long minTime = 5000;//5초
        float minDistance = 0;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);

    }


    class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            // 실습을 위해 실시간이 아닌 버튼을 눌러야 동작할 수 있게 설정하겠음.
            // latitude = location.getLatitude();
            // longitude = location.getLongitude();
            //tvLatlng.setText("위도:"+latitude+"\n경도:"+longitude);

        }
    }


    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }

    public void localFooterClickAction() {
        footerTvKculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kCultureNav.setVisibility(View.VISIBLE);
                welefareNav.setVisibility(View.GONE);
                //part_up_round_back
                footerTvKculture.setBackgroundResource(R.drawable.part_up_round_back);
                footerTvWelefare.setBackgroundResource(R.drawable.default_back);
                footerTvMychoice.setBackgroundResource(R.drawable.default_back);
            }
        });
        footerTvWelefare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welefareNav.setVisibility(View.VISIBLE);
                kCultureNav.setVisibility(View.GONE);
                //part_up_round_back
                footerTvWelefare.setBackgroundResource(R.drawable.part_up_round_back);
                footerTvKculture.setBackgroundResource(R.drawable.default_back);
                footerTvMychoice.setBackgroundResource(R.drawable.default_back);
            }
        });
        footerTvMychoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kCultureNav.setVisibility(View.GONE);
                welefareNav.setVisibility(View.GONE);
                //part_up_round_back
                footerTvMychoice.setBackgroundResource(R.drawable.part_up_round_back);
                footerTvKculture.setBackgroundResource(R.drawable.default_back);
                footerTvWelefare.setBackgroundResource(R.drawable.default_back);
            }
        });
    }
}