package com.finnauxapp.ApiResponse;

import java.util.List;

public class CustomerAddressResponse {


    private List<AddressItem1> Item1;
    private List<AddressItem2> Item2;

    public List<AddressItem1> getItem1() {
        return Item1;
    }

    public void setItem1(List<AddressItem1> Item1) {
        this.Item1 = Item1;
    }

    public List<AddressItem2> getItem2() {
        return Item2;
    }

    public void setItem2(List<AddressItem2> Item2) {
        this.Item2 = Item2;
    }


}
