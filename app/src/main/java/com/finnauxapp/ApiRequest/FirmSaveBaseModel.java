package com.finnauxapp.ApiRequest;

import java.util.ArrayList;

public class FirmSaveBaseModel {
    public FirmSaveChildModel getFirm() {
        return Firm;
    }

    public void setFirm(FirmSaveChildModel firm) {
        Firm = firm;
    }

    public FirmSaveChildModel Firm;
    public ArrayList<kycDocModel> getCustomerKYCDoc() {
        return KYC_DOC;
    }

    public void setCustomerKYCDoc(ArrayList<kycDocModel> customerKYCDoc) {
        KYC_DOC = customerKYCDoc;
    }

    public ArrayList<kycDocModel> KYC_DOC;
}
