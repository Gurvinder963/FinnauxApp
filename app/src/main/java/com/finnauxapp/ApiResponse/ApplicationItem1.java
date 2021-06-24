package com.finnauxapp.ApiResponse;

public class ApplicationItem1 {

    private int ApplicationId;
    private String Application_No;
    private String Customer;
    private String MobileNo;

    public String getLoanPurpose() {
        return LoanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        LoanPurpose = loanPurpose;
    }

    private String LoanPurpose;

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getCollectionMode() {
        return CollectionMode;
    }

    public void setCollectionMode(String collectionMode) {
        CollectionMode = collectionMode;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public float getIR() {
        return IR;
    }

    public void setIR(float IR) {
        this.IR = IR;
    }

    private String CollectionMode;
    private int CustomerId;
    private float IR;
    private String Product;
    private int LoanAmount;
    private String LoanDuration;
    private String Branch;

    public int getBranchId() {
        return BranchId;
    }

    public void setBranchId(int branchId) {
        BranchId = branchId;
    }

    private int BranchId;
    private double LoginFeeAmount_Taken;

    public double getLoginFeeAmount_Taken() {
        return LoginFeeAmount_Taken;
    }

    public void setLoginFeeAmount_Taken(double loginFeeAmount_Taken) {
        LoginFeeAmount_Taken = loginFeeAmount_Taken;
    }

    public String getLoginFeeAmount_TakenOn() {
        return LoginFeeAmount_TakenOn;
    }

    public void setLoginFeeAmount_TakenOn(String loginFeeAmount_TakenOn) {
        LoginFeeAmount_TakenOn = loginFeeAmount_TakenOn;
    }

    public double getLoginFeeAmount_ToBeTaken() {
        return LoginFeeAmount_ToBeTaken;
    }

    public void setLoginFeeAmount_ToBeTaken(double loginFeeAmount_ToBeTaken) {
        LoginFeeAmount_ToBeTaken = loginFeeAmount_ToBeTaken;
    }

    public boolean isLoginFee_IsMandtory() {
        return LoginFee_IsMandtory;
    }

    public void setLoginFee_IsMandtory(boolean loginFee_IsMandtory) {
        LoginFee_IsMandtory = loginFee_IsMandtory;
    }

    private String LoginFeeAmount_TakenOn;
    private double LoginFeeAmount_ToBeTaken;

    public double getLoginFeeTax() {
        return LoginFeeTax;
    }

    public void setLoginFeeTax(double loginFeeTax) {
        LoginFeeTax = loginFeeTax;
    }

    private double LoginFeeTax;

    public double getSGST() {
        return SGST;
    }

    public void setSGST(double SGST) {
        this.SGST = SGST;
    }

    public double getCGST() {
        return CGST;
    }

    public void setCGST(double CGST) {
        this.CGST = CGST;
    }

    private double SGST;
    private double CGST;
    private boolean LoginFee_IsMandtory;

    public boolean isIs_AssetDetail() {
        return Is_AssetDetail;
    }

    public void setIs_AssetDetail(boolean is_AssetDetail) {
        Is_AssetDetail = is_AssetDetail;
    }

    private boolean Is_AssetDetail;



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

    public String getProduct() {
        return Product;
    }

    public void setProduct(String Product) {
        this.Product = Product;
    }

    public int getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(int LoanAmount) {
        this.LoanAmount = LoanAmount;
    }

    public String getLoanDuration() {
        return LoanDuration;
    }

    public void setLoanDuration(String LoanDuration) {
        this.LoanDuration = LoanDuration;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String Branch) {
        this.Branch = Branch;
    }
}
