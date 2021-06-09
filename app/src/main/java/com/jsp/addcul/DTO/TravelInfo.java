package com.jsp.addcul.DTO;

public class TravelInfo {
    private String name;
    private String photo;

    public TravelInfo(){

    }
    public TravelInfo(String name, String tag, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
