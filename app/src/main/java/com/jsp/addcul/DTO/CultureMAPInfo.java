package com.jsp.addcul.DTO;

public class CultureMAPInfo {
    private String category; // 분류
    private String name;    // 이름
    private String addr;    // 주소
    private String pNum;    // 전화번호
    private String homepage;


    private double latitude;
    private double longitude;


    public CultureMAPInfo(String category, String name, String addr, String pNum, String homepage, double latitude, double longitude) {
        this.category = category;
        this.name = name;
        this.addr = addr;
        this.pNum = pNum;
        this.homepage = homepage;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //  test
    public CultureMAPInfo(String name, String addr, double latitude, double longitude) {
        this.name = name;
        this.addr = addr;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CultureMAPInfo(String name, String addr, double latitude, double longitude, String pNum) {
        this.name = name;
        this.addr = addr;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pNum = pNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getpNum() {
        return pNum;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
