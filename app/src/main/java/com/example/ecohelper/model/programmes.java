package com.example.ecohelper.model;

public class programmes {
    private int image;
    private String offer;

    public programmes(int image , String offer){
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
