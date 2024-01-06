package com.example.madapp;

public class Article {
    private String title;
    private String source;
    private String thumbnail;

    private String url;

    public Article(String title, String url, String source, String thumbnail) {
        this.title = title;
        this.source = source;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    // Getter methods
    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getThumbnail() {return thumbnail;}

    public String getUrl() {
        return url;
    }

}