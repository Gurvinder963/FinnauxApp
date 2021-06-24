package com.finnauxapp.ApiResponse;

public class EmployeeBranchResponse {


    /**
     * BranchId : 1
     * Branch_Name : JAIPUR HO (JPR)
     */

    private int BranchId;
    private String Branch_Name;

    public int getBranchId() {
        return BranchId;
    }

    public void setBranchId(int BranchId) {
        this.BranchId = BranchId;
    }

    public String getBranch_Name() {
        return Branch_Name;
    }

    public void setBranch_Name(String Branch_Name) {
        this.Branch_Name = Branch_Name;
    }
}
