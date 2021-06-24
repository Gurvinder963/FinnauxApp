package com.finnauxapp.ApiResponse;

public class CalculateIRRResponse {

    public float Disbursement_Amt;
    public float Interest_Amt;
    public float Agreement_Value;

    public float getDisbursement_Amt() {
        return Disbursement_Amt;
    }

    public void setDisbursement_Amt(float disbursement_Amt) {
        Disbursement_Amt = disbursement_Amt;
    }

    public float getInterest_Amt() {
        return Interest_Amt;
    }

    public void setInterest_Amt(float interest_Amt) {
        Interest_Amt = interest_Amt;
    }

    public float getAgreement_Value() {
        return Agreement_Value;
    }

    public void setAgreement_Value(float agreement_Value) {
        Agreement_Value = agreement_Value;
    }

    public float getLTV() {
        return LTV;
    }

    public void setLTV(float LTV) {
        this.LTV = LTV;
    }

    public float getEMI_Amt() {
        return EMI_Amt;
    }

    public void setEMI_Amt(float EMI_Amt) {
        this.EMI_Amt = EMI_Amt;
    }

    public float getCase_IRR() {
        return Case_IRR;
    }

    public void setCase_IRR(float case_IRR) {
        Case_IRR = case_IRR;
    }

    public float getDisbursement_IRR() {
        return Disbursement_IRR;
    }

    public void setDisbursement_IRR(float disbursement_IRR) {
        Disbursement_IRR = disbursement_IRR;
    }

    public float getMargin() {
        return Margin;
    }

    public void setMargin(float margin) {
        Margin = margin;
    }

    public float getROI() {
        return ROI;
    }

    public void setROI(float ROI) {
        this.ROI = ROI;
    }

    public float LTV;
    public float EMI_Amt;
    public float Case_IRR;
    public float Disbursement_IRR;
    public float Margin;
    public float ROI;
}
