package com.finnauxapp.ApiRequest;

import java.util.List;

public class CalculateIRRRequest {

    public String Asset_Cost;
    public String NetFinance_Amt;
    public String Flat_Rate;

    public String getAsset_Cost() {
        return Asset_Cost;
    }

    public void setAsset_Cost(String asset_Cost) {
        Asset_Cost = asset_Cost;
    }

    public String getNetFinance_Amt() {
        return NetFinance_Amt;
    }

    public void setNetFinance_Amt(String netFinance_Amt) {
        NetFinance_Amt = netFinance_Amt;
    }

    public String getFlat_Rate() {
        return Flat_Rate;
    }

    public void setFlat_Rate(String flat_Rate) {
        Flat_Rate = flat_Rate;
    }

    public String getNo_Of_Inst() {
        return No_Of_Inst;
    }

    public void setNo_Of_Inst(String no_Of_Inst) {
        No_Of_Inst = no_Of_Inst;
    }

    public String getTenure() {
        return Tenure;
    }

    public void setTenure(String tenure) {
        Tenure = tenure;
    }

    public String getAdv_Inst() {
        return Adv_Inst;
    }

    public void setAdv_Inst(String adv_Inst) {
        Adv_Inst = adv_Inst;
    }

    public String getMgmtFee() {
        return MgmtFee;
    }

    public void setMgmtFee(String mgmtFee) {
        MgmtFee = mgmtFee;
    }

    public String getEMI_Amount() {
        return EMI_Amount;
    }

    public void setEMI_Amount(String EMI_Amount) {
        this.EMI_Amount = EMI_Amount;
    }

    public String getEMI_Type() {
        return EMI_Type;
    }

    public void setEMI_Type(String EMI_Type) {
        this.EMI_Type = EMI_Type;
    }



    public List<?> getSTEP_EMI() {
        return STEP_EMI;
    }

    public void setSTEP_EMI(List<?> STEP_EMI) {
        this.STEP_EMI = STEP_EMI;
    }

    public String No_Of_Inst;
    public String Tenure;
    public String Adv_Inst;
    public String MgmtFee;
    public String EMI_Amount;
    public String EMI_Type;

    public String getIRR_CalculateBy() {
        return IRR_CalculateBy;
    }

    public void setIRR_CalculateBy(String IRR_CalculateBy) {
        this.IRR_CalculateBy = IRR_CalculateBy;
    }

    public String IRR_CalculateBy;

    public String getIRR_Type() {
        return IRR_Type;
    }

    public void setIRR_Type(String IRR_Type) {
        this.IRR_Type = IRR_Type;
    }

    public boolean isStep() {
        return IsStep;
    }

    public void setStep(boolean step) {
        IsStep = step;
    }

    public int getSTEP_IRR() {
        return STEP_IRR;
    }

    public void setSTEP_IRR(int STEP_IRR) {
        this.STEP_IRR = STEP_IRR;
    }

    public String IRR_Type;
    public List<?> STEP_EMI;

    public List<String> getCashflow() {
        return cashflow;
    }

    public void setCashflow(List<String> cashflow) {
        this.cashflow = cashflow;
    }

    public List<String> cashflow;
    public boolean IsStep;
    public int STEP_IRR;

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String ProductId;
    public String First_EMI_Date;

    public String getFirst_EMI_Date() {
        return First_EMI_Date;
    }

    public void setFirst_EMI_Date(String first_EMI_Date) {
        First_EMI_Date = first_EMI_Date;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public String Purpose;
}
