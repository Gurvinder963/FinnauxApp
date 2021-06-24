package com.finnauxapp.ApiRequest;

public class ExpenditureSaveRequest {

    public  int ExpenditureId;
    public int CustomerId;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int applicationId) {
        ApplicationId = applicationId;
    }

    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public int LoginUserId;

    public int ApplicationId;

    public String ExpenditureType;

    public int getExpenditueId() {
        return ExpenditureId;
    }

    public void setExpenditueId(int expenditueId) {
        ExpenditureId = expenditueId;
    }

    public String getExpenditureType() {
        return ExpenditureType;
    }

    public void setExpenditureType(String expenditureType) {
        ExpenditureType = expenditureType;
    }

    public float getExpenditureAmount() {
        return ExpenditureAmount;
    }

    public void setExpenditureAmount(float expenditureAmount) {
        ExpenditureAmount = expenditureAmount;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public float ExpenditureAmount;
    public String Remark;
}
