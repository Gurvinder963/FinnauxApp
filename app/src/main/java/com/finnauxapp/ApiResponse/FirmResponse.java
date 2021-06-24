package com.finnauxapp.ApiResponse;

public class FirmResponse {

    /**
     * ID : 2
     * FirmId : 151
     * Partner_Name : suraj
     * Partner_Gender : M
     * Partner_Age : 25
     * Partner_PhoneNo : 9856329856
     * Partner_Designation : manager
     * Partner_SharePer : 10.0
     */

    private int ID;
    private int FirmId;
    private String Partner_Name;
    private String Partner_Gender;
    private int Partner_Age;
    private String Partner_PhoneNo;
    private String Partner_Designation;
    private double Partner_SharePer;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getFirmId() {
        return FirmId;
    }

    public void setFirmId(int FirmId) {
        this.FirmId = FirmId;
    }

    public String getPartner_Name() {
        return Partner_Name;
    }

    public void setPartner_Name(String Partner_Name) {
        this.Partner_Name = Partner_Name;
    }

    public String getPartner_Gender() {
        return Partner_Gender;
    }

    public void setPartner_Gender(String Partner_Gender) {
        this.Partner_Gender = Partner_Gender;
    }

    public int getPartner_Age() {
        return Partner_Age;
    }

    public void setPartner_Age(int Partner_Age) {
        this.Partner_Age = Partner_Age;
    }

    public String getPartner_PhoneNo() {
        return Partner_PhoneNo;
    }

    public void setPartner_PhoneNo(String Partner_PhoneNo) {
        this.Partner_PhoneNo = Partner_PhoneNo;
    }

    public String getPartner_Designation() {
        return Partner_Designation;
    }

    public void setPartner_Designation(String Partner_Designation) {
        this.Partner_Designation = Partner_Designation;
    }

    public double getPartner_SharePer() {
        return Partner_SharePer;
    }

    public void setPartner_SharePer(double Partner_SharePer) {
        this.Partner_SharePer = Partner_SharePer;
    }
}
