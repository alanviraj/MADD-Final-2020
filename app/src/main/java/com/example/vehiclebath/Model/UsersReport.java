package com.example.vehiclebath.Model;

public class UsersReport {

    private String Name,Email,Phone,Password;

    public UsersReport(){

    }

    public UsersReport(String name, String email, String phone, String password) {
        Name = name;
        Email = email;
        Phone = phone;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

}
