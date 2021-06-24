package com.finnauxapp.ApiRequest;

public class SaveRevertRequest {
    public int InquiryId;

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



    public int LoginUserId;

    public String getRevertReason() {
        return RevertReason;
    }

    public void setRevertReason(String revertReason) {
        RevertReason = revertReason;
    }

    public String RevertReason;

}
