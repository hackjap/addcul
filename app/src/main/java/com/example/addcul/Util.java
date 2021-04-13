package com.example.addcul;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

public class Util {

    Application application;
    private  Activity activity;

    public Util(Activity activity){
        this.activity = activity;

    }
    public void showToast(String msg){
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show();
    }


}
