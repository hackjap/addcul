package com.example.addcul.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.addcul.R;
import com.example.addcul.fragment.FragFood;
import com.example.addcul.fragment.FragLife;
import com.example.addcul.fragment.FragMusic;
import com.example.addcul.fragment.FragTravel;

public class KcultureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kculture);

        final Button btn_food, btn_life, btn_music, btn_travel;
        LinearLayout contentLayout;

        final FragmentManager fragmentManager;
        final FragFood fragFood;
        final FragMusic fragMusic;
        final FragLife fragLife;
        final FragTravel fragTravel;

        btn_food = (Button) findViewById(R.id.btn_food);
        btn_life = (Button) findViewById(R.id.btn_life);
        btn_music = (Button) findViewById(R.id.btn_music);
        btn_travel = (Button) findViewById(R.id.btn_travel);
        contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

        fragmentManager = getSupportFragmentManager();
        // 프래그먼트 객체 만들기
        fragFood = new FragFood();
        fragMusic = new FragMusic();
        fragLife = new FragLife();
        fragTravel = new FragTravel();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.contentLayout, fragFood);
        ft.commit();

        final Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contentLayout);




        // Food
        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.contentLayout, fragFood);
                ft.commit();
                /*
                for(Fragment fragment:getSupportFragmentManager().getFragments()){
                    if(fragment.isVisible())
                        if(fragment instanceof FragFood)
                            btn_food.setBackgroundColor(getColor(R.color.basic_yellow));
                        else
                            btn_food.setBackgroundColor(getColor(R.color.ultimate_gray));
                }

                 */


            }
        });
        // Music
        btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.contentLayout, fragMusic);
                ft.commit();


            }
        });
        // Life
        btn_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.contentLayout, fragLife);
                ft.commit();
            }
        });
        // Travel
        btn_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.contentLayout, fragTravel);
                ft.commit();
            }
        });


    }

}
