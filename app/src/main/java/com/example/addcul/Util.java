package com.example.addcul;

import android.app.Activity;
import android.widget.Toast;

public class Util {
    private  Activity activity;

    public Util(Activity activity){
        this.activity = activity;

    }
    public void showToast(String msg){
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show();
    }

}
