package com.finnauxapp.ApiRequest;

public class GenerateOTPRequest {
    private String PhoneNo;

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    private int LoginUserId;
    private int CustomerId;
}

