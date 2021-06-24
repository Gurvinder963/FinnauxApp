package com.finnauxapp.ApiRequest;

public class DeleteCustomerRequest {
    public int CustomerId;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int applicationId) {
        ApplicationId = applicationId;
    }

    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public int ApplicationId;
    int LoginUserId;
}
