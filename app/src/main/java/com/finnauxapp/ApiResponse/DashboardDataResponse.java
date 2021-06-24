package com.finnauxapp.ApiResponse;

public class DashboardDataResponse {


    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    /**
     * MyLeads : 5
     * MyApplications : 1
     * PendingDoc : 1
     * FileProcessing : 2
     */

    private int Value;

    public int getProcessId() {
        return ProcessId;
    }

    public void setProcessId(int processId) {
        ProcessId = processId;
    }

    private int ProcessId;
   private String Key;


}
