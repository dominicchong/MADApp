package com.example.ecohelper.model;

public class news {

    private int image;
    private String offer;

    public news(int image , String offer){
        this.image = image;
        this.offer = offer;
    }

    public int getImage() {
        return image;
    }

    public String getOffer() {
        return offer;
    }
}

