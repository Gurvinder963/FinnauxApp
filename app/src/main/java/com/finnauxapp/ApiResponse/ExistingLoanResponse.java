package com.finnauxapp.ApiResponse;

public class ExistingLoanResponse {


    /**
     * ExistingLoanId : 1
     * ApplicationId : 13
     * CustomerId : 28
     * LoanStatus : Running
     * LoanType : Housing Loan (HL)
     * LoanAmount : 100000.5
     * LoanTakenFrom : au bank
     * LoanTakenOn : 2015
     * LoanDuration_Month : 5
     * MonthlyEMI : 1000.0
     * CurrentOutStandingAmount : 150000.0
     */

    private int ExistingLoanId;
    private int ApplicationId;
    private int CustomerId;
    private String LoanStatus;
    private String LoanType;
    private double LoanAmount;
    private String LoanTakenFrom;
    private String LoanTakenOn;
    private int LoanDuration_Month;
    private double MonthlyEMI;
    private double CurrentOutStandingAmount;

    public int getExistingLoanId() {
        return ExistingLoanId;
    }

    public void setExistingLoanId(int ExistingLoanId) {
        this.ExistingLoanId = ExistingLoanId;
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

    public String getLoanStatus() {
        return LoanStatus;
    }

    public void setLoanStatus(String LoanStatus) {
        this.LoanStatus = LoanStatus;
    }

    public String getLoanType() {
        return LoanType;
    }

    public void setLoanType(String LoanType) {
        this.LoanType = LoanType;
    }

    public double getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(double LoanAmount) {
        this.LoanAmount = LoanAmount;
    }

    public String getLoanTakenFrom() {
        return LoanTakenFrom;
    }

    public void setLoanTakenFrom(String LoanTakenFrom) {
        this.LoanTakenFrom = LoanTakenFrom;
    }

    public String getLoanTakenOn() {
        return LoanTakenOn;
    }

    public void setLoanTakenOn(String LoanTakenOn) {
        this.LoanTakenOn = LoanTakenOn;
    }

    public int getLoanDuration_Month() {
        return LoanDuration_Month;
    }

    public void setLoanDuration_Month(int LoanDuration_Month) {
        this.LoanDuration_Month = LoanDuration_Month;
    }

    public double getMonthlyEMI() {
        return MonthlyEMI;
    }

    public void setMonthlyEMI(double MonthlyEMI) {
        this.MonthlyEMI = MonthlyEMI;
    }

    public double getCurrentOutStandingAmount() {
        return CurrentOutStandingAmount;
    }

    public void setCurrentOutStandingAmount(double CurrentOutStandingAmount) {
        this.CurrentOutStandingAmount = CurrentOutStandingAmount;
    }
}
