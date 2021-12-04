package com.jsp.addcul.activity.problem;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.jsp.addcul.R;

@SuppressWarnings("deprecation")
public class PregnancybirthActivity extends TabActivity { //TabActivity를 extends 해준다.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancybirth);


        TabHost tabHost = getTabHost();

        //tab1에 Kim이라는 태그를 붙여줌
        TabHost.TabSpec tab1 = tabHost.newTabSpec("Group1").setIndicator("응급상황대처법");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);//레이아웃과 연결

        TabHost.TabSpec tab2 = tabHost.newTabSpec("Group2").setIndicator("건강보험");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("Group3").setIndicator("의료급여제도");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("Group4").setIndicator("진료 분아별 병원 구분 ");
        tab4.setContent(R.id.tab4);
        tabHost.addTab(tab4);

        tabHost.setCurrentTab(0);


    }
}