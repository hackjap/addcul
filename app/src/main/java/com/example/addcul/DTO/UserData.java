package com.example.addcul.DTO;

public class UserData {
    public String userEmailID; // email 주소에서 @이전까지의 ㄱ밧.
    public String fcmToken;


    public UserData(){

    }
    public UserData(String userEmailID, String fcmToken) {
        this.userEmailID = userEmailID;
        this.fcmToken = fcmToken;
    }

    public String getUserEmailID() {
        return userEmailID;
    }

    public void setUserEmailID(String userEmailID) {
        this.userEmailID = userEmailID;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
