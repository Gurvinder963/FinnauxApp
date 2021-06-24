package com.finnauxapp.ApiRequest;

public class CustomerIncomeBaseRequest {
    public SaveIncomeDetailRequest getCustomerIncome() {
        return CustomerIncome;
    }

    public void setCustomerIncome(SaveIncomeDetailRequest customerIncome) {
        CustomerIncome = customerIncome;
    }

    public SaveIncomeDetailRequest CustomerIncome;
}
