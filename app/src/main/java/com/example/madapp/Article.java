package com.example.madapp;

public class Article {
    private String title;
    private String source;
    private String urlToImage;
    private String imageUrl;
    private String url;

    public Article(String title, String url, String source){//String imageUrl) {
        this.title = title;
        this.source = source;
        this.urlToImage = urlToImage;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    // Getter methods
    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}