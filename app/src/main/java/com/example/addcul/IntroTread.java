package com.example.addcul;

import android.os.Handler;
import android.os.Message;
import android.view.textclassifier.ConversationActions;


public class IntroTread extends Thread{

    public Handler handler;

    public IntroTread(Handler handler){
        this.handler=handler;
    }



    public void run(){
        Message msg = new Message();

        try{
            Thread.sleep(3000);
            msg.what=1;
            handler.sendEmptyMessage(msg.what);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
