package com.finnauxapp.ApiResponse;

import java.io.Serializable;

public class LeadResponse implements Serializable {


    /**
     * InquiryId : 1
     * InquiryNo : EN0001
     * CustomerName : Pradeep Kumar
     * CustomerAddress : 234 Rajendra Nagare, Vaishali Nagar, Sirsi Raod, Jaipur
     * ContactNumber : 8989898989
     * CreateOn : Oct  9 2020  2:06PM
     * Branch : JAIPUR
     */

    private int InquiryId;
    private String InquiryNo;
    private String CustomerName;

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    private String Source;
    private String CustomerAddress;
    private String ContactNumber;
    private String Purpose;

    public int getBranchId() {
        return BranchId;
    }

    public void setBranchId(int branchId) {
        BranchId = branchId;
    }

    private int BranchId;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private String Email;

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public String getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        LoanAmount = loanAmount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String LoanAmount;
    private String Status;

    private String CreateOn;
    private String Branch;
    private String HoldReason;

    public String getHoldReason() {
        return HoldReason;
    }

    public void setHoldReason(String holdReason) {
        HoldReason = holdReason;
    }

    public String getHoldOn() {
        return HoldOn;
    }

    public void setHoldOn(String holdOn) {
        HoldOn = holdOn;
    }

    private String HoldOn;

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
}
