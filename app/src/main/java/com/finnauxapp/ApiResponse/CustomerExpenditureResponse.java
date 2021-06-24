package com.finnauxapp.ApiResponse;

public class CustomerExpenditureResponse {


    /**
     * CustomerExpenditureId : 1
     * ApplicationId : 13
     * CustomerId : 28
     * ExpenditureType : kids fee
     * TotalExpenditureAmount : 6000
     * Remark :
     */

    private int CustomerExpenditureId;
    private int ApplicationId;
    private int CustomerId;
    private String ExpenditureType;
    private int TotalExpenditureAmount;
    private String Remark;

    public int getCustomerExpenditureId() {
        return CustomerExpenditureId;
    }

    public void setCustomerExpenditureId(int CustomerExpenditureId) {
        this.CustomerExpenditureId = CustomerExpenditureId;
    }

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int ApplicationId) {
        this.ApplicationId = ApplicationId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getExpenditureType() {
        return ExpenditureType;
    }

    public void setExpenditureType(String ExpenditureType) {
        this.ExpenditureType = ExpenditureType;
    }

    public int getTotalExpenditureAmount() {
        return TotalExpenditureAmount;
    }

    public void setTotalExpenditureAmount(int TotalExpenditureAmount) {
        this.TotalExpenditureAmount = TotalExpenditureAmount;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
}
