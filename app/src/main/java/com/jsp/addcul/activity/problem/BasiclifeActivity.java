package com.jsp.addcul.activity.problem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jsp.addcul.R;

import java.util.ArrayList;

public class BasiclifeActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_wel);

        findViewById(R.id.wel1).setOnClickListener(onClickListener);
        findViewById(R.id.wel2).setOnClickListener(onClickListener);
        findViewById(R.id.wel3).setOnClickListener(onClickListener);
        findViewById(R.id.wel4).setOnClickListener(onClickListener);
        findViewById(R.id.wel5).setOnClickListener(onClickListener);


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            /*
             *  중간메뉴
             */
            switch (v.getId()) {
                // SOS
                case R.id.wel1:
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gurofc.familynet.or.kr/center/lay1/program/S295T322C324/receipt/view.do?seq=132962"));
                    startActivity(intent);
                    break;
                case R.id.wel2:
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gurofc.familynet.or.kr/center/lay1/program/S295T322C324/receipt/view.do?seq=132947"));
                    startActivity(intent2);

                    break;
                // 언어교환
                case R.id.wel3:
                    Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gurofc.familynet.or.kr/center/lay1/program/S295T322C324/receipt/view.do?seq=131104"));
                    startActivity(intent3);
                    break;
                case R.id.wel4:
                    Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gurofc.familynet.or.kr/center/lay1/program/S295T322C324/receipt/view.do?seq=130980"));
                    startActivity(intent4);
                    break;
                case R.id.wel5:
                    Intent intent5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gurofc.familynet.or.kr/center/lay1/program/S295T322C324/receipt/view.do?seq=132646"));
                    startActivity(intent5);
                    break;
            }
        }
    };
    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }


}