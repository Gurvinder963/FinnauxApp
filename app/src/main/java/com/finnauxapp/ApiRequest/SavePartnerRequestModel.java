package com.finnauxapp.ApiRequest;

public class SavePartnerRequestModel {

    public int ApplicationId;
    public int FirmId;
    public int PartnerId;
    public int Partner_Age;

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int applicationId) {
        ApplicationId = applicationId;
    }

    public int getFirmId() {
        return FirmId;
    }

    public void setFirmId(int firmId) {
        FirmId = firmId;
    }

    public int getPartnerId() {
        return PartnerId;
    }

    public void setPartnerId(int partnerId) {
        PartnerId = partnerId;
    }

    public int getPartner_Age() {
        return Partner_Age;
    }

    public void setPartner_Age(int partner_Age) {
        Partner_Age = partner_Age;
    }

    public String getPartner_Name() {
        return Partner_Name;
    }

    public void setPartner_Name(String partner_Name) {
        Partner_Name = partner_Name;
    }

    public String getPartner_Gender() {
        return Partner_Gender;
    }

    public void setPartner_Gender(String partner_Gender) {
        Partner_Gender = partner_Gender;
    }

    public String getPartner_Designation() {
        return Partner_Designation;
    }

    public void setPartner_Designation(String partner_Designation) {
        Partner_Designation = partner_Designation;
    }

    public String getPartner_PhoneNo() {
        return Partner_PhoneNo;
    }

    public void setPartner_PhoneNo(String partner_PhoneNo) {
        Partner_PhoneNo = partner_PhoneNo;
    }

    public double getPartner_Share() {
        return Partner_Share;
    }

    public void setPartner_Share(double partner_Share) {
        Partner_Share = partner_Share;
    }

    public String Partner_Name;
    public String Partner_Gender;
    public String Partner_Designation;
    public String Partner_PhoneNo;
    public double Partner_Share;
}
