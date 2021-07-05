package com.example.vehiclebath.model1;

public class Advertisements1 {


    private String Description;
    private String ImageUrl;

    public Advertisements1() {
    }

    public Advertisements1(String description, String imageUrl) {
        Description = description;
        ImageUrl = imageUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
