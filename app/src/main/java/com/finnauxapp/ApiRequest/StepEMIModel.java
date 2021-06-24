package com.finnauxapp.ApiRequest;

public class StepEMIModel {

    public String FromEMI;

    public String getFromEMI() {
        return FromEMI;
    }

    public void setFromEMI(String fromEMI) {
        FromEMI = fromEMI;
    }

    public String getToEMI() {
        return ToEMI;
    }

    public void setToEMI(String toEMI) {
        ToEMI = toEMI;
    }

    public String getEMI_Amount() {
        return EMI_Amount;
    }

    public void setEMI_Amount(String EMI_Amount) {
        this.EMI_Amount = EMI_Amount;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getNoOfInstl() {
        return NoOfInstl;
    }

    public void setNoOfInstl(String noOfInstl) {
        NoOfInstl = noOfInstl;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String ToEMI;
    public String EMI_Amount;
    public boolean disable;
    public String NoOfInstl;
  public String TotalAmount;
}
