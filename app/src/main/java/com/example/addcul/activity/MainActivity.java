package com.example.addcul.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.addcul.Corona19;
import com.example.addcul.R;
import com.example.addcul.Util;
import com.example.addcul.adapter.Corona19Adapter;
import com.example.addcul.adapter.ImageSliderAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends BasicActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    FirebaseUser firebaseUser;
    ImageView myInfoProfile;  // 이미지 뷰
    TextView myInfoText;    // 닉네임 text
    String nickName;
    String photoUrl;
    ImageView home;
    RecyclerView recyclerView;

    private com.example.addcul.Util util;
    int flag = 0;


    // 슬라이드

    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;

    private String[] images = new String[]{
//            "https://www.notion.so/jsp98/56841d5a774a4bb583e697252fa03770",
            "https://s3.us-west-2.amazonaws.com/secure.notion-static.com/0903a414-5369-4369-aa1c-80da5e82025d/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210526%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210526T080901Z&X-Amz-Expires=86400&X-Amz-Signature=b6d45d39f4693d1120eb45f1d23aaf6c93e03679e5f4a7544588440e0f41f9e0&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22",
            "https://s3.us-west-2.amazonaws.com/secure.notion-static.com/6c4a005a-37da-4674-8d76-68e302d1be94/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210526%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210526T083102Z&X-Amz-Expires=86400&X-Amz-Signature=397f1db394be1b40f197391d9a2ef3268336fa8f458fe50167e581ad11b18e91&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22",
            "https://s3.us-west-2.amazonaws.com/secure.notion-static.com/8be1b9d4-c9b4-4c74-bb2d-4974e63e6151/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210526%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210526T081329Z&X-Amz-Expires=86400&X-Amz-Signature=1742eb1ee0b6b0d04dd09e9137d5d32b66ea8371bd2b35ae0c0a9794df5b543e&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22"
    };
//            "https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F99F2E033599D964307",
//            "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
//            "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
//            "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
//            "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        //Date 객체 사용
        Date date = new Date();
        String time1 = simpleDateFormat.format(date);
        //requestQueue의 선언부

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/" +
                "getCovid19InfStateJson?serviceKey=I2PzH0J7xfnYQlkzYq7DrQx7wXDZ" +
                "SgSwGR4W%2FrQ0YBLeg703oEvi%2BZiW9bt0F9POtNwkX60pN7Ngn4Ecr3tUug%3D" +
                "%3D&pageNo=1&numOfRows=10&startCreateDt=20201212&endCreateDt="+time1;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //오류 처리
                    }
                });
        requestQueue.add(stringRequest);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
       // actionBar.setIcon(R.drawable.img_logo_actionbar);

        View actionbarView = getLayoutInflater().inflate(R.layout.actionbar,null);
        actionbarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // 파이어베이스 유저 초기화

        //FirebaseAuth.getInstance().signOut();
        TextView textView = (TextView)findViewById(R.id.tv_home);
        home = (ImageView)findViewById(R.id.img_home);
        home.setImageResource(R.drawable.ic_home_yellow_24dp);
        // 이미지 크키 설정
        ViewGroup.LayoutParams params2 = home.getLayoutParams();
        params2.width = 130;
        params2.height = 130;
        home.setLayoutParams(params2);

        textView.setText("");
        // 슬라이드
        sliderViewPager = findViewById(R.id.sliderViewPager);
        layoutIndicator = findViewById(R.id.layoutIndicators);

        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(this, images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(images.length);


        myInfoProfile = findViewById(R.id.img_my_info);   // 프로필
        myInfoText = findViewById(R.id.tv_my_info);     // 닉네임


        util = new Util(this);

        if (firebaseUser == null) { // 로그인 상태가 아닐때
            myInfoProfile.setImageResource(R.drawable.ic_lock_open_black_24dp);
            myInfoText.setText("로그인");

        } else {  // 로그인 상태일때

            TextView tvMyinfo = (TextView)findViewById(R.id.tv_my_info);
            ImageView ivMyinfo = (ImageView) findViewById(R.id.img_my_info);
            ivMyinfo.setImageResource(R.drawable.ic_account_circle_black_24dp);
            tvMyinfo.setText("내정보");

            /* 구글
            Intent intent = getIntent();
            nickName = intent.getStringExtra("nickName");    // loginActivity로 부터 닉네임 전달받음
            photoUrl = intent.getStringExtra("photoUrl");    //loginActivity로 부터 프로필 전달받음
             */

            // 구글 로그인일 경우,
            if (nickName != null & photoUrl != null) {   // 구글 로그인 상태일때
                myInfoText.setText(nickName);   // 닉네임 텍스트뷰에 세팅
                Glide.with(this).load(photoUrl).into(myInfoProfile); // 프로필 URL을 이미지 뷰에 세팅

            } else { // 일반 로그인일 경우
                myInfoProfile.setImageResource(R.drawable.ic_account_circle_black_24dp);
                myInfoText.setText("내 정보");
                // 이미지 크키 설정
                ViewGroup.LayoutParams params = myInfoProfile.getLayoutParams();
                params.width = 75;
                params.height = 75;
                myInfoProfile.setLayoutParams(params);

            }
            // myInfoProfile.setImageResource(R.drawable.ic_lock_open_black_24dp);


        }

        /*
         *  중간메뉴 (SOS,언어교환,한국문화,문제해결)
         */
        // SOS
        findViewById(R.id.img_sos).setOnClickListener(onClickListener);
        findViewById(R.id.tv_sos).setOnClickListener(onClickListener);
        // 언어교환
        findViewById(R.id.img_lang_change).setOnClickListener(onClickListener);
        findViewById(R.id.tv_lang_change).setOnClickListener(onClickListener);
        // 한국문화
        findViewById(R.id.img_kor_info).setOnClickListener(onClickListener);
        findViewById(R.id.tv_kor_info).setOnClickListener(onClickListener);
        // 문제해결
        findViewById(R.id.img_problem).setOnClickListener(onClickListener);
        findViewById(R.id.tv_problem).setOnClickListener(onClickListener);

        // footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_home).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_map).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_my_info).setOnClickListener(onFooterlistner);

        findViewById(R.id.tv_notice).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            /*
             *  중간메뉴
             */
            switch (v.getId()) {
                // SOS
                case R.id.img_sos:
                    startActivity(ReadPostActivity.class);
                    break;

                case R.id.tv_sos:
                    startActivity(ReadPostActivity.class);
                    break;
                // 언어교환
                case R.id.img_lang_change:
                    startActivity(ReadChatActivity.class);
                    break;
                case R.id.tv_lang_change:
                    //startActivity()
                    break;
                // 한국문화
                case R.id.img_kor_info:
                    startActivity(KcultureActivity.class);
                    break;
                case R.id.tv_kor_info:
                    break;
                // 문제해결
                case R.id.img_problem:
                    startActivity(ProblemActivity.class);
                    break;
                case R.id.tv_problem:
                    //startActivity()
                    break;
                case R.id.tv_notice:
                    startActivity(IndexActivitiy.class);
                    break;

           } // end of switch


        }
    };

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }
    public void processData(String data) {
        recyclerView = (RecyclerView) findViewById(R.id.rv_corona);
        ArrayList<Corona19> list = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(data));

            int eventType = xpp.getEventType();
            boolean dateFlag = false, confFlag = false, relFlag = false, deathFlag=false, examFlag=false;
            int count=0;
            String tmpDate="", tmpConf="", tmpRel="", tmpDeath="", tmpExam="";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("stateDt")) dateFlag = true;
                    if (xpp.getName().equals("decideCnt")) confFlag = true;
                    if (xpp.getName().equals("clearCnt")) relFlag = true;
                    if (xpp.getName().equals("deathCnt")) deathFlag = true;
                    if (xpp.getName().equals("examCnt")) examFlag = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    if (dateFlag) {
                        tmpDate = xpp.getText();
                        count++;
                        dateFlag = false;
                    } else if (confFlag) {
                        tmpConf = xpp.getText();
                        count++;
                        confFlag = false;
                    } else if (relFlag) {
                        tmpRel = xpp.getText();
                        count++;
                        relFlag = false;
                    }
                    else if (deathFlag) {
                        tmpDeath = xpp.getText();
                        count++;
                        deathFlag = false;
                    }
                    else if (examFlag) {
                        tmpExam = xpp.getText();
                        count++;
                        examFlag = false;
                    }
                }
                if(count==5) {
                    list.add(new Corona19(tmpDate,tmpConf,tmpRel,tmpDeath,tmpExam));
                    count=0;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Corona19Adapter(list));
    }
}
