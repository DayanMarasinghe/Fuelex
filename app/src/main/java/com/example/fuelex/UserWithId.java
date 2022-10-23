package com.example.fuelex;

public class UserWithId {
    private String id;
    private String userName;
    private String password;
    private String fullName;
    private String nic;
    private String vehicleType;
    private String vehicleNumber;

    public UserWithId(String id, String userName, String password, String fullName, String nic, String vehicleType, String vehicleNumber) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.nic = nic;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
