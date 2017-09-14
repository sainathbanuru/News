package com.example.sss.news;

/**
 * Created by sss on 14/9/17.
 */

public class newsData {

    String author, title, description, url, urlToImage, publisher;


    public newsData(String author, String title, String description, String url, String urlToImage, String publisher) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publisher = publisher;
    }


    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublisher() {
        return publisher;
    }
}
