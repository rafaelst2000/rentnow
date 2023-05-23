package com.example.rentnow;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Motorcycle implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("brand")
    private String brand;
    @SerializedName("displacement")
    private String displacement;
    @SerializedName("rate")
    private Float rate;
    @SerializedName("price")
    private Float price;
    @SerializedName("image")
    private String image;
    @SerializedName("cv")
    private String cv;
    @SerializedName("weigth")
    private String weigth;
    @SerializedName("fuel")
    private Float fuel;
    @SerializedName("location")
    private String location;


    public Motorcycle(String id, String name, String description, String brand, String displacement, Float rate, Float price, String image, String cv, String weight, Float fuel, String location ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.displacement = displacement;
        this.rate = rate;
        this.price = price;
        this.image = image;
        this.cv = cv;
        this.weigth = weigth;
        this.fuel = fuel;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDisplacement() {
        return displacement;
    }

    public Float getRate() {
        return rate;
    }

    public Float getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getCv() {
        return cv;
    }

    public String getWeigth() {
        return weigth;
    }

    public Float getFuel() {
        return fuel;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}