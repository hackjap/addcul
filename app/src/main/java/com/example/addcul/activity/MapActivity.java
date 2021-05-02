package com.example.addcul.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.addcul.CultureMAPInfo;
import com.example.addcul.R;
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
public class MapActivity extends AppCompatActivity implements AutoPermissionsListener, GoogleMap.OnMarkerClickListener {

    SupportMapFragment mapFragment;
    GoogleMap map;
    TextView tvAddress, tvCategory, tvName,tvBusstop,tvSubway,tvpNum;
    Button btn,btnMyLocation;
    LatLng latLng;
    Location location;

    String url; // 최종 url
    String defaultUrl; // 기본 url
    String myAPIKey; // api 키
    String result; // parsing 결과
    String address; // 주소

    int nearPostion=0;

    //volley
    RequestQueue requestQueue;
    StringRequest stringRequest;

    //
    ArrayList<CultureMAPInfo> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        btn = (Button) findViewById(R.id.button);
        btnMyLocation=(Button)findViewById(R.id.btn_location);
        tvCategory = (TextView) findViewById(R.id.tv_category);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvpNum = (TextView)findViewById(R.id.tv_pnum);
        tvBusstop = (TextView)findViewById(R.id.tv_busstop);
        tvSubway = (TextView)findViewById(R.id.tv_subway);


        defaultUrl = "http://openapi.seoul.go.kr:8088/";
        myAPIKey = "73555766486a616e38336d64466f53";

        data = new ArrayList<>();   // data 객체 초기화

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        AutoPermissions.Companion.loadAllPermissions(this, 101); // auto permission

        // 구글맵 비동기 로드
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                // 37.55512522440527, 126.96981185690053 서울역
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.55512522440527,126.96981185690053),15));
                map.setMyLocationEnabled(true); // 내위치 설정
                //run();
            }
        });

        // 현재 위치 정보 가져오기 버튼 동작
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 run();   // 프로세스 실행

            }
        });

        btnMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    } // end of onCreate


    private void run() {    // makeRequest
        String listCount = "1/10/";
        url = defaultUrl + myAPIKey + "/json/culturalSpaceInfo/" + listCount;
        Log.e("Location : ", url);
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // tvWeather.setText("날씨 : \n"+response);


                        setJason(response); // data 객체 데이터 값 삽입

                        latLng = new LatLng(data.get(0).getLatitude(),data.get(0).getLongitude());


                        LatLng testLocation = new LatLng(37.566680508881475,126.99758279442203);  //37.566680508881475, 126.99758279442203 을지로4가역


                      //  map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14)); // 기본 설정 위치
                        map.setMyLocationEnabled(true);
                        locationServices(); // location에 내 위치 저장

                        // 현재 내 위치
                        Double myLatitude = location.getLatitude();
                        Double myLongitude = location.getLongitude();
                        // Double myLatitude = testLocation.latitude;
                         //Double myLongitude =testLocation.longitude;


                            Double oper;
                            Double min =99999.0;
                            for(int i=0;i<data.size();i++){
                                oper = ((myLatitude-data.get(i).getLatitude()) + (myLongitude-data.get(i).getLongitude()));

                                // 거리 비교를 위해 절대값 처리
                                if(oper<=0){
                                    oper = oper*-1;
                                }else{
                                    oper = oper*1;
                                }
                                Log.e("TestOper : ", oper.toString());
                                Log.e("Testmin : ", min.toString());

                                if(oper < min){

                                    min = oper;
                                    nearPostion = i;

                                }
                            }
                        btnMyLocation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(data.get(nearPostion).getLatitude(),data.get(nearPostion).getLongitude()),15));
                            }
                        });




                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude,myLongitude),15));
                        //Log.e("내 위치 위도 : ",myLatitude.toString());


                        markerService(data); // 파싱 받은 데이터로 마커 생성


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
        textViewChange(markerPosition);
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

    public void textViewChange(int position){
        tvCategory.setText("종류 : "+data.get(position).getCategory());
        tvName.setText("이름 : "+data.get(position).getName());
        tvAddress.setText("주소 : "+ data.get(position).getAddr());
        tvpNum.setText("전화번호 : " + data.get(position).getpNum());
        tvBusstop.setText("버스 정류장 : " + data.get(position).getBusstop());
        tvSubway.setText("지하철 : " + data.get(position).getSubway());
    }
    public void markerService(ArrayList<CultureMAPInfo> data) {

        for (int i = 0; i < data.size(); i++) {
            latLng = new LatLng(data.get(i).getLatitude(), data.get(i).getLongitude());
            Marker marker;
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng).title(data.get(i)
                            .getName()).snippet("여기는 "+i+1+"번쨰 한국 문화 공간입니다.")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            marker = map.addMarker(markerOptions);
            marker.setTag(i);


            textViewChange(i);
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
                String category = culture.getString("SUBJCODE");
                String name = culture.getString("FAC_NAME");
                String addr = culture.getString("ADDR");
                String pNum = culture.getString("PHNE");

                // 버스나 지하철 수단이 없는 경우도 있음
                String busstop = culture.getString("BUSSTOP");
                String subway = culture.getString("SUBWAY");
                if(busstop.length()==0)
                    busstop = " x ";
                else if(subway.length()==0)
                    subway =" x ";

                double latitude = culture.getDouble("X_COORD");    // 위도
                double longitude = culture.getDouble("Y_COORD");  // 경도

                data.add(new CultureMAPInfo(category,name, addr, pNum, busstop,subway,latitude,longitude));
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
}
