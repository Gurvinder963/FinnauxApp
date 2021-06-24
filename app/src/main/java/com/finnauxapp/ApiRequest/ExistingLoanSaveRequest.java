package com.finnauxapp.ApiRequest;

public class ExistingLoanSaveRequest {
    public String LoanType;
    public String LoanStatus;

    public String getLoanFirmName() {
        return LoanFirmName;
    }

    public void setLoanFirmName(String loanFirmName) {
        LoanFirmName = loanFirmName;
    }

    public String LoanFirmName;

    public String getLoanType() {
        return LoanType;
    }

    public void setLoanType(String loanType) {
        LoanType = loanType;
    }

    public String getLoanStatus() {
        return LoanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        LoanStatus = loanStatus;
    }


    public String getLoanTakeYear() {
        return LoanTakenOn;
    }

    public void setLoanTakeYear(String loanTakeYear) {
        LoanTakenOn = loanTakeYear;
    }

    public int getLoanDuration() {
        return LoanDurationMonth;
    }

    public void setLoanDuration(int loanDuration) {
        LoanDurationMonth = loanDuration;
    }

    public float getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(float loanAmount) {
        LoanAmount = loanAmount;
    }

    public float getOutstandingAmount() {
        return CurrentOutStandingAmount;
    }

    public void setOutstandingAmount(float outstandingAmount) {
        CurrentOutStandingAmount = outstandingAmount;
    }

    public float getMonthlyEMI() {
        return MonthlyEMI;
    }

    public void setMonthlyEMI(float monthlyEMI) {
        MonthlyEMI = monthlyEMI;
    }

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int applicationId) {
        ApplicationId = applicationId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public String LoanTakenOn;
    public int LoanDurationMonth;
    public float LoanAmount;
    public float CurrentOutStandingAmount;
    public float MonthlyEMI;

    public int ApplicationId;
    public int CustomerId;
    public int LoginUserId;

    public int getCELId() {
        return CELId;
    }

    public void setCELId(int CELId) {
        this.CELId = CELId;
    }

    public int CELId;
}
