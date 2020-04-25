package com.example.asus.app3_02.domain;

public class user {
    private String userName;
    private int imgId;

    public user(String userName, int imgId) {
        this.userName = userName;
        this.imgId = imgId;
    }

    public String getUserName() {
        return userName;
    }

    public int getImgId() {
        return imgId;
    }
}
