package com.example.addcul.DTO;

import java.io.Serializable;
import java.util.Date;

public class PostInfo implements Serializable {

    private String title;
    private String contents;
    private String publisher;
    private Date createdAt; // 생성일
    private String id;


    public PostInfo(String title, String contents, String publisher, Date createdAt) {
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;

    }

    public PostInfo(String title,String contents, String publisher, Date createdAt, String id) {
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public String  getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
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