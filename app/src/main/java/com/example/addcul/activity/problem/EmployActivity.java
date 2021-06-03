package com.example.addcul.activity.problem;

import android.app.TabActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import androidx.annotation.Nullable;

import com.example.addcul.R;

@SuppressWarnings("deprecation")
public class EmployActivity extends TabActivity {

    //한국버튼
    ImageView homebtn01,callbtn01;
    //의정부 버튼
    ImageView homebtn02,callbtn02;
    //김해 버튼
    ImageView homebtn03,callbtn03;
    //창원 버튼
    ImageView homebtn04,callbtn04;
    //인천 버튼
    ImageView homebtn05,callbtn05;
    //대구 버튼
    ImageView homebtn06,callbtn06;
    //천안 버튼
    ImageView homebtn07,callbtn07;
    //광주 버튼
    ImageView homebtn08,callbtn08;
    //양산 버튼
    ImageView homebtn09,callbtn09;


    /*소지역센터 버튼튼*/
    ImageView socall01,socall02,socall03,socall04,socall05,socall06,socall07;
    // 34개의 소지역센터 mapbutton
    ImageView somap01,somap02,somap03,somap04,somap05,somap06,somap07;
    //아래는 추후 추가예정
    /*ImageView socall08,socall09,socall10,socall11,socall12,socall13,socall14, socall15, socall16, socall17, socall18, socall19, socall20, socall21,socall22,socall23, socall24, socall25, socall26, socall27, socall28, socall29, socall30, socall31, socall32, socall33, socall34;*/
    /*ImageView somap08,somap09,somap10,somap11,somap12,somap13,somap14, somap15, somap16, somap17, somap18, somap19, somap20, somap21,somap22,somap23, somap24, somap25, somap26, somap27, somap28, somap29, somap30, somap31, somap32, somap33, somap34;*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos03);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Group1").setIndicator("거점센터");
        tab1.setContent(R.id.sos02_1);
        tabHost.addTab(tab1);//레이아웃과 연결

        TabHost.TabSpec tab2 = tabHost.newTabSpec("Group2").setIndicator("소지역센터");
        tab2.setContent(R.id.sos02_2);
        tabHost.addTab(tab2);

        tabHost.setCurrentTab(0);

        //한국 홈페이지
        homebtn01 = findViewById(R.id.homebtn01);
        homebtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.migrantok.org/"));
                startActivity(intent);
            }
        });
        //한국전화걸기
        callbtn01 = findViewById(R.id.callbtn01);
        callbtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0269008000"));
                startActivity(intent);
            }
        });

        //의정부 홈페이지
        homebtn02 = findViewById(R.id.homebtn02);
        homebtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ufwc.or.kr/main/index.jsp"));
                startActivity(intent);
            }
        });
        //의정부 전화걸기
        callbtn02 = findViewById(R.id.callbtn02);
        callbtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0318389111"));
                startActivity(intent);
            }
        });

        //김해 홈페이지
        homebtn03 = findViewById(R.id.homebtn03);
        homebtn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gimhaekorea.or.kr/"));
                startActivity(intent);
            }
        });
        //김해 전화걸기
        callbtn03 = findViewById(R.id.callbtn03);
        callbtn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0553382727"));
                startActivity(intent);
            }
        });

        //창원 홈페이지
        homebtn04 = findViewById(R.id.homebtn04);
        homebtn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mfwc.or.kr/"));
                startActivity(intent);
            }
        });
        //창원 전화걸기
        callbtn04 = findViewById(R.id.callbtn04);
        callbtn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0552535270"));
                startActivity(intent);
            }
        });

        //인천 홈페이지
        homebtn05 = findViewById(R.id.homebtn05);
        homebtn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.infc.or.kr/"));
                startActivity(intent);
            }
        });
        //인천 전화걸기
        callbtn05 = findViewById(R.id.callbtn05);
        callbtn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0324315757"));
                startActivity(intent);
            }
        });

        //대구 홈페이지
        homebtn06 = findViewById(R.id.homebtn06);
        homebtn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dfwc.or.kr/"));
                startActivity(intent);
            }
        });
        //대구 전화걸기
        callbtn06 = findViewById(R.id.callbtn06);
        callbtn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0536549700"));
                startActivity(intent);
            }
        });

        //천안 홈페이지
        homebtn07 = findViewById(R.id.homebtn07);
        homebtn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cfwc.or.kr/"));
                startActivity(intent);
            }
        });
        //천안 전화걸기
        callbtn07 = findViewById(R.id.callbtn07);
        callbtn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0414117000"));
                startActivity(intent);
            }
        });

        //광주 홈페이지
        homebtn08 = findViewById(R.id.homebtn08);
        homebtn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gjfc119.or.kr/"));
                startActivity(intent);
            }
        });
        //광주 전화걸기
        callbtn08 = findViewById(R.id.callbtn08);
        callbtn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0629441199"));
                startActivity(intent);
            }
        });

        //양산 홈페이지
        homebtn09 = findViewById(R.id.homebtn09);
        homebtn09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ysfc.or.kr/"));
                startActivity(intent);
            }
        });
        //양산 전화걸기
        callbtn09 = findViewById(R.id.callbtn09);
        callbtn09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0559120255"));
                startActivity(intent);
            }
        });


        /////////////////////
        /*소지역센터*/
        ///////////////////


        //(사)한국이주노동자복지회 전화걸기
        socall01 = findViewById(R.id.socall01);
        socall01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/028584115"));
                startActivity(intent);
            }
        });
        //(사)한국이주노동자복지회 지도보기
        somap01 = findViewById(R.id.somap01);
        somap01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kko.to/6xQgM0ljj"));
                startActivity(intent);
            }
        });


        //엘림외국인지원센터 전화걸기
        socall02 = findViewById(R.id.socall02);
        socall02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0327661061"));
                startActivity(intent);
            }
        });
        //엘림외국인지원센터 지도보기
        somap02 = findViewById(R.id.somap02);
        somap02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kko.to/kOLvp0l0o"));
                startActivity(intent);
            }
        });


        //인천외국인노동자센터 전화걸기
        socall03 = findViewById(R.id.socall03);
        socall03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0328743613"));
                startActivity(intent);
            }
        });
        //인천외국인노동자센터 지도보기
        somap02 = findViewById(R.id.somap03);
        somap02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kko.to/GLs6Mj20o"));
                startActivity(intent);
            }
        });

        //부천이주민센터 전화걸기
        socall04 = findViewById(R.id.socall04);
        socall04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0326540664"));
                startActivity(intent);
            }
        });
        //부천이주민센터 지도보기
        somap04 = findViewById(R.id.somap04);
        somap04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kko.to/4sgYH02jT"));
                startActivity(intent);
            }
        });

        //(사)국경없는마을 김포이주민센터 전화걸기
        socall05 = findViewById(R.id.socall05);
        socall05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0319827661"));
                startActivity(intent);
            }
        });
        //(사)국경없는마을 김포이주민센터 지도보기
        somap05 = findViewById(R.id.somap05);
        somap05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kko.to/7XTETj200"));
                startActivity(intent);
            }
        });

        //오산이주노동자센터 전화걸기
        socall06 = findViewById(R.id.socall06);
        socall06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0313729301"));
                startActivity(intent);
            }
        });
        //오산이주노동자센터 지도보기
        somap06 = findViewById(R.id.somap06);
        somap06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kko.to/p_m5H0ljM"));
                startActivity(intent);
            }
        });

        //평택외국인복지센터 전화걸기
        socall07 = findViewById(R.id.socall07);
        socall07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/0316528855"));
                startActivity(intent);
            }
        });
        //평택외국인복지센터 지도보기
        somap07 = findViewById(R.id.somap07);
        somap07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kko.to/GYJ1T0l0o"));
                startActivity(intent);
            }
        });
    }
}