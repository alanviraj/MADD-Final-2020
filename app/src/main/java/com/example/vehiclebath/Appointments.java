package com.example.vehiclebath;

public class Appointments {
    private String C_Name;
    private String CarWashType;
    private String Date;
    private String Time;
    private String Key;


    public Appointments(){

    }

    public Appointments(String c_Name, String carWashType, String date, String time) {
        C_Name = c_Name;
        CarWashType = carWashType;
        Date = date;
        Time = time;
    }

    public Appointments(String c_Name, String carWashType, String date, String time, String key) {
        C_Name = c_Name;
        CarWashType = carWashType;
        Date = date;
        Time = time;
        Key = key;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getC_Name() {
        return C_Name;
    }

    public void setC_Name(String c_Name) {
        C_Name = c_Name;
    }

    public String getCarWashType() {
        return CarWashType;
    }

    public void setCarWashType(String carWashType) {
        CarWashType = carWashType;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
