package com.finnauxapp.ApiRequest;

public class CustomerAddressBean {
    public int AddressId;
    public int CustomerId;
    public String AddressType;

    public int getAddressId() {
        return AddressId;
    }

    public void setAddressId(int addressId) {
        AddressId = addressId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getAddressType() {
        return AddressType;
    }

    public void setAddressType(String addressType) {
        AddressType = addressType;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLandMark() {
        return LandMark;
    }

    public void setLandMark(String landMark) {
        LandMark = landMark;
    }

    public int getTehsilId() {
        return TehsilId;
    }

    public void setTehsilId(int tehsilId) {
        TehsilId = tehsilId;
    }

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int districtId) {
        DistrictId = districtId;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getLatLong() {
        return LatLong;
    }

    public void setLatLong(String latLong) {
        LatLong = latLong;
    }

    public float getNearstBranchDistance_KM() {
        return NearstBranchDistance_KM;
    }

    public void setNearstBranchDistance_KM(float nearstBranchDistance_KM) {
        NearstBranchDistance_KM = nearstBranchDistance_KM;
    }

    public int getTotalYearsOnAddress() {
        return TotalYearsOnAddress;
    }

    public void setTotalYearsOnAddress(int totalYearsOnAddress) {
        TotalYearsOnAddress = totalYearsOnAddress;
    }

    public String getAddressRentBuy() {
        return AddressRentBuy;
    }

    public void setAddressRentBuy(String addressRentBuy) {
        AddressRentBuy = addressRentBuy;
    }

    public String Address;
    public String LandMark;
    public int TehsilId;
    public int DistrictId;
    public String PinCode;
    public String LatLong;
    public float NearstBranchDistance_KM;
    public int TotalYearsOnAddress;
    public String AddressRentBuy;

}
