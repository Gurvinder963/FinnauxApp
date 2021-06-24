package com.finnauxapp.ApiRequest;

public class CompleteProcessAppRequest {
    public int ApplicationId;
    public int ProcessId;

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int applicationId) {
        ApplicationId = applicationId;
    }

    public int getProcessId() {
        return ProcessId;
    }

    public void setProcessId(int processId) {
        ProcessId = processId;
    }

    public String getRecommendation() {
        return Recommendation;
    }

    public void setRecommendation(String recommendation) {
        Recommendation = recommendation;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public int getLoginUserId() {
        return LoginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        LoginUserId = loginUserId;
    }

    public String Recommendation;
    public String Remarks;
    public int LoginUserId;
}
