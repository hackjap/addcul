package com.example.addcul;

import java.io.Serializable;
import java.util.Date;

public class ChatInfo implements Serializable {

    private String text;
    private String publisher;
    private Date created;
    private String id;



    public ChatInfo(String text) {
    ;
        this.text = text;


    }

    // 채팅버젼
    public ChatInfo(String text, String publisher,Date created) {
        this.text = text;
        this.created = created;
        this.publisher=publisher;

    }

    // 문서 삭제용
    public ChatInfo(String text, String publisher,Date created,String id) {
        this.text = text;
        this.publisher=publisher;
        this.created = created;
        this.id = id;

    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
