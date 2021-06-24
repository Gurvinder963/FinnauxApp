package com.finnauxapp.ApiRequest;

public class ReferenceSaveRequest {
    public int RefId;
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
    public String ReferenceType;
    public String PersonName;

    public int getRefId() {
        return RefId;
    }

    public void setRefId(int refId) {
        RefId = refId;
    }

    public String getReferenceType() {
        return ReferenceType;
    }

    public void setReferenceType(String referenceType) {
        ReferenceType = referenceType;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public String getPersonAddress() {
        return PersonAddress;
    }

    public void setPersonAddress(String personAddress) {
        PersonAddress = personAddress;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getPersonComments() {
        return PersonComments;
    }

    public void setPersonComments(String personComments) {
        PersonComments = personComments;
    }

    public int getPersonKnowYear() {
        return PersonKnowYear;
    }

    public void setPersonKnowYear(int personKnowYear) {
        PersonKnowYear = personKnowYear;
    }

    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public String PersonAddress;
    public String ContactNo;
    public String PersonComments;
    public int PersonKnowYear;
    public int LoginUserId;

}
