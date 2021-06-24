package com.finnauxapp.ApiRequest;

import java.util.ArrayList;

public class CustomerRegistrationRequest {

    public ApplicationCustomerModel getApplicationCustomer() {
        return ApplicationCustomer;
    }

    public void setApplicationCustomer(ApplicationCustomerModel applicationCustomer) {
        ApplicationCustomer = applicationCustomer;
    }

    public ApplicationCustomerModel ApplicationCustomer;

    public ArrayList<kycDocModel> getCustomerKYCDoc() {
        return CustomerKYCDoc;
    }

    public void setCustomerKYCDoc(ArrayList<kycDocModel> customerKYCDoc) {
        CustomerKYCDoc = customerKYCDoc;
    }

    public ArrayList<kycDocModel> CustomerKYCDoc;


}
