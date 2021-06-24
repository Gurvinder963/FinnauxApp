package com.finnauxapp.ApiRequest;

public class RejectRequest {
    private int InquiryId;

    public int getInquiryId() {
        return InquiryId;
    }

    public void setInquiryId(int inquiryId) {
        InquiryId = inquiryId;
    }

    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public int LoginUserId;
    public String Reason;
}
