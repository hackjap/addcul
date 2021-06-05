package com.example.addcul.DTO;

public class ChatData {
    private String userName;
    private String message;
    private String uid;

    public ChatData(){

    }
    public ChatData(String userName, String message,String uid) {
        this.userName = userName;
        this.message = message;
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
