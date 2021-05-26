package com.example.addcul;

public class LifeInfo {
    private String name;
    private String photo;

    public LifeInfo(){

    }
    public LifeInfo(String name, String tag, String photo) {
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
