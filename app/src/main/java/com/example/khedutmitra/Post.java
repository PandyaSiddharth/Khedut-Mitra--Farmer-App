package com.example.khedutmitra;

import java.io.Serializable;

public class Post implements Serializable {

    String id;
    String img_url;
    String title;
    String description;
    String createdBy;

    public Post(String id, String img_url, String title, String description, String createdBy){
        this.id = id;
        this.img_url = img_url;
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
