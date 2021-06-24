package com.finnauxapp.ApiResponse;

import java.io.Serializable;

public class ApplicationListResponse implements Serializable {


    /**
     * ApplicationId : 5
     * Application_No : FN0004
     * Customer :
     * Branch : JAIPUR (BR0001)
     * Product : Personal Loan (PL)
     * LoanAmount : 10000
     */
    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int CustomerId;
    private int ApplicationId;
    private String Application_No;
    private String Customer;

    public String getCreateOn() {
        return CreateOn;
    }

    public void setCreateOn(String createOn) {
        CreateOn = createOn;
    }

    private String CreateOn;

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    private String MobileNo;
    private String Branch;
    private String Product;

    public String getCreditUser() {
        return CreditUser;
    }

    public void setCreditUser(String creditUser) {
        CreditUser = creditUser;
    }

    public String getCreditUserRemark() {
        return CreditUserRemark;
    }

    public void setCreditUserRemark(String creditUserRemark) {
        CreditUserRemark = creditUserRemark;
    }

    private String CreditUser;
    private String CreditUserRemark;

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String ProfilePic;
    private float LoanAmount;

    public float getEMI() {
        return EMI;
    }

    public void setEMI(float EMI) {
        this.EMI = EMI;
    }

    public float getIR() {
        return IR;
    }

    public void setIR(float IR) {
        this.IR = IR;
    }

    private float EMI;
    private float IR;

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int ApplicationId) {
        this.ApplicationId = ApplicationId;
    }

    public String getApplication_No() {
        return Application_No;
    }

    public void setApplication_No(String Application_No) {
        this.Application_No = Application_No;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String Customer) {
        this.Customer = Customer;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String Branch) {
        this.Branch = Branch;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String Product) {
        this.Product = Product;
    }

    public float getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(float LoanAmount) {
        this.LoanAmount = LoanAmount;
    }
}
