package com.finnauxapp.ApiRequest;

public class CustomerDetailRequest {
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

    public int ApplicationId;
    public int CustomerId;
}
