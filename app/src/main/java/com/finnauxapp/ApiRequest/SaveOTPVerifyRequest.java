package com.finnauxapp.ApiRequest;

public class SaveOTPVerifyRequest {
    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public int getOTPCode() {
        return OTPCode;
    }

    public void setOTPCode(int OTPCode) {
        this.OTPCode = OTPCode;
    }

    private String PhoneNo;
    private int OTPCode;
}
