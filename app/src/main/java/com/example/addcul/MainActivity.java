package com.example.addcul;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.addcul.DTO.Corona19;
import com.example.addcul.Util.Util;
import com.example.addcul.activity.chat.IndexActivitiy;
import com.example.addcul.activity.config.BasicActivity;
import com.example.addcul.activity.kculture.KcultureActivity;
import com.example.addcul.activity.kculture.YoutubeActivity;
import com.example.addcul.activity.post.ReadPostActivity;
import com.example.addcul.activity.problem.ProblemActivity;
import com.example.addcul.adapter.Corona19Adapter;
import com.example.addcul.adapter.ImageSliderAdapter;
import com.example.addcul.adapter.NoticeAdapter;
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
    RecyclerView recyclerView, rvNotice;
    int faqFlag =0;
    private Util util;
    int flag = 0;

    // 슬라이드
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;
    private String[] images = new String[]{
            "https://blogfiles.pstatic.net/MjAyMTA2MDNfMTIw/MDAxNjIyNjk1MTM0ODE2.deHSpdovuEeCMyOIpR1NMyf_qyL5OcMfdkBNXkk8uE4g.F_pifAvWR-xx2DctV8KKETjlK89wPllEASw190LGd-cg.PNG.jangsp57/main1.png",
            "https://blogfiles.pstatic.net/MjAyMTA2MDNfMTQ5/MDAxNjIyNjk1MTMyMTA1.L4xh2WRV1OCihQgWZXrNJUSEUrnjdWLq7xJUk8JPD94g.x-g0APJPWeHYU6ZLN-WCf6byx_erM9ugF2z3xzc2VHkg.PNG.jangsp57/main2.png",
            "https://blogfiles.pstatic.net/MjAyMTA2MDNfMTI2/MDAxNjIyNjk1MTQyNDIz.Wi4Zk9PR9ynlsHKLT0JDZNodeCjOz9bVgSNrWcuz-Y0g.2aPMSpZdTrqswiYdkK7jl45SELGxrGsIWgYnASNi27Mg.PNG.jangsp57/main3.png"
    };

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
                "%3D&pageNo=1&numOfRows=10&startCreateDt=20201212&endCreateDt=" + time1;

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

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//
//        View actionbarView = getLayoutInflater().inflate(R.layout.actionbar, null);
//        actionbarView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(MainActivity.class);
//
//            }
//        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // 파이어베이스 유저 초기화

        //FirebaseAuth.getInstance().signOut();
        TextView textView = (TextView) findViewById(R.id.tv_home);
        home = (ImageView) findViewById(R.id.img_home);
        home.setImageResource(R.drawable.ic_home_yellow_24dp);
        // 이미지 크키 설정
        ViewGroup.LayoutParams params2 = home.getLayoutParams();
        home.setLayoutParams(params2);

        // 슬라이드
        sliderViewPager = findViewById(R.id.sliderViewPager);
        layoutIndicator = findViewById(R.id.layoutIndicators);

        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(this, images);
        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(imageSliderAdapter);

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        imageSliderAdapter.setOnMyTouchListener(new ImageSliderAdapter.OnMyTouchListener() {
            @Override
            public void onTouch(View v, int position) {
                if(position == 0){
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.liveinkorea.kr/portal/KOR/board/mlbs/boardView.do?menuSeq=282&boardSeq=2&conSeq=403547"));
                    startActivity(intent);
                }
            }
        });

        setupIndicators(images.length);

        util = new Util(this);

        myInfoProfile = findViewById(R.id.img_my_info);   // 프로필
        myInfoText = findViewById(R.id.tv_my_info);     // 닉네임


        if (firebaseUser == null) { // 로그인 상태가 아닐때
            myInfoProfile.setImageResource(R.drawable.ic_lock_open_black_24dp);
            myInfoText.setText("로그인");

        } else {  // 로그인 상태일때


            myInfoProfile.setImageResource(R.drawable.ic_account_circle_black_24dp);
            myInfoText.setText("내정보");

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

        // 매인 베너
        findViewById(R.id.sliderContainer).setOnClickListener(onClickListener);

        // footer 바인딩
        // 하단메뉴
        findViewById(R.id.img_search).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_home).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_translate).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_map).setOnClickListener(onFooterlistner);
        findViewById(R.id.img_my_info).setOnClickListener(onFooterlistner);
        findViewById(R.id.tv_notice).setOnClickListener(onClickListener);


        //FAQ
        findViewById(R.id.faq_tv1).setOnClickListener(onClickListener);
        findViewById(R.id.faq_tv2).setOnClickListener(onClickListener);
        // 공지사항 리사이클러뷰
        NoticeAdapter noticeAdapter = new NoticeAdapter(getNotice());
        rvNotice = (RecyclerView) findViewById(R.id.rv_notice);

        rvNotice.setLayoutManager(new LinearLayoutManager(this));
        rvNotice.setAdapter(noticeAdapter);

        noticeAdapter.setOnMyTouchLister(new NoticeAdapter.OnMyTouchLister() {
            @Override
            public void onTouch(View v, int position) {
                if(position==0){
                    Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_LONG).show();
                    startActivity(YoutubeActivity.class);
                }
                else if(position==1){
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.liveinkorea.kr/portal/KOR/board/mlbs/boardView.do?menuSeq=282&boardSeq=2&conSeq=394554"));
                    startActivity(intent);
                }
                else if(position==2){
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.liveinkorea.kr/portal/KOR/board/mlbs/boardView.do?menuSeq=282&boardSeq=2&conSeq=401194"));
                    startActivity(intent);
                }

            }
        });
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
                    startActivity(IndexActivitiy.class);
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

                case R.id.sliderContainer: {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com"));
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
                    Log.e("CXXTEST", "df");
                    break;
                }
                // FAQ
                case R.id.faq_tv1:{
                    if(faqFlag == 0) {
                        findViewById(R.id.faq_explain1).setVisibility(View.VISIBLE);
                        ImageView image  = findViewById(R.id.faq_img1);
                        image.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                        faqFlag = 1;
                    }
                    else{
                        findViewById(R.id.faq_explain1).setVisibility(View.GONE);
                        ImageView image  = findViewById(R.id.faq_img1);
                        image.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                        faqFlag =0;

                    }
                    break;
                }
                case R.id.faq_tv2:{
                    if(faqFlag == 0) {
                        findViewById(R.id.faq_explain2).setVisibility(View.VISIBLE);
                        ImageView image  = findViewById(R.id.faq_img2);
                        image.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                        faqFlag = 1;
                    }
                    else{
                        findViewById(R.id.faq_explain2).setVisibility(View.GONE);
                        ImageView image  = findViewById(R.id.faq_img2);
                        image.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                        faqFlag =0;
                    }
                    break;

                }
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
            boolean dateFlag = false, confFlag = false, relFlag = false, deathFlag = false, examFlag = false;
            int count = 0;
            String tmpDate = "", tmpConf = "", tmpRel = "", tmpDeath = "", tmpExam = "";
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

                    } else if (deathFlag) {
                        tmpDeath = xpp.getText();
                        count++;
                        deathFlag = false;
                    } else if (examFlag) {
                        tmpExam = xpp.getText();
                        count++;
                        examFlag = false;
                    }
                }
                if (count == 5) {
                    list.add(new Corona19(tmpDate, tmpConf, tmpRel, tmpDeath, tmpExam));
                    count = 0;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Corona19Adapter(list));
    }


    private ArrayList<String> getNotice(){
        ArrayList<String> noticeList = new ArrayList<>();
        noticeList.add(0,"(추천) 애드컬 사용법 ver.1");
        noticeList.add(1,"(코로나19) 사회적 거리 두기 단계별 기준 및 방역 조치");
        noticeList.add(2,"2021년 한국생활가이드북 웰컴북");



        return noticeList;
    }

}
