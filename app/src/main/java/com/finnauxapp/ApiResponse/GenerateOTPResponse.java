package com.finnauxapp.ApiResponse;

public class GenerateOTPResponse {

    private int OTPCode;

    public int getOTPCode() {
        return OTPCode;
    }

    public void setOTPCode(int OTPCode) {
        this.OTPCode = OTPCode;
    }

    public String getOTPGenerateOn() {
        return OTPGenerateOn;
    }

    public void setOTPGenerateOn(String OTPGenerateOn) {
        this.OTPGenerateOn = OTPGenerateOn;
    }

    private String OTPGenerateOn;

}
