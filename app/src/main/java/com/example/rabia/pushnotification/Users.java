package com.example.rabia.pushnotification;

public class Users extends UserId{
    String email,image;
    public Users(){

    }

    public Users(String email, String image) {
        this.email = email;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
