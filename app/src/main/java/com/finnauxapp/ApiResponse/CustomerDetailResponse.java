package com.finnauxapp.ApiResponse;

import java.io.Serializable;

public class CustomerDetailResponse implements Serializable {


    /**
     * CustomerId : 1
     * ApplicationNo : FN0001
     * CustomerType : H
     * Customer_FirstName : Pradeep
     * Customer_LastName : Kumar
     * Customer_FatherName : Mr. Ganpat Singh Tandi
     * Customer_Gender : M
     * Customer_DOB : 4/10/1980 12:00:00 AM
     * Customer_ProfilePic : pradeep.png
     * Customer_PhoneNo : 9414282404
     * Customer_PhoneNo1 : 7073343040
     * Customer_PhoneNo2 : null
     * Customer_WhatsAppNo : 7073343040
     * Customer_PhoneNo_IsVerified : true
     * Customer_Email : pradeep@gmail.com
     */

    private int CustomerId;
    private String ApplicationNo;
    private String CustomerType;
    private String Customer_FirstName;
    private String Customer_LastName;
    private String Customer_FatherName;
    private String Customer_Gender;
    private String Customer_DOB;
    private String Customer_ProfilePic;
    private String Customer_PhoneNo;
    private String Customer_PhoneNo1;
    private Object Customer_PhoneNo2;

    public String getDate_Of_Incorruptions() {
        return Date_Of_Incorruptions;
    }

    public void setDate_Of_Incorruptions(String date_Of_Incorruptions) {
        Date_Of_Incorruptions = date_Of_Incorruptions;
    }

    private String Date_Of_Incorruptions;

    private String Customer_WhatsAppNo;
    private String Nature_Of_Business;
    private String FirmType;

    public String getNature_Of_Business() {
        return Nature_Of_Business;
    }

    public void setNature_Of_Business(String nature_Of_Business) {
        Nature_Of_Business = nature_Of_Business;
    }

    public String getFirmType() {
        return FirmType;
    }

    public void setFirmType(String firmType) {
        FirmType = firmType;
    }

    public int getNoofEmployee() {
        return NoofEmployee;
    }

    public void setNoofEmployee(int noofEmployee) {
        NoofEmployee = noofEmployee;
    }

    public int getNoOfPartner() {
        return NoOfPartner;
    }

    public void setNoOfPartner(int noOfPartner) {
        NoOfPartner = noOfPartner;
    }

    public double getGrossValue() {
        return GrossValue;
    }

    public void setGrossValue(double grossValue) {
        GrossValue = grossValue;
    }

    private int NoofEmployee;
    private int NoOfPartner;
    private double GrossValue;
    private boolean Customer_PhoneNo_IsVerified;

    public boolean isFirm() {
        return IsFirm;
    }

    public void setFirm(boolean firm) {
        IsFirm = firm;
    }

    private boolean IsFirm;
    private String Customer_Email;
    public String Customer_Cast;
    public String Customer_Religion;
    public String getCustomer_Religion() {
        return Customer_Religion;
    }

    public void setCustomer_Religion(String customer_Religion) {
        Customer_Religion = customer_Religion;
    }

    public String getCustomer_Cast() {
        return Customer_Cast;
    }

    public void setCustomer_Cast(String customer_Cast) {
        Customer_Cast = customer_Cast;
    }

    public String getCustomer_MaritalStatus() {
        return Customer_MaritalStatus;
    }

    public void setCustomer_MaritalStatus(String customer_MaritalStatus) {
        Customer_MaritalStatus = customer_MaritalStatus;
    }

    public String Customer_MaritalStatus;
    public String getCustomer_Relation() {
        return CustomerRelation;
    }

    public void setCustomer_Relation(String customer_Relation) {
        CustomerRelation = customer_Relation;
    }

    private String CustomerRelation;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getApplicationNo() {
        return ApplicationNo;
    }

    public void setApplicationNo(String ApplicationNo) {
        this.ApplicationNo = ApplicationNo;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String CustomerType) {
        this.CustomerType = CustomerType;
    }

    public String getCustomer_FirstName() {
        return Customer_FirstName;
    }

    public void setCustomer_FirstName(String Customer_FirstName) {
        this.Customer_FirstName = Customer_FirstName;
    }

    public String getCustomer_LastName() {
        return Customer_LastName;
    }

    public void setCustomer_LastName(String Customer_LastName) {
        this.Customer_LastName = Customer_LastName;
    }

    public String getCustomer_FatherName() {
        return Customer_FatherName;
    }

    public void setCustomer_FatherName(String Customer_FatherName) {
        this.Customer_FatherName = Customer_FatherName;
    }

    public String getCustomer_Gender() {
        return Customer_Gender;
    }

    public void setCustomer_Gender(String Customer_Gender) {
        this.Customer_Gender = Customer_Gender;
    }

    public String getCustomer_DOB() {
        return Customer_DOB;
    }

    public void setCustomer_DOB(String Customer_DOB) {
        this.Customer_DOB = Customer_DOB;
    }

    public String getCustomer_ProfilePic() {
        return Customer_ProfilePic;
    }

    public void setCustomer_ProfilePic(String Customer_ProfilePic) {
        this.Customer_ProfilePic = Customer_ProfilePic;
    }

    public String getCustomer_PhoneNo() {
        return Customer_PhoneNo;
    }

    public void setCustomer_PhoneNo(String Customer_PhoneNo) {
        this.Customer_PhoneNo = Customer_PhoneNo;
    }

    public String getCustomer_PhoneNo1() {
        return Customer_PhoneNo1;
    }

    public void setCustomer_PhoneNo1(String Customer_PhoneNo1) {
        this.Customer_PhoneNo1 = Customer_PhoneNo1;
    }

    public Object getCustomer_PhoneNo2() {
        return Customer_PhoneNo2;
    }

    public void setCustomer_PhoneNo2(Object Customer_PhoneNo2) {
        this.Customer_PhoneNo2 = Customer_PhoneNo2;
    }

    public String getCustomer_WhatsAppNo() {
        return Customer_WhatsAppNo;
    }

    public void setCustomer_WhatsAppNo(String Customer_WhatsAppNo) {
        this.Customer_WhatsAppNo = Customer_WhatsAppNo;
    }

    public boolean isCustomer_PhoneNo_IsVerified() {
        return Customer_PhoneNo_IsVerified;
    }

    public void setCustomer_PhoneNo_IsVerified(boolean Customer_PhoneNo_IsVerified) {
        this.Customer_PhoneNo_IsVerified = Customer_PhoneNo_IsVerified;
    }

    public String getCustomer_Email() {
        return Customer_Email;
    }

    public void setCustomer_Email(String Customer_Email) {
        this.Customer_Email = Customer_Email;
    }
}
