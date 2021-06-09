package com.jsp.addcul.activity.googlemap;

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
import com.jsp.addcul.DTO.CultureMAPInfo;
import com.jsp.addcul.DTO.EmergencyMAPInfo;
import com.jsp.addcul.DTO.WelfareMAPInfo;
import com.jsp.addcul.R;
import com.jsp.addcul.activity.config.BasicActivity;
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
    int MARKER_FLAG = 0;
    //nav_category
    TextView btnCategoryArt, btnCategoryMuseum, btnMyLocation,
            navCategoryTvConsertholl, navCategoryTvLibrary, navCategoryTvArtcenter, navCategoryTvEtc;
    ImageView imgCall, imgUrl;
    LinearLayout mapExplainContainer;
    LatLng latLng;
    Location location;

    String url; // 최종 url
    String defaultUrl; // 기본 url
    String myAPIKey; // api 키
    String result; // parsing 결과
    String address; // 주소

    Marker marker1, marker2, marker3;
    int nearPostion = 0;
    int partPosition = 0;

    //volley
    RequestQueue requestQueue;
    StringRequest stringRequest;

    //
    ArrayList<CultureMAPInfo> data;
    ArrayList<WelfareMAPInfo> welefareData;
    ArrayList<EmergencyMAPInfo>emergencyData;

    // backbutton
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;


    //local-footer
    FrameLayout kCultureNav;
    FrameLayout welefareNav;

    LinearLayout footerTvKculture;
    LinearLayout footerTvWelefare;
    LinearLayout footerTvEmergency;

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
        footerTvKculture = (LinearLayout) findViewById(R.id.map_footer_tv_kculture);
        footerTvWelefare = (LinearLayout) findViewById(R.id.map_footer_tv_welefare);
        footerTvEmergency = (LinearLayout) findViewById(R.id.map_footer_tv_emergency);

        localFooterClickAction(); // localfooter 클릭 시 동작


        // global - footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_search).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_home).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_map).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_my_info).setOnClickListener(onFooterlistner);

        ImageView ivMap = (ImageView) findViewById(R.id.img_map);
        TextView tvMap = (TextView) findViewById(R.id.tv_map);
        ivMap.setImageResource(R.drawable.img_bar_map_yellow);


        data = new ArrayList<>();   // data 객체 초기화
        welefareData = new ArrayList<>();
        emergencyData = new ArrayList<>();

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
        cutureRun();


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
    private void emergencyRun() {    // makeRequest

        url = "http://openapi.seoul.go.kr:8088/73555766486a616e38336d64466f53/json/TvEmgcHospitalInfo/1/100/";

        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // 데이터 파싱 및 객체 가져오기
                        setEmergencyJason(response);

                        //마커 표시
                        emergencyMarkerService(emergencyData);
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.55512522440527, 126.96981185690053), 12));
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
    private void welfareRun() {    // makeRequest

        url = "https://openapi.gg.go.kr/MultipleCulturesFamilySupport?key=59bf4a04d0be4d56b741d21f0c9a2ee9&type=json&pIndex=1&pSize=100";

        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        setWelfareJason(response); // data 객체 데이터 값 삽입

                        // latLng = new LatLng(welefareData.get(0).getLatitude(), welefareData.get(0).getLongitude());
                        //마커 표시
                        welefareMarkerService(welefareData);
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.55512522440527, 126.96981185690053), 10));
                        // locationServices(); // location에 내 위치 저장
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

    private void cutureRun() {    // makeRequest

        String listCount = "1/300/";
        defaultUrl = "http://openapi.seoul.go.kr:8088/";
        myAPIKey = "73555766486a616e38336d64466f53";
        url = defaultUrl + myAPIKey + "/json/culturalSpaceInfo/" + listCount;

        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        setCultureJason(response); // data 객체 데이터 값 삽입

                        latLng = new LatLng(data.get(0).getLatitude(), data.get(0).getLongitude());

                        LatLng testLocation = new LatLng(37.566680508881475, 126.99758279442203);  //37.566680508881475, 126.99758279442203 을지로4가역

                        //  map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14)); // 기본 설정 위치
                        //map.setMyLocationEnabled(true);
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
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.55512522440527, 126.96981185690053), 11));
                            }
                        });
                        btnCategoryMuseum.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "박물관/기념관");
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.55512522440527, 126.96981185690053), 11));
                            }
                        });
                        navCategoryTvConsertholl.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "공연장");
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.55512522440527, 126.96981185690053), 11));
                            }
                        });
                        navCategoryTvLibrary.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "도서관");
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.55512522440527, 126.96981185690053), 11));
                            }
                        });
                        navCategoryTvArtcenter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "문화예술회관");
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.55512522440527, 126.96981185690053), 11));
                            }
                        });
                        navCategoryTvEtc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                markerService(data, "기타");
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.55512522440527, 126.96981185690053), 11));
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
        if(MARKER_FLAG ==0)
            getKultureContents(markerPosition);
        else if(MARKER_FLAG == 1)
            getWelfareContents(markerPosition);
        else if(MARKER_FLAG == 2)
            getEmergencyContents(markerPosition);

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

    public void getKultureContents(final int position) {
        MARKER_FLAG = 0;
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
    }

        public void getWelfareContents(final int position) {
            MARKER_FLAG = 1;
            tvCategory.setText("");
            tvName.setText(welefareData.get(position).getName());
            tvAddress.setText(welefareData.get(position).getAddr());
            // Log.e("포지션 : ",partPosition+" ");
            imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 전화 연결
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + welefareData.get(position).getpNum()));
                    startActivity(intent);
                }
            });
            imgUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    //url 연결
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(welefareData.get(position).getHomepage()));
//                    startActivity(intent);
                }
            });
        }
            public void getEmergencyContents(final int position) {
                MARKER_FLAG = 2;
                tvCategory.setText(emergencyData.get(position).getCategory());
                tvName.setText(emergencyData.get(position).getName());
                tvAddress.setText(emergencyData.get(position).getAddr());
                // Log.e("포지션 : ",partPosition+" ");
                imgCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 전화 연결
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + emergencyData.get(position).getpNum()));
                        startActivity(intent);
                    }
                });
                imgUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                    //url 연결
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(welefareData.get(position).getHomepage()));
//                    startActivity(intent);
                    }
                });

    }

    public void emergencyMarkerService(ArrayList<EmergencyMAPInfo> EData) {
        map.clear();
        for (int i = 0; i < EData.size(); i++) {

            double latitude = EData.get(i).getLatitude();
            double longitude = EData.get(i).getLongitude();

            LatLng welfareLatLng = new LatLng(latitude,longitude);

            Log.e("LATLATTIDUE1 :", welfareLatLng + "");
            Log.e("LATLATTIDUE2 :", latitude + "");
            Log.e("LATLATTIDUE3 :", longitude + "");

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(welfareLatLng).title(EData.get(i)
                            .getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            marker2 = map.addMarker(markerOptions);
            marker2.setTag(i);
            marker2.showInfoWindow();
            getEmergencyContents(i);
        }
        map.setOnMarkerClickListener(this);

    }


    public void welefareMarkerService(ArrayList<WelfareMAPInfo> wData) {
        map.clear();
        for (int i = 0; i < wData.size(); i++) {

            double latitude = wData.get(i).getLatitude();
            double longitude = wData.get(i).getLongitude();

            LatLng welfareLatLng = new LatLng(latitude,longitude);

            Log.e("LATLATTIDUE1 :", welfareLatLng + "");
            Log.e("LATLATTIDUE2 :", latitude + "");
            Log.e("LATLATTIDUE3 :", longitude + "");

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(welfareLatLng).title(wData.get(i)
                            .getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            marker2 = map.addMarker(markerOptions);
            marker2.setTag(i);
            marker2.showInfoWindow();
           getWelfareContents(i);
        }
        map.setOnMarkerClickListener(this);

    }

    public void markerService(ArrayList<CultureMAPInfo> cData, String mapCategory) {
//       int categoryPostion=0;

        map.clear();
        for (int i = 0; i < cData.size(); i++) {

            String category = cData.get(i).getCategory();
            Log.e("Test Category : ", category);
            Log.e("Test MAPCategory : ", mapCategory);
            if (mapCategory.equals(category)) {
                Log.e("Test Position : ", "111");
                LatLng cultureLatLng = new LatLng(cData.get(i).getLatitude(), cData.get(i).getLongitude());
                Log.e("LATLATTIDUE2 :", latLng.toString());
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(cultureLatLng).title(cData.get(i)
                                .getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                marker1 = map.addMarker(markerOptions);
                marker1.setTag(i);
                marker1.showInfoWindow();


                getKultureContents(i);
            }


            /*
            tvName.setText("이름 : " + data.get(i).getName());
            tvAddress.setText("주소 : " + data.get(i).getAddr());
            tvLatlng.setText("위도 : " + latitude + "\n경도 : " + longitude);

             */
        }
        map.setOnMarkerClickListener(this);

    }

    public void setEmergencyJason(String response) {

        try {

            JSONObject jsonObject = new JSONObject(response);
            Log.e("getJason1 : ", jsonObject.toString());
            String tvEmgcHospitalInfo = jsonObject.getString("TvEmgcHospitalInfo");
            Log.e("getJason2 : ", tvEmgcHospitalInfo);
            JSONObject subObject = new JSONObject(tvEmgcHospitalInfo);
            Log.e("getJason3 : ", subObject.toString());
            String row = subObject.getString("row");

            JSONArray rowArray = new JSONArray(row);
            Log.e("getJason7 : ", rowArray.toString());
            for (int i = 0; i < rowArray.length(); i++) {
                String index = rowArray.getString(i);
                JSONObject emergency = new JSONObject(index);

                String category = emergency.getString("DUTYDIVNAM");
                String name = emergency.getString("DUTYNAME");        // 이름
                Log.e("getJason8 : ", name);
                String addr = emergency.getString("DUTYADDR");            // 주소
                String pNum = emergency.getString("DUTYTEL3");            // 전화번호
                double latitude = emergency.getDouble("WGS84LAT");    // 위도
                double longitude = emergency.getDouble("WGS84LON");  // 경도

                emergencyData.add(new EmergencyMAPInfo(category,name, addr, pNum, latitude, longitude));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setWelfareJason(String response) {

        try {

            JSONObject jsonObject = new JSONObject(response);
            Log.e("getJason1 : ", jsonObject.toString());
            String multipleCulturesFamilySupport = jsonObject.getString("MultipleCulturesFamilySupport");
            Log.e("getJason2 : ", multipleCulturesFamilySupport);
            JSONArray jsonArray = new JSONArray(multipleCulturesFamilySupport);
            Log.e("getJason3 : ", jsonArray.toString());
            String firstIndex = jsonArray.get(1).toString();
            Log.e("getJason4 : ", firstIndex);
            JSONObject rowObject = new JSONObject(firstIndex);
            Log.e("getJason5 : ", rowObject.toString());
            String row = rowObject.getString("row");
            Log.e("getJason6 : ", row);


            JSONArray rowArray = new JSONArray(row);
            Log.e("getJason7 : ", rowArray.toString());
            for (int i = 0; i < rowArray.length(); i++) {
                String index = rowArray.getString(i);
                JSONObject welfare = new JSONObject(index);
                String name = welfare.getString("INST_GRP_NM");        // 이름
                Log.e("getJason8 : ", name);
                String addr = welfare.getString("REFINE_LOTNO_ADDR");            // 주소
                String pNum = welfare.getString("CONTCT_NO");            // 전화번호
                double latitude = welfare.getDouble("REFINE_WGS84_LAT");    // 위도
                double longitude = welfare.getDouble("REFINE_WGS84_LOGT");  // 경도

                welefareData.add(new WelfareMAPInfo(name, addr, pNum, latitude, longitude));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setCultureJason(String response) {

        try {

            JSONObject jsonObject = new JSONObject(response);
            //Log.e("getJason1 : ", jsonObject.toString());
            String culturalSpaceInfo = jsonObject.getString("culturalSpaceInfo");
            // Log.e("getJason2 : ", culturalSpaceInfo);
            JSONObject subObject = new JSONObject(culturalSpaceInfo);
            // Log.e("getJason3 : ", subObject.toString());
            String row = subObject.getString("row");
            // Log.e("getJason4 : ", row);
            JSONArray jsonArray = new JSONArray(row);
            // Log.e("getJason4 : ", jsonArray.toString());


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
                footerTvEmergency.setBackgroundResource(R.drawable.default_back);
            }
        });
        footerTvWelefare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                welefareNav.setVisibility(View.VISIBLE);
                kCultureNav.setVisibility(View.GONE);
                //part_up_round_back
                footerTvWelefare.setBackgroundResource(R.drawable.part_up_round_back);
                footerTvKculture.setBackgroundResource(R.drawable.default_back);
                footerTvEmergency.setBackgroundResource(R.drawable.default_back);
                welfareRun();
            }
        });
        footerTvEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kCultureNav.setVisibility(View.GONE);
                welefareNav.setVisibility(View.GONE);
                //part_up_round_back
                footerTvEmergency.setBackgroundResource(R.drawable.part_up_round_back);
                footerTvKculture.setBackgroundResource(R.drawable.default_back);
                footerTvWelefare.setBackgroundResource(R.drawable.default_back);
                emergencyRun();
            }
        });
    }
}
