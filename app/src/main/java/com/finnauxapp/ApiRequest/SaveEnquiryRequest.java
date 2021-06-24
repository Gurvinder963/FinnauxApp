package com.finnauxapp.ApiRequest;

public class SaveEnquiryRequest {
    public String Source;
    public String CustomerName;

    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public int LoginUserId;

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerNumber() {
        return ContactNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        ContactNumber = customerNumber;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        CustomerEmail = customerEmail;
    }

    public int getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        LoanAmount = loanAmount;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public String CustomerAddress;
    public String ContactNumber;
    public String CustomerEmail;
    public int LoanAmount;
    public String Purpose;
    public String CustomerState;
    public String CustomerDistrict;

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getCustomerState() {
        return CustomerState;
    }

    public void setCustomerState(String customerState) {
        CustomerState = customerState;
    }

    public String getCustomerDistrict() {
        return CustomerDistrict;
    }

    public void setCustomerDistrict(String customerDistrict) {
        CustomerDistrict = customerDistrict;
    }

    public String getCustomerTehsil() {
        return CustomerTehsil;
    }

    public void setCustomerTehsil(String customerTehsil) {
        CustomerTehsil = customerTehsil;
    }

    public String getCustomerPincode() {
        return CustomerPincode;
    }

    public void setCustomerPincode(String customerPincode) {
        CustomerPincode = customerPincode;
    }

    public String getLeadReference() {
        return LeadReference;
    }

    public void setLeadReference(String leadReference) {
        LeadReference = leadReference;
    }

    public int getInquirySelfAssigned() {
        return InquirySelfAssigned;
    }

    public void setInquirySelfAssigned(int inquirySelfAssigned) {
        InquirySelfAssigned = inquirySelfAssigned;
    }

    public String CustomerTehsil;
    public String CustomerPincode;
    public String LeadReference;
    public int InquirySelfAssigned;

    public int getBranchId() {
        return BranchId;
    }

    public void setBranchId(int branchId) {
        BranchId = branchId;
    }

    public int BranchId;

}
