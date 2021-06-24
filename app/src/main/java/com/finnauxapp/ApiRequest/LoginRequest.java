package com.finnauxapp.ApiRequest;

public class LoginRequest {
    public String UserLoginID;

    public String getUserLoginID() {
        return UserLoginID;
    }

    public void setUserLoginID(String userLoginID) {
        UserLoginID = userLoginID;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String UserPassword;
    public String IPAddress;

}
