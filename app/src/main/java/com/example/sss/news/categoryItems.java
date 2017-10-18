package com.example.sss.news;

/**
 * Created by sss on 18/10/17.
 */

public class categoryItems {

    private String title;
    private int imageId;

    public categoryItems(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
