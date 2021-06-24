package com.finnauxapp.ApiRequest;

public class SaveAnswerChildModel {

    public int FI_ApplicationId;
    public int FI_CustomerId;

    public int getFI_ApplicationId() {

        return FI_ApplicationId;
    }

    public void setFI_ApplicationId(int FI_ApplicationId) {
        this.FI_ApplicationId = FI_ApplicationId;
    }

    public int getFI_CustomerId() {
        return FI_CustomerId;
    }

    public void setFI_CustomerId(int FI_CustomerId) {
        this.FI_CustomerId = FI_CustomerId;
    }

    public int getFI_LoginUserId() {
        return FI_LoginUserId;
    }

    public void setFI_LoginUserId(int FI_LoginUserId) {
        this.FI_LoginUserId = FI_LoginUserId;
    }

    public int getFI_QuestionId() {
        return FI_QuestionId;
    }

    public void setFI_QuestionId(int FI_QuestionId) {
        this.FI_QuestionId = FI_QuestionId;
    }

    public String getFI_Answer() {
        return FI_Answer;
    }

    public void setFI_Answer(String FI_Answer) {
        this.FI_Answer = FI_Answer;
    }

    public int FI_LoginUserId;
    public int FI_QuestionId;
    public String FI_Answer;
}
