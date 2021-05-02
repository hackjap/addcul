package com.example.addcul;

public class CultureMAPInfo {
    private String category; // 분류
    private String name;    // 이름
    private String addr;    // 주소
    private String pNum;    // 전화번호
    private String busstop;     // 버스정류장
    private String subway;  // 지하철
    private double latitude;
    private double longitude;

    public CultureMAPInfo(String category, String name, String addr, String pNum, String busstop,String subway,double latitude,double longitude) {
        this.category = category;
        this.name = name;
        this.addr = addr;
        this.pNum = pNum;
        this.busstop = busstop;
        this.subway = subway;
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

    public String getBusstop() {
        return busstop;
    }

    public void setBusstop(String busstop) {
        this.busstop = busstop;
    }

    public String getSubway() {
        return subway;
    }

    public void setSubway(String subway) {
        this.subway = subway;
    }
}
