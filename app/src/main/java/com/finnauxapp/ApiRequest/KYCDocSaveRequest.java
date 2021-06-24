package com.finnauxapp.ApiRequest;

public class KYCDocSaveRequest {

    public int CustomerId;
    public int KYC_DocId;

    public void setKYC_DocId(int KYC_DocId) {
        this.KYC_DocId = KYC_DocId;
    }

    public void setKYC_DocNumber(String KYC_DocNumber) {
        this.KYC_DocNumber = KYC_DocNumber;
    }

    public void setKYC_DocFile(String KYC_DocFile) {
        this.KYC_DocFile = KYC_DocFile;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public String KYC_DocNumber;
    public String KYC_DocFile;
    int LoginUserId;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }
}
