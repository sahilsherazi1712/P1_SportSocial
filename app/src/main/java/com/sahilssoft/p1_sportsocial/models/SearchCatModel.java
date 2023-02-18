package com.sahilssoft.p1_sportsocial.models;

public class SearchCatModel {
    int image;
    String category;

    public SearchCatModel(int image, String category) {
        this.image = image;
        this.category = category;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
