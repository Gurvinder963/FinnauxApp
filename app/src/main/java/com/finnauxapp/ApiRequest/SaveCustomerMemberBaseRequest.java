package com.finnauxapp.ApiRequest;

public class SaveCustomerMemberBaseRequest {

    public SaveCustomerMemberRequest getCustomerMember() {
        return CustomerMember;
    }

    public void setCustomerMember(SaveCustomerMemberRequest customerMember) {
        CustomerMember = customerMember;
    }

    public SaveCustomerMemberRequest CustomerMember;
}
