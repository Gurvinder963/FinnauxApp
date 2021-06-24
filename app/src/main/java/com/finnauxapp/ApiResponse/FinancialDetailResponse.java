package com.finnauxapp.ApiResponse;

import java.util.List;

public class FinancialDetailResponse {


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

    public static class Item2Bean {
        public int FromEMI;
        public int ToEMI;

        public int getFromEMI() {
            return FromEMI;
        }

        public void setFromEMI(int fromEMI) {
            FromEMI = fromEMI;
        }

        public int getToEMI() {
            return ToEMI;
        }

        public void setToEMI(int toEMI) {
            ToEMI = toEMI;
        }

        public int getNoOfEMI() {
            return NoOfEMI;
        }

        public void setNoOfEMI(int noOfEMI) {
            NoOfEMI = noOfEMI;
        }

        public double getEMIAmount() {
            return EMIAmount;
        }

        public void setEMIAmount(double EMIAmount) {
            this.EMIAmount = EMIAmount;
        }

        public double getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            TotalAmount = totalAmount;
        }

        public int NoOfEMI;
        public double EMIAmount;
        public double TotalAmount;

    }


    public static class Item1Bean {
        /**
         * ApplicationId : CczhRe5hkngr8l8HhvHu4g==
         * ApplicationIdentity : 8
         * ApplicationNo : APJPR00008
         * ProductId : 18
         * Product : Business Loan Un-Secured
         * AssetCost : 1000000.0
         * NetFinance : 10000.0
         * Flat_Rate : 12.0
         * Tenure : 12
         * No_Of_Instl : 12
         * Adv_Instl : 0
         * ManagementFee : 0.0
         * DisbursementAmt : 10000.0
         * AgreementValue : 11208.0
         * InterestAmt : 1208.0
         * EMIAmount : 934.0
         * IRR_Type : Flat
         * Case_IRR : 21.6
         * Disbursement_IRR : 21.6
         * LTV : 1.0
         * Margin : 990000.0
         * FirstEMIDate : 2021-03-29T00:00:00
         * EMI_Type : Monthly
         * ExpiryDate : 2022-03-29T00:00:00
         * SchemeId : 0
         * IRR_CalculateBy : ROI
         */

        private String ApplicationId;
        private int ApplicationIdentity;
        private String ApplicationNo;
        private int ProductId;
        private String Product;
        private double AssetCost;
        private double NetFinance;
        private double Flat_Rate;
        private int Tenure;
        private int No_Of_Instl;
        private int Adv_Instl;
        private double ManagementFee;
        private double DisbursementAmt;
        private double AgreementValue;
        private double InterestAmt;
        private double EMIAmount;
        private String IRR_Type;
        private double Case_IRR;
        private double Disbursement_IRR;
        private double LTV;
        private double Margin;
        private String FirstEMIDate;
        private String EMI_Type;
        private String ExpiryDate;
        private int SchemeId;
        private String IRR_CalculateBy;

        public String getApplicationId() {
            return ApplicationId;
        }

        public void setApplicationId(String ApplicationId) {
            this.ApplicationId = ApplicationId;
        }

        public int getApplicationIdentity() {
            return ApplicationIdentity;
        }

        public void setApplicationIdentity(int ApplicationIdentity) {
            this.ApplicationIdentity = ApplicationIdentity;
        }

        public String getApplicationNo() {
            return ApplicationNo;
        }

        public void setApplicationNo(String ApplicationNo) {
            this.ApplicationNo = ApplicationNo;
        }

        public int getProductId() {
            return ProductId;
        }

        public void setProductId(int ProductId) {
            this.ProductId = ProductId;
        }

        public String getProduct() {
            return Product;
        }

        public void setProduct(String Product) {
            this.Product = Product;
        }

        public double getAssetCost() {
            return AssetCost;
        }

        public void setAssetCost(double AssetCost) {
            this.AssetCost = AssetCost;
        }

        public double getNetFinance() {
            return NetFinance;
        }

        public void setNetFinance(double NetFinance) {
            this.NetFinance = NetFinance;
        }

        public double getFlat_Rate() {
            return Flat_Rate;
        }

        public void setFlat_Rate(double Flat_Rate) {
            this.Flat_Rate = Flat_Rate;
        }

        public int getTenure() {
            return Tenure;
        }

        public void setTenure(int Tenure) {
            this.Tenure = Tenure;
        }

        public int getNo_Of_Instl() {
            return No_Of_Instl;
        }

        public void setNo_Of_Instl(int No_Of_Instl) {
            this.No_Of_Instl = No_Of_Instl;
        }

        public int getAdv_Instl() {
            return Adv_Instl;
        }

        public void setAdv_Instl(int Adv_Instl) {
            this.Adv_Instl = Adv_Instl;
        }

        public double getManagementFee() {
            return ManagementFee;
        }

        public void setManagementFee(double ManagementFee) {
            this.ManagementFee = ManagementFee;
        }

        public double getDisbursementAmt() {
            return DisbursementAmt;
        }

        public void setDisbursementAmt(double DisbursementAmt) {
            this.DisbursementAmt = DisbursementAmt;
        }

        public double getAgreementValue() {
            return AgreementValue;
        }

        public void setAgreementValue(double AgreementValue) {
            this.AgreementValue = AgreementValue;
        }

        public double getInterestAmt() {
            return InterestAmt;
        }

        public void setInterestAmt(double InterestAmt) {
            this.InterestAmt = InterestAmt;
        }

        public double getEMIAmount() {
            return EMIAmount;
        }

        public void setEMIAmount(double EMIAmount) {
            this.EMIAmount = EMIAmount;
        }

        public String getIRR_Type() {
            return IRR_Type;
        }

        public void setIRR_Type(String IRR_Type) {
            this.IRR_Type = IRR_Type;
        }

        public double getCase_IRR() {
            return Case_IRR;
        }

        public void setCase_IRR(double Case_IRR) {
            this.Case_IRR = Case_IRR;
        }

        public double getDisbursement_IRR() {
            return Disbursement_IRR;
        }

        public void setDisbursement_IRR(double Disbursement_IRR) {
            this.Disbursement_IRR = Disbursement_IRR;
        }

        public double getLTV() {
            return LTV;
        }

        public void setLTV(double LTV) {
            this.LTV = LTV;
        }

        public double getMargin() {
            return Margin;
        }

        public void setMargin(double Margin) {
            this.Margin = Margin;
        }

        public String getFirstEMIDate() {
            return FirstEMIDate;
        }

        public void setFirstEMIDate(String FirstEMIDate) {
            this.FirstEMIDate = FirstEMIDate;
        }

        public String getEMI_Type() {
            return EMI_Type;
        }

        public void setEMI_Type(String EMI_Type) {
            this.EMI_Type = EMI_Type;
        }

        public String getExpiryDate() {
            return ExpiryDate;
        }

        public void setExpiryDate(String ExpiryDate) {
            this.ExpiryDate = ExpiryDate;
        }

        public int getSchemeId() {
            return SchemeId;
        }

        public void setSchemeId(int SchemeId) {
            this.SchemeId = SchemeId;
        }

        public String getIRR_CalculateBy() {
            return IRR_CalculateBy;
        }

        public void setIRR_CalculateBy(String IRR_CalculateBy) {
            this.IRR_CalculateBy = IRR_CalculateBy;
        }
    }
}
