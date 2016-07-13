package com.tan90.androidservices.model;

import android.graphics.Bitmap;

/**
 * Created by I320626 on 7/11/2016.
 */
public class Flower {

    private int productId;
    private String name;
    private String category;
    private String instructtions;
    private double price;
    private String photo;
    private Bitmap bitmap;



    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstructtions() {
        return instructtions;
    }

    public void setInstructtions(String instructtions) {
        this.instructtions = instructtions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
