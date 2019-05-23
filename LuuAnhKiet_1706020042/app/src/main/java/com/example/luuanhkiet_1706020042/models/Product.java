package com.example.luuanhkiet_1706020042.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Product implements Serializable {
    String description;
    String producer;
    String product_name;
    int id;
    int price;
    @Exclude
    String keyParent;
    @Exclude
    public String getKeyParent() {
        return keyParent;
    }
    @Exclude
    public void setKeyParent(String keyParent) {
        this.keyParent = keyParent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
