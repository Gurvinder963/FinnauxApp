package com.finnauxapp.ApiRequest;

public class SaveHoleRequest {
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

    public String getHoldReason() {
        return HoldReason;
    }

    public void setHoldReason(String holdReason) {
        HoldReason = holdReason;
    }

    public String getNextFollowUp() {
        return NextFollowUp;
    }

    public void setNextFollowUp(String nextFollowUp) {
        NextFollowUp = nextFollowUp;
    }

    public int LoginUserId;
    public String HoldReason;
    public String NextFollowUp;
}
