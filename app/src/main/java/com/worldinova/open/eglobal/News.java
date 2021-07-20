package com.worldinova.open.eglobal;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class News implements Serializable{

    @Exclude private String id;

    private String photo_url, title, description;

    public News() {

    }

    public News(String photo_url, String title, String description) {
        this.photo_url = photo_url;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
