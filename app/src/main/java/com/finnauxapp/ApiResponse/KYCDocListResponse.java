package com.finnauxapp.ApiResponse;

import java.util.List;

public class KYCDocListResponse {


    private List<Item1Bean> Item1;
    private List<Item2Bean> Item2;

    public List<Item1Bean> getItem1() {
        return Item1;
    }

    public void setItem1(List<Item1Bean> Item1) {
        this.Item1 = Item1;
    }

    public List<Item2Bean> getItem2() {
        return Item2;
    }

    public void setItem2(List<Item2Bean> Item2) {
        this.Item2 = Item2;
    }

    public static class Item1Bean {
        /**
         * CustomerId : 28
         * CustomerName : PRAVESH shekhawat
         * AC_CustomerType : H
         * ApplicationId : 13
         * Application_No : FN0010
         */

        private int CustomerId;
        private String CustomerName;
        private String AC_CustomerType;
        private int ApplicationId;
        private String Application_No;

        public int getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(int CustomerId) {
            this.CustomerId = CustomerId;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String CustomerName) {
            this.CustomerName = CustomerName;
        }

        public String getAC_CustomerType() {
            return AC_CustomerType;
        }

        public void setAC_CustomerType(String AC_CustomerType) {
            this.AC_CustomerType = AC_CustomerType;
        }

        public int getApplicationId() {
            return ApplicationId;
        }

        public void setApplicationId(int ApplicationId) {
            this.ApplicationId = ApplicationId;
        }

        public String getApplication_No() {
            return Application_No;
        }

        public void setApplication_No(String Application_No) {
            this.Application_No = Application_No;
        }
    }

    public static class Item2Bean {
        /**
         * KYC_DocId : 1
         * KYC_DocNumber : DTPPS3774G
         * KYC_DocFile : PANCard_22359.jpg
         * UploadOn : 28 Oct 20  2:01PM
         */

        private int KYC_DocId;
        private String KYC_DocNumber;
        private String KYC_DocFile;

        public String getDocumnet() {
            return Documnet;
        }

        public void setDocumnet(String documnet) {
            Documnet = documnet;
        }

        private String Documnet;
        private String UploadOn;

        public int getKYC_DocId() {
            return KYC_DocId;
        }

        public void setKYC_DocId(int KYC_DocId) {
            this.KYC_DocId = KYC_DocId;
        }

        public String getKYC_DocNumber() {
            return KYC_DocNumber;
        }

        public void setKYC_DocNumber(String KYC_DocNumber) {
            this.KYC_DocNumber = KYC_DocNumber;
        }

        public String getKYC_DocFile() {
            return KYC_DocFile;
        }

        public void setKYC_DocFile(String KYC_DocFile) {
            this.KYC_DocFile = KYC_DocFile;
        }

        public String getUploadOn() {
            return UploadOn;
        }

        public void setUploadOn(String UploadOn) {
            this.UploadOn = UploadOn;
        }
    }
}
