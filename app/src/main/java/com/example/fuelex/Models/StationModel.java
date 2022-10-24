package com.example.fuelex.Models;

import com.google.gson.annotations.SerializedName;

public class StationModel {
    @SerializedName("id")
    private String id;
    @SerializedName("location")
    private String location;
    @SerializedName("userName")
    private String userName;
    @SerializedName("password")
    private String password;

    public StationModel(String id, String location, String userName, String password) {
        this.id = id;
        this.location = location;
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
