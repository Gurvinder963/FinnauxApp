package com.finnauxapp.ApiRequest;

public class LoginFeeRequest {

    public float Amount;
    int LoginUserId;
    String CollectionMode;
    String Remark;

    public String getCollectionMode() {
        return CollectionMode;
    }

    public void setCollectionMode(String collectionMode) {
        CollectionMode = collectionMode;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public float getTaxPercentage() {
        return TaxPercentage;
    }

    public void setTaxPercentage(float taxPercentage) {
        TaxPercentage = taxPercentage;
    }

    public float getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(float taxAmount) {
        TaxAmount = taxAmount;
    }

    float TaxPercentage;
    float TaxAmount;

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int applicationId) {
        ApplicationId = applicationId;
    }

    int ApplicationId;
}
