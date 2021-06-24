package com.finnauxapp.ApiRequest;

public class CustomerAddressSaveRequest {

    public CustomerAddressBean getAddress() {
        return Address;
    }

    public void setAddress(CustomerAddressBean address) {
        Address = address;
    }

    CustomerAddressBean Address;
}
