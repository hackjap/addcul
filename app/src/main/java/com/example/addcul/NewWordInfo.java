package com.example.addcul;

public class NewWordInfo {
    private String num;
    private String name;
    private String info;
    private String subInfo;

    public NewWordInfo(){

    }
    public NewWordInfo(String num, String name, String info,String subInfo) {
        this.num = num;
        this.name = name;
        this.info = info;
        this.subInfo = subInfo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSubInfo() {
        return subInfo;
    }

    public void setSubInfo(String subInfo) {
        this.subInfo = subInfo;
    }
}
