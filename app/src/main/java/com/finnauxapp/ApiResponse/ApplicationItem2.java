package com.finnauxapp.ApiResponse;

public class ApplicationItem2 {

    public int ACId;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int CustomerId;
    public String ProfilePic;

    public int getACId() {
        return ACId;
    }

    public void setACId(int ACId) {
        this.ACId = ACId;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getPhoneNoIsVerified() {
        return PhoneNoIsVerified;
    }

    public void setPhoneNoIsVerified(String phoneNoIsVerified) {
        PhoneNoIsVerified = phoneNoIsVerified;
    }

    public String Customer;

    public String getCustomerRelation() {
        return CustomerRelation;
    }

    public void setCustomerRelation(String customerRelation) {
        CustomerRelation = customerRelation;
    }

    public String CustomerRelation;
    public String CustomerType;
    public String PhoneNo;
    public String PhoneNoIsVerified;

    public boolean isIs_FI_Allow() {
        return Is_FI_Allow;
    }

    public void setIs_FI_Allow(boolean is_FI_Allow) {
        Is_FI_Allow = is_FI_Allow;
    }

    public boolean Is_FI_Allow;


}
