package com.example.vehiclebath.model1;

public class Loyalty {

    private String Name, Phone, Password;
    private  float LoyaltyPoints;

    public Loyalty() {

    }

    public Loyalty(String name, String phone, String password, float loyaltyPoints) {
        Name = name;
        Phone = phone;
        Password = password;
        LoyaltyPoints = loyaltyPoints;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public float getLoyaltyPoints() {
        return LoyaltyPoints;
    }

    public void setLoyaltyPoints(float loyaltyPoints) {
        LoyaltyPoints = loyaltyPoints;
    }
}
