package com.finnauxapp.ApiResponse;

public class SchemeResponse {

    /**
     * SchemeId : 2
     * Scheme : New HL Scheme
     * Product : Housing Loan
     * Amount : 50000.00 - 1000000.00
     * ROI : 12.00 - 25.00
     * Period : 25 Mar 2021 - 29 Apr 2021
     * AdvanceEMI : 0
     * Status : Running
     * SchemeState : Rajasthan
     */

    private int SchemeId;
    private String Scheme;
    private String Product;
    private String Amount;
    private String ROI;
    private String Period;
    private int AdvanceEMI;
    private String Status;
    private String SchemeState;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public int getSchemeId() {
        return SchemeId;
    }

    public void setSchemeId(int SchemeId) {
        this.SchemeId = SchemeId;
    }

    public String getScheme() {
        return Scheme;
    }

    public void setScheme(String Scheme) {
        this.Scheme = Scheme;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String Product) {
        this.Product = Product;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getROI() {
        return ROI;
    }

    public void setROI(String ROI) {
        this.ROI = ROI;
    }

    public String getPeriod() {
        return Period;
    }

    public void setPeriod(String Period) {
        this.Period = Period;
    }

    public int getAdvanceEMI() {
        return AdvanceEMI;
    }

    public void setAdvanceEMI(int AdvanceEMI) {
        this.AdvanceEMI = AdvanceEMI;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getSchemeState() {
        return SchemeState;
    }

    public void setSchemeState(String SchemeState) {
        this.SchemeState = SchemeState;
    }
}
