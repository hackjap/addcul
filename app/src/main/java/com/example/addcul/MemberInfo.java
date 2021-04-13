package com.example.addcul;

public class MemberInfo {

    private String email;
    private String name;
    private String sex;
    private String phoneNumber;
    private String birthDay;
    private String uid;


    public MemberInfo(String email,String name, String sex,String phoneNumber, String birthDay,String uid) {
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.uid = uid;


    }
    // 정보 조회용
    public MemberInfo(String name,String uid) {
        this.name = name;
        this.uid = uid;


    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
