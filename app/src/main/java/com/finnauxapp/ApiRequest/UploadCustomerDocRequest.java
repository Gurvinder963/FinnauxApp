package com.finnauxapp.ApiRequest;

public class UploadCustomerDocRequest {
    public String DocData;

    public String getDocData() {
        return DocData;
    }

    public void setDocData(String docData) {
        DocData = docData;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String DocName;
    public String CustomerID;
}
