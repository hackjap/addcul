package com.example.addcul.DTO;

public class FoodInfo {
    private String name;
    private String tag;
    private String photo;

    public FoodInfo(){

    }
    public FoodInfo(String name, String tag,String photo) {
        this.name = name;
        this.tag = tag;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
