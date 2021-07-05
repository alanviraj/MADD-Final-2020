package com.example.vehiclebath.Model;

public class Subscription {

    private String Name,Price,DiscountPercentage,Availability;

    public Subscription() {

    }

    public Subscription(String name, String price, String discountPercentage, String availability) {
        Name = name;
        Price = price;
        DiscountPercentage = discountPercentage;
        Availability = availability;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

}
