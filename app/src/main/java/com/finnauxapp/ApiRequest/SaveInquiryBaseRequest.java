package com.finnauxapp.ApiRequest;

public class SaveInquiryBaseRequest {


    public SaveEnquiryRequest getInquiry() {
        return Inquiry;
    }

    public void setInquiry(SaveEnquiryRequest inquiry) {
        Inquiry = inquiry;
    }

    public SaveEnquiryRequest Inquiry;

}
