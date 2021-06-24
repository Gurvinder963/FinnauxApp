package com.finnauxapp.ApiResponse;

public class HoldLeadResponse {


    /**
     * InquiryId : 1079
     * InquiryNo : EN0066
     * CustomerName : ASHISH GUPTA
     * CustomerAddress : 46, Panchwati colony, Behind Anil Eye hospital
     Mawana
     Meerut
     * ContactNumber : 9057217972
     * CreateOn : 05 Dec 20 10:21AM
     * Branch : JAIPUR HO
     * LoanAmount : 120000
     * Purpose : bullet
     * Status : Hold
     * BranchId : 1
     * Email : ss@qq.co
     * HoldOn : 16 Dec 2020
     * HoldReason : Plz follow up in next 2 month
     */

    private int InquiryId;
    private String InquiryNo;
    private String CustomerName;
    private String CustomerAddress;
    private String ContactNumber;
    private String CreateOn;
    private String Branch;
    private int LoanAmount;
    private String Purpose;
    private String Status;
    private int BranchId;
    private String Email;
    private String HoldOn;
    private String HoldReason;

    public int getInquiryId() {
        return InquiryId;
    }

    public void setInquiryId(int InquiryId) {
        this.InquiryId = InquiryId;
    }

    public String getInquiryNo() {
        return InquiryNo;
    }

    public void setInquiryNo(String InquiryNo) {
        this.InquiryNo = InquiryNo;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String CustomerAddress) {
        this.CustomerAddress = CustomerAddress;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String ContactNumber) {
        this.ContactNumber = ContactNumber;
    }

    public String getCreateOn() {
        return CreateOn;
    }

    public void setCreateOn(String CreateOn) {
        this.CreateOn = CreateOn;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String Branch) {
        this.Branch = Branch;
    }

    public int getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(int LoanAmount) {
        this.LoanAmount = LoanAmount;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String Purpose) {
        this.Purpose = Purpose;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getBranchId() {
        return BranchId;
    }

    public void setBranchId(int BranchId) {
        this.BranchId = BranchId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getHoldOn() {
        return HoldOn;
    }

    public void setHoldOn(String HoldOn) {
        this.HoldOn = HoldOn;
    }

    public String getHoldReason() {
        return HoldReason;
    }

    public void setHoldReason(String HoldReason) {
        this.HoldReason = HoldReason;
    }
}
