package com.finnauxapp.ApiResponse;

import java.util.List;

public class DuplicationCheckResponse {


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
         * CustomerId : 11
         * Customer_FirstName : aman
         * Customer_LastName : shamra
         * Customer_FatherName : manoj
         * Customer_Gender : M
         * Customer_DOB : 11/23/1980 12:00:00 AM
         * Customer_ProfilePic :
         * Customer_PhoneNo : 9898989898
         * Customer_PhoneNo1 : 8888888888
         * Customer_PhoneNo2 :
         * Customer_WhatsAppNo : 7777777777
         * Customer_PhoneNo_IsVerified : true
         * Customer_Email : a@gmail.com
         */

        private int CustomerId;
        private String Customer_FirstName;

       /* public String getCustomerType() {
            return CustomerType;
        }

        public void setCustomerType(String customerType) {
            CustomerType = customerType;
        }*/

      //  private String CustomerType;
        private String Customer_LastName;
        private String Customer_FatherName;
        private String Customer_Gender;
        private String Customer_DOB;
        private String Customer_ProfilePic;
        private String Customer_PhoneNo;
        private String Customer_PhoneNo1;
        private String Customer_PhoneNo2;
        private String Customer_WhatsAppNo;
        private boolean Customer_PhoneNo_IsVerified;
        private String Customer_Email;

        public int getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(int CustomerId) {
            this.CustomerId = CustomerId;
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

        public String getCustomer_PhoneNo2() {
            return Customer_PhoneNo2;
        }

        public void setCustomer_PhoneNo2(String Customer_PhoneNo2) {
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

    public static class Item2Bean {
        /**
         * DocId : 1
         * KYC_CustomerId : 11
         * KYC_DocId : 1
         * Doc_Name : PAN Card
         * KYC_DocNumber : 5656765
         * KYC_DocFile : PANCard_19790.jpg
         */

        private int DocId;
        private int KYC_CustomerId;
        private int KYC_DocId;
        private String Doc_Name;
        private String KYC_DocNumber;
        private String KYC_DocFile;

        public int getDocId() {
            return DocId;
        }

        public void setDocId(int DocId) {
            this.DocId = DocId;
        }

        public int getKYC_CustomerId() {
            return KYC_CustomerId;
        }

        public void setKYC_CustomerId(int KYC_CustomerId) {
            this.KYC_CustomerId = KYC_CustomerId;
        }

        public int getKYC_DocId() {
            return KYC_DocId;
        }

        public void setKYC_DocId(int KYC_DocId) {
            this.KYC_DocId = KYC_DocId;
        }

        public String getDoc_Name() {
            return Doc_Name;
        }

        public void setDoc_Name(String Doc_Name) {
            this.Doc_Name = Doc_Name;
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
    }
}
