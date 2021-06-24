package com.finnauxapp.ApiRequest;

public class LeadRequest {
    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public int LoginUserId;

    public String getInqStatus() {
        return InqStatus;
    }

    public void setInqStatus(String inqStatus) {
        InqStatus = inqStatus;
    }

    public String InqStatus;
}
