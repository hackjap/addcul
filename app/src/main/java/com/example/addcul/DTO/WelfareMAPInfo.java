package com.example.addcul.DTO;

public class WelfareMAPInfo {

//    String name = welfare.getString("INST_GRP_NM");        // 이름
//    String addr = welfare.getString("REFINE_LOTNO_ADDR");            // 주소
//    String pNum = welfare.getString("CONTCT_NO");            // 전화번호
//    double latitude = welfare.getDouble("REFINE_WGS84_LOGT");    // 위도
//    double longitude = welfare.getDouble("REFINE_WGS84_LAT");  // 경도

    private String name;    // 이름
    private String addr;    // 주소
    private String pNum;    // 전화번호
    private double latitude;
    private double longitude;

    public WelfareMAPInfo(String name, String addr, String pNum, double latitude, double longitude) {
        this.name = name;
        this.addr = addr;
        this.pNum = pNum;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getpNum() {
        return pNum;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
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
}
