package com.finnauxapp.ApiResponse;

public class ReferenceResponse {


    /**
     * RefId : 2
     * ApplicationId : 13
     * CustomerId : 28
     * ReferenceType : Neighbour
     * PersonName : vikas ji
     * PersonAddress : Manasarovaor
     * ContactNo : 9859384933
     * PersonKnowYear : 15
     * PersonComments : 9859384933
     * RefDate : 07 Nov 2020
     */

    private int RefId;
    private int ApplicationId;
    private int CustomerId;
    private String ReferenceType;
    private String PersonName;
    private String PersonAddress;
    private String ContactNo;
    private int PersonKnowYear;
    private String PersonComments;
    private String RefDate;

    public int getRefId() {
        return RefId;
    }

    public void setRefId(int RefId) {
        this.RefId = RefId;
    }

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int ApplicationId) {
        this.ApplicationId = ApplicationId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getReferenceType() {
        return ReferenceType;
    }

    public void setReferenceType(String ReferenceType) {
        this.ReferenceType = ReferenceType;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String PersonName) {
        this.PersonName = PersonName;
    }

    public String getPersonAddress() {
        return PersonAddress;
    }

    public void setPersonAddress(String PersonAddress) {
        this.PersonAddress = PersonAddress;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String ContactNo) {
        this.ContactNo = ContactNo;
    }

    public int getPersonKnowYear() {
        return PersonKnowYear;
    }

    public void setPersonKnowYear(int PersonKnowYear) {
        this.PersonKnowYear = PersonKnowYear;
    }

    public String getPersonComments() {
        return PersonComments;
    }

    public void setPersonComments(String PersonComments) {
        this.PersonComments = PersonComments;
    }

    public String getRefDate() {
        return RefDate;
    }

    public void setRefDate(String RefDate) {
        this.RefDate = RefDate;
    }
}
