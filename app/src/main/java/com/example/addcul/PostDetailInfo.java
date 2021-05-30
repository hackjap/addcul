package com.example.addcul;

import java.io.Serializable;
import java.util.Date;

public class PostDetailInfo implements Serializable {

    private String name;
    private String contents;
    private String publisher;
    private Date createdAt; // 생성일
    private String id;


    public PostDetailInfo(String contents) {
        this.contents = contents;
    }

    public PostDetailInfo(String name, String contents, Date createdAt,String id ) {
        this.name = name;
        this.contents = contents;
        this.createdAt = createdAt;
        this.id = id;

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getname() {
        return name;
    }

    public String  getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}